package com.isacetin.gibinteraktifsosyalapp.feature.events.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventDto(
    val id: String,
    val title: String,
    val description: String? = null,
    val location: String? = null,
    @SerialName("starts_at") val startsAt: String,
    val capacity: Int,
    @SerialName("created_by") val createdBy: String? = null,
)
