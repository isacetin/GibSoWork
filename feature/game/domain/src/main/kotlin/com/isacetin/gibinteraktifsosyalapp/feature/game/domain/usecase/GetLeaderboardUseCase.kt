package com.isacetin.gibinteraktifsosyalapp.feature.game.domain.usecase

import com.isacetin.gibinteraktifsosyalapp.feature.game.domain.model.GameConstants
import com.isacetin.gibinteraktifsosyalapp.feature.game.domain.model.LeaderboardEntry
import com.isacetin.gibinteraktifsosyalapp.feature.game.domain.repository.GameRepository
import javax.inject.Inject

class GetLeaderboardUseCase @Inject constructor(
    private val repository: GameRepository,
) {
    suspend operator fun invoke(limit: Int = GameConstants.LEADERBOARD_LIMIT): Result<List<LeaderboardEntry>> =
        repository.getLeaderboard(limit)
            .map { entries ->
                entries
                    .sortedByDescending { it.score }
                    .take(limit)
                    .mapIndexed { index, entry -> entry.copy(rank = index + 1) }
            }
}
