package com.isacetin.gibinteraktifsosyalapp.feature.tasks.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Mirrors a row of the `jira_tasks` table (see docs/03_MIMARI_PLAN.md). */
@Serializable
data class TaskDto(
    val id: String,
    val key: String,
    val title: String,
    @SerialName("story_points") val storyPoints: Int,
    val status: String,
    @SerialName("points_awarded") val pointsAwarded: Boolean,
)
