package com.isacetin.gibinteraktifsosyalapp.feature.shop.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** `avatars` row — `equippedItems` maps category (`hat`/`glasses`/`outfit`/`bg`) to an item id. */
@Serializable
data class AvatarDto(
    @SerialName("user_id") val userId: String,
    @SerialName("base_id") val baseId: String,
    @SerialName("equipped_items") val equippedItems: Map<String, String> = emptyMap(),
)
