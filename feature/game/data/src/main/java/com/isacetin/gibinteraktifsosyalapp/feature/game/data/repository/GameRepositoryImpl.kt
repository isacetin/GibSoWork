package com.isacetin.gibinteraktifsosyalapp.feature.game.data.repository

import com.isacetin.gibinteraktifsosyalapp.core.common.ApiException
import com.isacetin.gibinteraktifsosyalapp.core.common.Constants
import com.isacetin.gibinteraktifsosyalapp.core.common.toResult
import com.isacetin.gibinteraktifsosyalapp.core.network.safeApiCall
import com.isacetin.gibinteraktifsosyalapp.feature.game.data.mapper.toDomain
import com.isacetin.gibinteraktifsosyalapp.feature.game.data.remote.GameApi
import com.isacetin.gibinteraktifsosyalapp.feature.game.data.remote.dto.GameScoreRequest
import com.isacetin.gibinteraktifsosyalapp.feature.game.data.remote.dto.PointTransactionRequest
import com.isacetin.gibinteraktifsosyalapp.feature.game.domain.model.GameConstants
import com.isacetin.gibinteraktifsosyalapp.feature.game.domain.model.GameException
import com.isacetin.gibinteraktifsosyalapp.feature.game.domain.model.LeaderboardEntry
import com.isacetin.gibinteraktifsosyalapp.feature.game.domain.repository.GameRepository
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val api: GameApi,
) : GameRepository {

    override suspend fun getLeaderboard(limit: Int): Result<List<LeaderboardEntry>> =
        safeApiCall { api.getLeaderboard(limit = limit) }
            .toResult()
            .map { entries -> entries.mapIndexed { index, dto -> dto.toDomain(rank = index + 1) } }

    override suspend fun getBestScore(): Result<Int> =
        safeApiCall {
            api.getGameScores(
                userId = "eq.${Constants.DEMO_USER_ID}",
                gameId = "eq.${GameConstants.GAME_ID}",
            )
        }.toResult().map { scores -> scores.firstOrNull()?.score ?: 0 }

    override suspend fun getBalance(): Result<Int> =
        safeApiCall { api.getUsers(id = "eq.${Constants.DEMO_USER_ID}") }
            .toResult()
            .map { users -> users.firstOrNull()?.pointsBalance ?: 0 }

    override suspend fun submitScore(gameId: String, score: Int, earnedPoints: Int): Result<Unit> =
        safeApiCall {
            api.submitScore(
                GameScoreRequest(
                    userId = Constants.DEMO_USER_ID,
                    gameId = gameId,
                    score = score,
                ),
            )
        }.toResult().fold(
            onSuccess = {
                if (earnedPoints <= 0) {
                    Result.success(Unit)
                } else {
                    safeApiCall {
                        api.addPointTransaction(
                            PointTransactionRequest(
                                userId = Constants.DEMO_USER_ID,
                                amount = earnedPoints,
                                source = "game",
                                refId = gameId,
                            ),
                        )
                    }.toResult()
                }
            },
            onFailure = { Result.failure(it.toGameException()) },
        )

    private fun Throwable.toGameException(): Throwable =
        if (this is ApiException && code == 400) {
            GameException.Validation(message ?: "Geçersiz oyun skoru")
        } else {
            this
        }
}
