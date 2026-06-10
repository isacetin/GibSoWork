package com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.model

/** Combined result of [com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.usecase.GetTasksUseCase]. */
data class TasksOverview(
    val tasks: List<Task>,
    val balance: Int,
)
