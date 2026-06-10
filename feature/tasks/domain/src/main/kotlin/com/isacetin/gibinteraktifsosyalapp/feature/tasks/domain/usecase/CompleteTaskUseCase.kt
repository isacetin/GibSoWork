package com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.usecase

import com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.model.Task
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.model.TaskCompletionResult
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.model.TaskStatus
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.repository.TasksRepository
import javax.inject.Inject

/**
 * Marks a task as "done" and reports the points earned.
 *
 * Idempotency guard (AC-02): if [task] is already [TaskStatus.DONE], the repository
 * is not called again and 0 points are awarded.
 */
class CompleteTaskUseCase @Inject constructor(
    private val repository: TasksRepository,
) {
    suspend operator fun invoke(task: Task): Result<TaskCompletionResult> {
        if (task.status == TaskStatus.DONE) {
            return Result.success(TaskCompletionResult(task = task, awardedPoints = 0))
        }

        return repository.setTaskStatus(task.id, TaskStatus.DONE).map {
            TaskCompletionResult(
                task = task.copy(status = TaskStatus.DONE),
                awardedPoints = task.reward,
            )
        }
    }
}
