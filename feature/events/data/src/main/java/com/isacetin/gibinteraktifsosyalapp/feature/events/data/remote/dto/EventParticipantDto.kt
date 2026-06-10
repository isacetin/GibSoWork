package com.isacetin.gibinteraktifsosyalapp.feature.events.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventParticipantDto(
    @SerialName("event_id") val eventId: String,
    @SerialName("user_id") val userId: String,
)
