package com.isacetin.gibinteraktifsosyalapp.feature.shop.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** `users` row, projected to just the points balance. */
@Serializable
data class UserBalanceDto(
    val id: String,
    @SerialName("points_balance") val pointsBalance: Int,
)
