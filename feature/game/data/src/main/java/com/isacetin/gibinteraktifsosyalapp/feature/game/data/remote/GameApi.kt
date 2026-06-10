package com.isacetin.gibinteraktifsosyalapp.feature.game.data.remote

import com.isacetin.gibinteraktifsosyalapp.feature.game.data.remote.dto.GameScoreDto
import com.isacetin.gibinteraktifsosyalapp.feature.game.data.remote.dto.GameScoreRequest
import com.isacetin.gibinteraktifsosyalapp.feature.game.data.remote.dto.LeaderboardEntryDto
import com.isacetin.gibinteraktifsosyalapp.feature.game.data.remote.dto.PointTransactionRequest
import com.isacetin.gibinteraktifsosyalapp.feature.game.data.remote.dto.UserBalanceDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface GameApi {
    @GET("leaderboard")
    suspend fun getLeaderboard(
        @Query("order") order: String = "score.desc",
        @Query("limit") limit: Int = 20,
    ): List<LeaderboardEntryDto>

    @GET("game_scores")
    suspend fun getGameScores(
        @Query("user_id") userId: String,
        @Query("game_id") gameId: String,
        @Query("order") order: String = "score.desc",
        @Query("limit") limit: Int = 1,
    ): List<GameScoreDto>

    @GET("users")
    suspend fun getUsers(
        @Query("id") id: String,
        @Query("select") select: String = "id,points_balance",
    ): List<UserBalanceDto>

    @Headers("Prefer: return=representation")
    @POST("game_scores")
    suspend fun submitScore(@Body request: GameScoreRequest): List<GameScoreDto>

    @Headers("Prefer: return=minimal")
    @POST("point_transactions")
    suspend fun addPointTransaction(@Body request: PointTransactionRequest)
}
