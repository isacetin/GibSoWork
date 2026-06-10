package com.isacetin.gibinteraktifsosyalapp.feature.game.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.CoinIcon
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.EmptyState
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.GibCard
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.GibPrimaryButton
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.LoadingShimmer
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.PointBadge
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.CardShape
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.GibExtendedTheme
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.GibTheme
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.PillShape
import com.isacetin.gibinteraktifsosyalapp.feature.game.domain.model.LeaderboardEntry

@Composable
fun GameRoute(
    modifier: Modifier = Modifier,
    viewModel: GameViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    GameScreen(
        uiState = uiState,
        onStart = viewModel::startGame,
        onCoinTap = viewModel::tapCoin,
        onFinish = viewModel::finishGame,
        onLeaderboard = viewModel::showLeaderboard,
        onLanding = viewModel::showLanding,
        onDismissBanner = viewModel::dismissBanner,
        modifier = modifier,
    )
}

@Composable
fun GameScreen(
    uiState: GameUiState,
    onStart: () -> Unit,
    onCoinTap: (FallingCoin) -> Unit,
    onFinish: () -> Unit,
    onLeaderboard: () -> Unit,
    onLanding: () -> Unit,
    onDismissBanner: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        GameUiState.Loading -> LoadingContent(modifier)
        is GameUiState.Error -> EmptyState(
            title = "Oyun yüklenemedi",
            description = uiState.message,
            emoji = "🪙",
            modifier = modifier.fillMaxSize(),
        )
        is GameUiState.Content -> when (uiState.mode) {
            GameMode.Landing -> LandingContent(uiState, onStart, onLeaderboard, onDismissBanner, modifier)
            GameMode.Playing -> PlayingContent(uiState, onCoinTap, onFinish, modifier)
            GameMode.Result -> ResultContent(uiState, onStart, onLeaderboard, onDismissBanner, modifier)
            GameMode.Leaderboard -> LeaderboardContent(uiState, onLanding, onDismissBanner, modifier)
        }
    }
}

@Composable
private fun LoadingContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        LoadingShimmer(modifier = Modifier.fillMaxWidth().height(220.dp).clip(CardShape))
        LoadingShimmer(modifier = Modifier.fillMaxWidth().height(88.dp).clip(CardShape))
        LoadingShimmer(modifier = Modifier.fillMaxWidth().height(88.dp).clip(CardShape))
    }
}

@Composable
private fun LandingContent(
    state: GameUiState.Content,
    onStart: () -> Unit,
    onLeaderboard: () -> Unit,
    onDismissBanner: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            HeaderRow(title = "Oyna", points = state.balance)
        }
        state.bannerMessage?.let { message ->
            item { Banner(message = message, onDismiss = onDismissBanner) }
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(CardShape)
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFF4F46E5), Color(0xFF7C3AED), Color(0xFFDB2777)),
                        ),
                    )
                    .padding(20.dp),
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Pill(text = "GÜNÜN OYUNU", color = Color.White.copy(alpha = 0.20f), contentColor = Color.White)
                    Text(
                        text = "Puan Yağmuru 🪙",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                    )
                    Text(
                        text = "Düşen coinleri yakala, 30 saniyede en yüksek skoru yap.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.88f),
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        StatTile(label = "Bugünkü en iyi", value = formatScore(state.bestScore), modifier = Modifier.weight(1f))
                        StatTile(label = "Skor başına", value = "🪙 1 puan", modifier = Modifier.weight(1f))
                    }
                    GibPrimaryButton(text = "Başla", onClick = onStart, modifier = Modifier.fillMaxWidth())
                }
            }
        }
        item {
            SectionTitle(title = "Sıralama · Bu Hafta", action = "Tümünü gör →", onAction = onLeaderboard)
        }
        items(state.leaderboard.take(4), key = { "${it.rank}-${it.displayName}" }) { entry ->
            LeaderboardRow(entry = entry)
        }
    }
}

@Composable
private fun PlayingContent(
    state: GameUiState.Content,
    onCoinTap: (FallingCoin) -> Unit,
    onFinish: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF14112E), Color(0xFF312E81), Color(0xFF581C87)),
                ),
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            HudPill(label = "SKOR", value = formatScore(state.score))
            if (state.combo >= 3) {
                Pill(text = "x${state.combo} Combo!", color = Color(0xFFFFC24D), contentColor = Color(0xFF3A2400))
            }
            TimerCircle(seconds = state.timeLeftSeconds)
        }

        state.coins.forEach { coin ->
            CoinTarget(
                coin = coin,
                onClick = { onCoinTap(coin) },
                modifier = Modifier.offset(
                    x = (maxWidth - 64.dp) * coin.x,
                    y = 96.dp + ((maxHeight - 220.dp) * coin.y),
                ),
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 28.dp)
                .width(132.dp)
                .height(34.dp)
                .clip(RoundedCornerShape(22.dp))
                .background(Color.White.copy(alpha = 0.18f))
                .border(1.dp, Color.White.copy(alpha = 0.38f), RoundedCornerShape(22.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "sepet", color = Color.White.copy(alpha = 0.78f), fontWeight = FontWeight.Bold)
        }

        Surface(
            onClick = onFinish,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp),
            color = Color.White.copy(alpha = 0.16f),
            shape = PillShape,
        ) {
            Text(
                text = "Bitir",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 9.dp),
            )
        }
    }
}

@Composable
private fun ResultContent(
    state: GameUiState.Content,
    onStart: () -> Unit,
    onLeaderboard: () -> Unit,
    onDismissBanner: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        state.bannerMessage?.let { message ->
            item { Banner(message = message, onDismiss = onDismissBanner) }
        }
        item {
            GibCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    Text(text = "🏆", fontSize = 54.sp)
                    Text(
                        text = "Oyun Bitti",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Text(
                        text = formatScore(state.score),
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    PointBadge(points = state.earnedPoints, big = true)
                    Text(
                        text = "Skorun kaydedildi; leaderboard artık biraz daha kızıştı.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                    )
                    GibPrimaryButton(text = "Tekrar Oyna", onClick = onStart, modifier = Modifier.fillMaxWidth())
                    Surface(onClick = onLeaderboard, shape = PillShape, color = MaterialTheme.colorScheme.surfaceVariant) {
                        Text(
                            text = "Leaderboard'a bak",
                            modifier = Modifier.padding(horizontal = 18.dp, vertical = 11.dp),
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LeaderboardContent(
    state: GameUiState.Content,
    onLanding: () -> Unit,
    onDismissBanner: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            HeaderRow(title = "Sıralama", points = state.balance)
        }
        state.bannerMessage?.let { message ->
            item { Banner(message = message, onDismiss = onDismissBanner) }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Pill(text = "Hafta", color = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onPrimary)
                Pill(text = "Tüm", color = MaterialTheme.colorScheme.surfaceVariant, contentColor = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.weight(1f))
                Surface(onClick = onLanding, shape = PillShape, color = MaterialTheme.colorScheme.surfaceVariant) {
                    Text(text = "Oyuna dön", modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp), fontWeight = FontWeight.Bold)
                }
            }
        }
        item {
            Podium(entries = state.leaderboard.take(3))
        }
        items(state.leaderboard, key = { "${it.rank}-${it.displayName}" }) { entry ->
            LeaderboardRow(entry = entry)
        }
    }
}

@Composable
private fun HeaderRow(title: String, points: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold)
        PointBadge(points = points)
    }
}

@Composable
private fun SectionTitle(title: String, action: String, onAction: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.ExtraBold)
        Text(
            text = action,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable(onClick = onAction),
        )
    }
}

@Composable
private fun StatTile(label: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(Color.White.copy(alpha = 0.16f))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(text = label, color = Color.White.copy(alpha = 0.72f), style = MaterialTheme.typography.labelMedium)
        Text(text = value, color = Color.White, fontWeight = FontWeight.ExtraBold)
    }
}

@Composable
private fun LeaderboardRow(entry: LeaderboardEntry) {
    GibCard(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(12.dp),
        accent = if (entry.isCurrentUser) MaterialTheme.colorScheme.primary else null,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            RankBadge(rank = entry.rank)
            Column(modifier = Modifier.weight(1f)) {
                Text(text = entry.displayName, fontWeight = FontWeight.ExtraBold)
                Text(text = if (entry.isCurrentUser) "Senin sıran" else "Bu hafta", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                CoinIcon()
                Text(text = formatScore(entry.score), fontWeight = FontWeight.ExtraBold)
            }
        }
    }
}

@Composable
private fun Podium(entries: List<LeaderboardEntry>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(CardShape)
            .background(Brush.linearGradient(listOf(Color(0xFFFDF2F8), Color(0xFFEDE9FE))))
            .padding(14.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.Bottom,
    ) {
        entries.forEachIndexed { index, entry ->
            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(listOf(104.dp, 132.dp, 92.dp).getOrElse(index) { 88.dp })
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color.White.copy(alpha = 0.75f))
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(text = listOf("🥇", "🥈", "🥉").getOrElse(index) { "🏅" }, fontSize = 24.sp)
                Text(text = entry.displayName, fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center)
                Text(text = formatScore(entry.score), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun CoinTarget(coin: FallingCoin, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(58.dp)
            .clip(RoundedCornerShape(percent = 50))
            .background(Color(0xFFFFF7D6).copy(alpha = 0.22f))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        CoinIcon(size = 42.dp)
    }
}

@Composable
private fun TimerCircle(seconds: Int) {
    Box(
        modifier = Modifier
            .size(58.dp)
            .clip(RoundedCornerShape(percent = 50))
            .background(Color.White.copy(alpha = 0.14f))
            .border(2.dp, Color.White.copy(alpha = 0.42f), RoundedCornerShape(percent = 50)),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "${seconds}sn", color = Color.White, fontWeight = FontWeight.ExtraBold)
    }
}

@Composable
private fun HudPill(label: String, value: String) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(18.dp))
            .background(Color.White.copy(alpha = 0.14f))
            .padding(horizontal = 14.dp, vertical = 9.dp),
    ) {
        Text(text = label, color = Color.White.copy(alpha = 0.70f), style = MaterialTheme.typography.labelSmall)
        Text(text = value, color = Color.White, fontWeight = FontWeight.ExtraBold)
    }
}

@Composable
private fun RankBadge(rank: Int) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(percent = 50))
            .background(
                if (rank <= 3) Color(0xFFFFF2C2) else MaterialTheme.colorScheme.surfaceVariant,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "#$rank", fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
private fun Pill(text: String, color: Color, contentColor: Color) {
    Surface(color = color, shape = PillShape) {
        Text(
            text = text,
            color = contentColor,
            fontWeight = FontWeight.ExtraBold,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
        )
    }
}

@Composable
private fun Banner(message: String, onDismiss: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(GibExtendedTheme.colors.accentSoft)
            .border(1.dp, GibExtendedTheme.colors.accent.copy(alpha = 0.25f), RoundedCornerShape(18.dp))
            .clickable(onClick = onDismiss)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        CoinIcon()
        Text(text = message, fontWeight = FontWeight.Bold, color = GibExtendedTheme.colors.accentDeep)
    }
}

private fun formatScore(score: Int): String =
    "%,d".format(score).replace(',', '.')

@Composable
private fun GameScreenPreview() {
    GibTheme {
        GameScreen(
            uiState = GameUiState.Content(
                bestScore = 1840,
                balance = 1240,
                leaderboard = listOf(
                    LeaderboardEntry(1, "Elif Y.", 1840),
                    LeaderboardEntry(2, "Mert D.", 1720),
                    LeaderboardEntry(3, "Burak S.", 1690),
                ),
            ),
            onStart = {},
            onCoinTap = {},
            onFinish = {},
            onLeaderboard = {},
            onLanding = {},
            onDismissBanner = {},
        )
    }
}
