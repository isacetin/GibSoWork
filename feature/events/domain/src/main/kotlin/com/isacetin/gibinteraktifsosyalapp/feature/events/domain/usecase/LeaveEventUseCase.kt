package com.isacetin.gibinteraktifsosyalapp.feature.events.domain.usecase

import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.model.Event
import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.repository.EventsRepository
import javax.inject.Inject

class LeaveEventUseCase @Inject constructor(
    private val repository: EventsRepository,
) {
    suspend operator fun invoke(event: Event): Result<Event> {
        if (!event.isJoined) {
            return Result.success(event)
        }
        return repository.leaveEvent(event.id).map {
            event.copy(isJoined = false, participantCount = (event.participantCount - 1).coerceAtLeast(0))
        }
    }
}
