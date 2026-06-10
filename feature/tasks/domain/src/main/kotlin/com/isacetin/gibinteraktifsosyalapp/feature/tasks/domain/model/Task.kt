package com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.model

/**
 * A single Jira task assigned to the current user.
 * [reward] is the points granted once the task is marked [TaskStatus.DONE]
 * (`storyPoints * Constants.POINT_PER_SP`).
 */
data class Task(
    val id: String,
    val key: String,
    val title: String,
    val storyPoints: Int,
    val status: TaskStatus,
    val reward: Int,
)
