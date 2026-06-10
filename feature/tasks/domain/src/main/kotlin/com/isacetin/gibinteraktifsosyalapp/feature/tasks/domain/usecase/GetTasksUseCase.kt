package com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.usecase

import com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.model.TasksOverview
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.repository.TasksRepository
import javax.inject.Inject

/** Loads the task list together with the user's current points balance. */
class GetTasksUseCase @Inject constructor(
    private val repository: TasksRepository,
) {
    suspend operator fun invoke(): Result<TasksOverview> {
        val tasks = repository.getTasks().getOrElse { return Result.failure(it) }
        val balance = repository.getBalance().getOrElse { return Result.failure(it) }
        return Result.success(TasksOverview(tasks = tasks, balance = balance))
    }
}
