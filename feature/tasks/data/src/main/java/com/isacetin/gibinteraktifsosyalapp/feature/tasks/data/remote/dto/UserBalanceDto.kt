package com.isacetin.gibinteraktifsosyalapp.feature.tasks.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Mirrors the points-balance fields of a row of the `users` table. */
@Serializable
data class UserBalanceDto(
    val id: String,
    @SerialName("points_balance") val pointsBalance: Int,
)
