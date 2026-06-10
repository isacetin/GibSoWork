package com.isacetin.gibinteraktifsosyalapp.feature.shop.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Body for `PATCH /avatars?user_id=eq.{id}` — replaces the full `equipped_items` map. */
@Serializable
data class UpdateAvatarRequest(
    @SerialName("equipped_items") val equippedItems: Map<String, String>,
)
