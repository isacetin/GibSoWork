package com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.model

/**
 * Result of [com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.usecase.CompleteTaskUseCase].
 * [awardedPoints] is 0 when the task was already done (idempotent re-completion, AC-02).
 */
data class TaskCompletionResult(
    val task: Task,
    val awardedPoints: Int,
)
