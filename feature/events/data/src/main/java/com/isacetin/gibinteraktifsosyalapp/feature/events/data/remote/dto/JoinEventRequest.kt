package com.isacetin.gibinteraktifsosyalapp.feature.events.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JoinEventRequest(
    @SerialName("event_id") val eventId: String,
)
