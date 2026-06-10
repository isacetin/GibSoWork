package com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.usecase

import com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.model.Task
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.model.TaskStatus
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.repository.TasksRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CompleteTaskUseCaseTest {

    private val repository: TasksRepository = mockk()
    private val useCase = CompleteTaskUseCase(repository)

    private val todoTask = Task(
        id = "task-12",
        key = "GIB-12",
        title = "Ödeme servisini yeni API'ye taşı",
        storyPoints = 5,
        status = TaskStatus.TODO,
        reward = 50,
    )

    @Test
    fun `AC-01 - completing a todo task awards storyPoints times POINT_PER_SP`() = runTest {
        coEvery { repository.setTaskStatus(todoTask.id, TaskStatus.DONE) } returns Result.success(Unit)

        val result = useCase(todoTask)

        assertTrue(result.isSuccess)
        val completion = result.getOrThrow()
        assertEquals(50, completion.awardedPoints)
        assertEquals(TaskStatus.DONE, completion.task.status)
        coVerify(exactly = 1) { repository.setTaskStatus(todoTask.id, TaskStatus.DONE) }
    }

    @Test
    fun `AC-02 - completing an already done task is idempotent and awards no points`() = runTest {
        val doneTask = todoTask.copy(status = TaskStatus.DONE)

        val result = useCase(doneTask)

        assertTrue(result.isSuccess)
        val completion = result.getOrThrow()
        assertEquals(0, completion.awardedPoints)
        assertEquals(TaskStatus.DONE, completion.task.status)
        coVerify(exactly = 0) { repository.setTaskStatus(any(), any()) }
    }

    @Test
    fun `repository failure is propagated as Result failure`() = runTest {
        val error = RuntimeException("network error")
        coEvery { repository.setTaskStatus(todoTask.id, TaskStatus.DONE) } returns Result.failure(error)

        val result = useCase(todoTask)

        assertTrue(result.isFailure)
        assertEquals(error, result.exceptionOrNull())
    }
}
