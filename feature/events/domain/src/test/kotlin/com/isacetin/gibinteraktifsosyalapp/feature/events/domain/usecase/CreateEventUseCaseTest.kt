package com.isacetin.gibinteraktifsosyalapp.feature.events.domain.usecase

import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.model.CreateEventInput
import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.model.Event
import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.model.EventException
import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.repository.EventsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

class CreateEventUseCaseTest {
    private val repository: EventsRepository = mockk()
    private val clock = Clock.fixed(Instant.parse("2026-06-10T10:00:00Z"), ZoneOffset.UTC)
    private val useCase = CreateEventUseCase(repository, clock)

    @Test
    fun `TC-07 - title at min boundary creates event`() = runTest {
        val input = input(title = "A")
        coEvery { repository.createEvent(input) } returns Result.success(event(title = "A"))

        val result = useCase(input)

        assertTrue(result.isSuccess)
        assertEquals("A", result.getOrThrow().title)
        coVerify(exactly = 1) { repository.createEvent(input) }
    }

    @Test
    fun `TC-07 - blank title returns 400-style validation error`() = runTest {
        val result = useCase(input(title = ""))

        assertTrue(result.isFailure)
        val error = result.exceptionOrNull() as EventException.Validation
        assertEquals(400, error.code)
        assertEquals("Başlık boş olamaz", error.message)
        coVerify(exactly = 0) { repository.createEvent(any()) }
    }

    @Test
    fun `TC-07 - past startsAt returns validation error`() = runTest {
        val result = useCase(input(startsAt = Instant.parse("2026-06-10T09:59:59Z")))

        assertTrue(result.isFailure)
        assertEquals("Geçmiş tarih", result.exceptionOrNull()?.message)
        coVerify(exactly = 0) { repository.createEvent(any()) }
    }

    private fun input(
        title: String = "Cuma Kahve Molası",
        startsAt: Instant = Instant.parse("2026-06-12T12:00:00Z"),
    ) = CreateEventInput(
        title = title,
        description = "Haftalık takım buluşması",
        location = "Teras Kafe",
        startsAt = startsAt,
        capacity = 12,
    )

    private fun event(title: String) = Event(
        id = "event-1",
        title = title,
        description = "Haftalık takım buluşması",
        location = "Teras Kafe",
        startsAt = Instant.parse("2026-06-12T12:00:00Z"),
        capacity = 12,
        participantCount = 0,
        isJoined = false,
    )
}
