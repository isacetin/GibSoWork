package com.isacetin.gibinteraktifsosyalapp.feature.shop.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** `user_items` row — identifies an item the current user already owns. */
@Serializable
data class UserItemDto(
    @SerialName("item_id") val itemId: String,
)
