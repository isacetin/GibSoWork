package com.isacetin.gibinteraktifsosyalapp.feature.events.domain.usecase

import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.model.Event
import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.model.EventException
import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.repository.EventsRepository
import javax.inject.Inject

class JoinEventUseCase @Inject constructor(
    private val repository: EventsRepository,
) {
    suspend operator fun invoke(event: Event): Result<Event> {
        if (event.isJoined) {
            return Result.failure(EventException.AlreadyJoined)
        }
        if (event.isFull) {
            return Result.failure(EventException.CapacityFull)
        }
        return repository.joinEvent(event.id).map {
            event.copy(isJoined = true, participantCount = event.participantCount + 1)
        }
    }
}
