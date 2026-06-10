package com.isacetin.gibinteraktifsosyalapp.feature.events.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateEventRequest(
    val title: String,
    val description: String,
    val location: String,
    @SerialName("starts_at") val startsAt: String,
    val capacity: Int,
    @SerialName("created_by") val createdBy: String,
)
