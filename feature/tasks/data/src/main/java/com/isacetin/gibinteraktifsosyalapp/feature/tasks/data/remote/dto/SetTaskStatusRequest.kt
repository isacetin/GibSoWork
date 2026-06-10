package com.isacetin.gibinteraktifsosyalapp.feature.tasks.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Request body for `PATCH /rest/v1/rpc/set_task_status`. */
@Serializable
data class SetTaskStatusRequest(
    @SerialName("task_id") val taskId: String,
    val status: String,
)
