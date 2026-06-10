package com.isacetin.gibinteraktifsosyalapp.feature.game.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isacetin.gibinteraktifsosyalapp.feature.game.domain.model.LeaderboardEntry
import com.isacetin.gibinteraktifsosyalapp.feature.game.domain.usecase.GetGameOverviewUseCase
import com.isacetin.gibinteraktifsosyalapp.feature.game.domain.usecase.GetLeaderboardUseCase
import com.isacetin.gibinteraktifsosyalapp.feature.game.domain.usecase.SubmitGameScoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class GameViewModel @Inject constructor(
    private val getOverview: GetGameOverviewUseCase,
    private val getLeaderboard: GetLeaderboardUseCase,
    private val submitScore: SubmitGameScoreUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<GameUiState>(GameUiState.Loading)
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private var gameJob: Job? = null
    private var nextCoinId = 1

    init {
        load()
    }

    fun load() {
        _uiState.value = GameUiState.Loading
        viewModelScope.launch {
            getOverview()
                .onSuccess { overview ->
                    _uiState.value = GameUiState.Content(
                        bestScore = overview.bestScore.takeIf { it > 0 } ?: seedLeaderboard().first().score,
                        balance = overview.balance.takeIf { it > 0 } ?: 1240,
                        leaderboard = overview.leaderboard.ifEmpty { seedLeaderboard() },
                    )
                }
                .onFailure {
                    _uiState.value = GameUiState.Content(
                        bestScore = 1840,
                        balance = 1240,
                        leaderboard = seedLeaderboard(),
                        bannerMessage = "Demo leaderboard gösteriliyor",
                    )
                }
        }
    }

    fun startGame() {
        gameJob?.cancel()
        nextCoinId = 1
        _uiState.value = contentOrSeed().copy(
            mode = GameMode.Playing,
            score = 0,
            timeLeftSeconds = 30,
            combo = 1,
            earnedPoints = 0,
            bannerMessage = null,
            basketX = 0.5f,
            coins = listOf(spawnCoin()),
        )
        gameJob = viewModelScope.launch {
            var sinceSpawnMs = 0L
            var sinceSecondMs = 0L
            while (true) {
                delay(FRAME_MS)
                val state = _uiState.value as? GameUiState.Content ?: return@launch
                if (state.mode != GameMode.Playing) return@launch

                val dt = FRAME_MS / 1000f
                var score = state.score
                var combo = state.combo
                val survivors = ArrayList<FallingCoin>(state.coins.size)
                for (coin in state.coins) {
                    val moved = coin.copy(y = coin.y + coin.speed * dt)
                    when {
                        moved.y < CATCH_LINE -> survivors.add(moved)
                        kotlin.math.abs(moved.x - state.basketX) <= CATCH_TOLERANCE -> {
                            // Sepetle yakalandı
                            combo = (combo + 1).coerceAtMost(5)
                            score += moved.value * combo
                        }
                        else -> {
                            // Iskalandı, combo sıfırlanır
                            combo = 1
                        }
                    }
                }

                sinceSpawnMs += FRAME_MS
                if (sinceSpawnMs >= SPAWN_INTERVAL_MS && survivors.size < MAX_COINS) {
                    sinceSpawnMs = 0L
                    survivors.add(spawnCoin())
                }

                sinceSecondMs += FRAME_MS
                var timeLeft = state.timeLeftSeconds
                if (sinceSecondMs >= 1_000) {
                    sinceSecondMs -= 1_000
                    timeLeft -= 1
                }

                _uiState.value = state.copy(
                    score = score,
                    combo = combo,
                    timeLeftSeconds = timeLeft.coerceAtLeast(0),
                    coins = survivors,
                )

                if (timeLeft <= 0) {
                    finishGame()
                    return@launch
                }
            }
        }
    }

    fun moveBasket(x: Float) {
        val state = _uiState.value as? GameUiState.Content ?: return
        if (state.mode != GameMode.Playing) return
        _uiState.value = state.copy(basketX = x.coerceIn(0f, 1f))
    }

    fun finishGame() {
        gameJob?.cancel()
        val state = _uiState.value as? GameUiState.Content ?: return
        if (state.mode != GameMode.Playing) return

        _uiState.value = state.copy(mode = GameMode.Result, coins = emptyList())
        viewModelScope.launch {
            submitScore(state.score)
                .onSuccess { result ->
                    val currentUser = LeaderboardEntry(
                        rank = 0,
                        displayName = "Ahmet K. (sen)",
                        score = result.score,
                        isCurrentUser = true,
                    )
                    val leaderboard = (state.leaderboard.filterNot { it.isCurrentUser } + currentUser)
                        .sortedByDescending { it.score }
                        .take(20)
                        .mapIndexed { index, entry -> entry.copy(rank = index + 1) }

                    _uiState.value = state.copy(
                        mode = GameMode.Result,
                        bestScore = maxOf(state.bestScore, result.score),
                        balance = state.balance + result.earnedPoints,
                        score = result.score,
                        earnedPoints = result.earnedPoints,
                        coins = emptyList(),
                        leaderboard = leaderboard,
                        bannerMessage = "Skor kaydedildi",
                    )
                }
                .onFailure { error ->
                    _uiState.value = state.copy(
                        mode = GameMode.Result,
                        coins = emptyList(),
                        bannerMessage = error.message ?: "Skor kaydedilemedi",
                    )
                }
        }
    }

    fun showLeaderboard() {
        val state = contentOrSeed()
        _uiState.value = state.copy(mode = GameMode.Leaderboard, bannerMessage = null)
        viewModelScope.launch {
            getLeaderboard()
                .onSuccess { entries ->
                    val leaderboard = entries.ifEmpty { seedLeaderboard() }
                    val current = _uiState.value as? GameUiState.Content ?: return@launch
                    _uiState.value = current.copy(leaderboard = leaderboard)
                }
        }
    }

    fun showLanding() {
        gameJob?.cancel()
        _uiState.value = contentOrSeed().copy(mode = GameMode.Landing, coins = emptyList(), bannerMessage = null)
    }

    fun dismissBanner() {
        val state = _uiState.value as? GameUiState.Content ?: return
        _uiState.value = state.copy(bannerMessage = null)
    }

    private fun contentOrSeed(): GameUiState.Content =
        (_uiState.value as? GameUiState.Content) ?: GameUiState.Content(
            bestScore = 1840,
            balance = 1240,
            leaderboard = seedLeaderboard(),
        )

    private fun spawnCoin(): FallingCoin =
        FallingCoin(
            id = nextCoinId++,
            x = Random.nextFloat().coerceIn(0.08f, 0.92f),
            y = 0f,
            value = listOf(50, 80, 100, 120).random(),
            speed = Random.nextDouble(0.30, 0.55).toFloat(),
        )

    private fun seedLeaderboard(): List<LeaderboardEntry> =
        listOf(
            LeaderboardEntry(rank = 1, displayName = "Elif Y.", score = 1840),
            LeaderboardEntry(rank = 2, displayName = "Mert D.", score = 1720),
            LeaderboardEntry(rank = 3, displayName = "Burak S.", score = 1690),
            LeaderboardEntry(rank = 4, displayName = "Ahmet K. (sen)", score = 1240, isCurrentUser = true),
            LeaderboardEntry(rank = 5, displayName = "Zeynep A.", score = 1180),
            LeaderboardEntry(rank = 6, displayName = "Can B.", score = 980),
        )

    private companion object {
        const val FRAME_MS = 16L
        const val SPAWN_INTERVAL_MS = 750L
        const val MAX_COINS = 6
        // Sepetin yakalama çizgisi (ekranın dikey oranı; 1.0 en alt)
        const val CATCH_LINE = 0.9f
        // Coin ile sepet merkezi arasındaki kabul edilen yatay mesafe
        const val CATCH_TOLERANCE = 0.14f
    }
}
