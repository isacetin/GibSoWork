package com.isacetin.gibinteraktifsosyalapp.feature.game.domain.repository

import com.isacetin.gibinteraktifsosyalapp.feature.game.domain.model.LeaderboardEntry

interface GameRepository {
    suspend fun getLeaderboard(limit: Int): Result<List<LeaderboardEntry>>
    suspend fun getBestScore(): Result<Int>
    suspend fun getBalance(): Result<Int>
    suspend fun submitScore(gameId: String, score: Int, earnedPoints: Int): Result<Unit>
}
