package com.isacetin.gibinteraktifsosyalapp.feature.events.data.repository

import com.isacetin.gibinteraktifsosyalapp.core.common.ApiException
import com.isacetin.gibinteraktifsosyalapp.core.common.Constants
import com.isacetin.gibinteraktifsosyalapp.core.common.toResult
import com.isacetin.gibinteraktifsosyalapp.core.network.safeApiCall
import com.isacetin.gibinteraktifsosyalapp.feature.events.data.mapper.toDomain
import com.isacetin.gibinteraktifsosyalapp.feature.events.data.mapper.toRequest
import com.isacetin.gibinteraktifsosyalapp.feature.events.data.remote.EventsApi
import com.isacetin.gibinteraktifsosyalapp.feature.events.data.remote.dto.JoinEventRequest
import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.model.CreateEventInput
import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.model.Event
import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.model.EventException
import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.repository.EventsRepository
import javax.inject.Inject

class EventsRepositoryImpl @Inject constructor(
    private val api: EventsApi,
) : EventsRepository {

    override suspend fun getEvents(): Result<List<Event>> {
        val events = safeApiCall { api.getEvents() }.toResult()
            .getOrElse { return Result.failure(it) }
        val participants = safeApiCall { api.getEventParticipants() }.toResult()
            .getOrElse { return Result.failure(it) }

        val countsByEventId = participants.groupingBy { it.eventId }.eachCount()
        val joinedEventIds = participants
            .filter { it.userId == Constants.DEMO_USER_ID }
            .map { it.eventId }
            .toSet()

        return Result.success(
            events.map { dto ->
                dto.toDomain(
                    participantCount = countsByEventId[dto.id] ?: 0,
                    isJoined = dto.id in joinedEventIds,
                )
            },
        )
    }

    override suspend fun getBalance(): Result<Int> =
        safeApiCall { api.getUsers(id = "eq.${Constants.DEMO_USER_ID}") }
            .toResult()
            .map { users -> users.firstOrNull()?.pointsBalance ?: 0 }

    override suspend fun createEvent(input: CreateEventInput): Result<Event> =
        safeApiCall {
            api.createEvent(input.toRequest(createdBy = Constants.DEMO_USER_ID))
        }
            .toResult()
            .fold(
                onSuccess = { created ->
                    created.firstOrNull()
                        ?.toDomain(participantCount = 0, isJoined = false)
                        ?.let { Result.success(it) }
                        ?: Result.failure(IllegalStateException("Etkinlik oluşturuldu ama yanıt boş döndü"))
                },
                onFailure = { Result.failure(it.toEventException()) },
            )

    override suspend fun joinEvent(eventId: String): Result<Unit> =
        safeApiCall { api.joinEvent(JoinEventRequest(eventId = eventId)) }
            .toResult()
            .fold(onSuccess = { Result.success(Unit) }, onFailure = { Result.failure(it.toEventException()) })

    override suspend fun leaveEvent(eventId: String): Result<Unit> =
        safeApiCall {
            api.leaveEvent(
                eventId = "eq.$eventId",
                userId = "eq.${Constants.DEMO_USER_ID}",
            )
        }.toResult()

    private fun Throwable.toEventException(): Throwable =
        if (this is ApiException) {
            when (code) {
                400 -> EventException.Validation(message ?: "Geçersiz etkinlik")
                409 -> EventException.CapacityFull
                else -> this
            }
        } else {
            this
        }
}
