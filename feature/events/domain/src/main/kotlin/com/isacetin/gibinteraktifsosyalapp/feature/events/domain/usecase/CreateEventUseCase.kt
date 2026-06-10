package com.isacetin.gibinteraktifsosyalapp.feature.events.domain.usecase

import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.model.CreateEventInput
import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.model.Event
import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.model.EventConstraints
import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.model.EventException
import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.repository.EventsRepository
import java.time.Clock
import javax.inject.Inject

class CreateEventUseCase(
    private val repository: EventsRepository,
    private val clock: Clock = Clock.systemUTC(),
) {
    @Inject
    constructor(repository: EventsRepository) : this(repository, Clock.systemUTC())

    suspend operator fun invoke(input: CreateEventInput): Result<Event> {
        val titleLength = input.title.trim().length
        if (titleLength !in EventConstraints.TITLE_MIN_LENGTH..EventConstraints.TITLE_MAX_LENGTH) {
            return Result.failure(EventException.Validation("Başlık boş olamaz"))
        }
        if (!input.startsAt.isAfter(clock.instant())) {
            return Result.failure(EventException.Validation("Geçmiş tarih"))
        }
        if (input.capacity !in EventConstraints.CAPACITY_MIN..EventConstraints.CAPACITY_MAX) {
            return Result.failure(EventException.Validation("Kapasite ${EventConstraints.CAPACITY_MIN}-${EventConstraints.CAPACITY_MAX} arasında olmalı"))
        }
        return repository.createEvent(input.copy(title = input.title.trim()))
    }
}
