package com.isacetin.gibinteraktifsosyalapp.feature.tasks.data.mapper

import com.isacetin.gibinteraktifsosyalapp.core.common.Constants
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.data.remote.dto.TaskDto
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.model.Task
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.model.TaskStatus

/** Maps a [TaskDto] from the network layer to the [Task] domain model. */
fun TaskDto.toDomain(): Task = Task(
    id = id,
    key = key,
    title = title,
    storyPoints = storyPoints,
    status = TaskStatus.fromApiValue(status),
    reward = storyPoints * Constants.POINT_PER_SP,
)
