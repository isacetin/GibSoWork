package com.isacetin.gibinteraktifsosyalapp.feature.shop.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** `shop_items` row (see docs/03_MIMARI_PLAN.md). */
@Serializable
data class ShopItemDto(
    val id: String,
    val name: String,
    val category: String,
    val price: Int,
    val rarity: String,
    @SerialName("asset_key") val assetKey: String,
)
