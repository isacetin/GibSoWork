package com.isacetin.gibinteraktifsosyalapp.feature.tasks.data.repository

import com.isacetin.gibinteraktifsosyalapp.core.common.Constants
import com.isacetin.gibinteraktifsosyalapp.core.common.toResult
import com.isacetin.gibinteraktifsosyalapp.core.network.safeApiCall
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.data.mapper.toDomain
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.data.remote.TaskApi
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.data.remote.dto.SetTaskStatusRequest
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.model.Task
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.model.TaskStatus
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.repository.TasksRepository
import javax.inject.Inject

class TasksRepositoryImpl @Inject constructor(
    private val api: TaskApi,
) : TasksRepository {

    override suspend fun getTasks(): Result<List<Task>> =
        safeApiCall { api.getTasks(assigneeId = "eq.${Constants.DEMO_USER_ID}") }
            .toResult()
            .map { dtos -> dtos.map { it.toDomain() } }

    override suspend fun getBalance(): Result<Int> =
        safeApiCall { api.getUsers(id = "eq.${Constants.DEMO_USER_ID}") }
            .toResult()
            .map { users -> users.firstOrNull()?.pointsBalance ?: 0 }

    override suspend fun setTaskStatus(taskId: String, status: TaskStatus): Result<Unit> =
        safeApiCall {
            api.setTaskStatus(SetTaskStatusRequest(taskId = taskId, status = status.apiValue))
        }.toResult()
}
