package com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.repository

import com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.model.Task
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.model.TaskStatus

interface TasksRepository {
    /** Tasks assigned to the current user (`GET /jira_tasks?assignee_id=eq.{id}`). */
    suspend fun getTasks(): Result<List<Task>>

    /** Current user's points balance (`GET /users?id=eq.{id}`). */
    suspend fun getBalance(): Result<Int>

    /** `PATCH /rpc/set_task_status` — server applies the idempotent point award. */
    suspend fun setTaskStatus(taskId: String, status: TaskStatus): Result<Unit>
}
