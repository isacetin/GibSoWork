package com.isacetin.gibinteraktifsosyalapp.feature.events.domain.model

import java.time.Instant

data class CreateEventInput(
    val title: String,
    val description: String,
    val location: String,
    val startsAt: Instant,
    val capacity: Int,
)
