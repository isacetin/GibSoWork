package com.isacetin.gibinteraktifsosyalapp.feature.tasks.presentation

import com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.model.Task

/** UI state for the Tasks ("Görevler") screen. */
sealed interface TasksUiState {

    data object Loading : TasksUiState

    data class Content(
        val tasks: List<Task>,
        val balance: Int,
        val rewardAmount: Int? = null,
    ) : TasksUiState

    data object Empty : TasksUiState

    data class Error(val message: String) : TasksUiState
}
