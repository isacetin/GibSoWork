package com.isacetin.gibinteraktifsosyalapp.feature.events.data.mapper

import com.isacetin.gibinteraktifsosyalapp.feature.events.data.remote.dto.CreateEventRequest
import com.isacetin.gibinteraktifsosyalapp.feature.events.data.remote.dto.EventDto
import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.model.CreateEventInput
import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.model.Event
import java.time.Instant

fun EventDto.toDomain(
    participantCount: Int,
    isJoined: Boolean,
): Event = Event(
    id = id,
    title = title,
    description = description.orEmpty(),
    location = location.orEmpty(),
    startsAt = Instant.parse(startsAt),
    capacity = capacity,
    participantCount = participantCount,
    isJoined = isJoined,
)

fun CreateEventInput.toRequest(createdBy: String): CreateEventRequest = CreateEventRequest(
    title = title,
    description = description,
    location = location,
    startsAt = startsAt.toString(),
    capacity = capacity,
    createdBy = createdBy,
)
