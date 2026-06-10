package com.isacetin.gibinteraktifsosyalapp.feature.events.domain.usecase

import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.model.EventsOverview
import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.repository.EventsRepository
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(
    private val repository: EventsRepository,
) {
    suspend operator fun invoke(): Result<EventsOverview> {
        val events = repository.getEvents().getOrElse { return Result.failure(it) }
        val balance = repository.getBalance().getOrElse { return Result.failure(it) }
        return Result.success(EventsOverview(events = events, balance = balance))
    }
}
