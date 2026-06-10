package com.isacetin.gibinteraktifsosyalapp.feature.events.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserBalanceDto(
    val id: String,
    @SerialName("points_balance") val pointsBalance: Int,
)
