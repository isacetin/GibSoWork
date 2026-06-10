package com.isacetin.gibinteraktifsosyalapp.feature.events.domain.repository

import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.model.CreateEventInput
import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.model.Event

interface EventsRepository {
    suspend fun getEvents(): Result<List<Event>>
    suspend fun getBalance(): Result<Int>
    suspend fun createEvent(input: CreateEventInput): Result<Event>
    suspend fun joinEvent(eventId: String): Result<Unit>
    suspend fun leaveEvent(eventId: String): Result<Unit>
}
