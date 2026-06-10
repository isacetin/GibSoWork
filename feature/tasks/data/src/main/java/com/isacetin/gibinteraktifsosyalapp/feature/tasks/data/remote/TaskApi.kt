package com.isacetin.gibinteraktifsosyalapp.feature.tasks.data.remote

import com.isacetin.gibinteraktifsosyalapp.feature.tasks.data.remote.dto.SetTaskStatusRequest
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.data.remote.dto.TaskDto
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.data.remote.dto.UserBalanceDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

/** Supabase PostgREST endpoints used by `:feature:tasks` (see docs/02_API_TEST.md). */
interface TaskApi {

    @GET("jira_tasks")
    suspend fun getTasks(@Query("assignee_id") assigneeId: String): List<TaskDto>

    @GET("users")
    suspend fun getUsers(@Query("id") id: String): List<UserBalanceDto>

    @Headers("Prefer: return=minimal")
    @POST("rpc/set_task_status")
    suspend fun setTaskStatus(@Body request: SetTaskStatusRequest)
}
