package com.isacetin.gibinteraktifsosyalapp.feature.game.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LeaderboardEntryDto(
    @SerialName("display_name") val displayName: String,
    val score: Int,
)
