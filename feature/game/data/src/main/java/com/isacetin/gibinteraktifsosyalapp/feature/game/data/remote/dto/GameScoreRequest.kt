package com.isacetin.gibinteraktifsosyalapp.feature.game.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameScoreRequest(
    @SerialName("user_id") val userId: String,
    @SerialName("game_id") val gameId: String,
    val score: Int,
)
