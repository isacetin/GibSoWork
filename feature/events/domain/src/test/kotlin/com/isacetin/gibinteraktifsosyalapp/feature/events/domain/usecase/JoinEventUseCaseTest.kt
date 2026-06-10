package com.isacetin.gibinteraktifsosyalapp.feature.events.domain.usecase

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
import java.time.Instant

class JoinEventUseCaseTest {
    private val repository: EventsRepository = mockk()
    private val useCase = JoinEventUseCase(repository)

    private val event = Event(
        id = "event-1",
        title = "Cuma Kahve Molası",
        description = "Takım buluşması",
        location = "Teras Kafe",
        startsAt = Instant.parse("2026-06-12T12:00:00Z"),
        capacity = 2,
        participantCount = 1,
        isJoined = false,
    )

    @Test
    fun `TC-06 - max minus one participant can join`() = runTest {
        coEvery { repository.joinEvent(event.id) } returns Result.success(Unit)

        val result = useCase(event)

        assertTrue(result.isSuccess)
        assertTrue(result.getOrThrow().isJoined)
        assertEquals(2, result.getOrThrow().participantCount)
        coVerify(exactly = 1) { repository.joinEvent(event.id) }
    }

    @Test
    fun `TC-06 - full event returns 409-style error without repository call`() = runTest {
        val full = event.copy(participantCount = 2)

        val result = useCase(full)

        assertTrue(result.isFailure)
        val error = result.exceptionOrNull() as EventException.CapacityFull
        assertEquals(409, error.code)
        coVerify(exactly = 0) { repository.joinEvent(any()) }
    }
}
