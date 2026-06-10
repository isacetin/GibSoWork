package com.isacetin.gibinteraktifsosyalapp.feature.shop.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Body for `POST /rpc/purchase_item`. */
@Serializable
data class PurchaseItemRequest(
    @SerialName("item_id") val itemId: String,
)
