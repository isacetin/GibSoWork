package com.isacetin.gibinteraktifsosyalapp.feature.game.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PointTransactionRequest(
    @SerialName("user_id") val userId: String,
    val amount: Int,
    val source: String,
    @SerialName("ref_id") val refId: String,
)
