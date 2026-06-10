package com.isacetin.gibinteraktifsosyalapp.feature.game.domain.usecase

import com.isacetin.gibinteraktifsosyalapp.feature.game.domain.model.GameConstants
import com.isacetin.gibinteraktifsosyalapp.feature.game.domain.model.LeaderboardEntry
import com.isacetin.gibinteraktifsosyalapp.feature.game.domain.repository.GameRepository
import javax.inject.Inject

data class GameOverview(
    val bestScore: Int,
    val balance: Int,
    val leaderboard: List<LeaderboardEntry>,
)

class GetGameOverviewUseCase @Inject constructor(
    private val repository: GameRepository,
    private val getLeaderboard: GetLeaderboardUseCase,
) {
    suspend operator fun invoke(): Result<GameOverview> {
        val bestScore = repository.getBestScore().getOrElse { 0 }
        val balance = repository.getBalance().getOrElse { 0 }
        val leaderboard = getLeaderboard(GameConstants.LEADERBOARD_LIMIT).getOrElse { return Result.failure(it) }
        return Result.success(GameOverview(bestScore = bestScore, balance = balance, leaderboard = leaderboard))
    }
}
