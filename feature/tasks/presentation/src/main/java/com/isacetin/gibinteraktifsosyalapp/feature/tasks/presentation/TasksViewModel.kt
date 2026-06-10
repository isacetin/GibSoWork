package com.isacetin.gibinteraktifsosyalapp.feature.tasks.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.model.Task
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.model.TaskStatus
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.usecase.CompleteTaskUseCase
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.usecase.GetTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val completeTaskUseCase: CompleteTaskUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<TasksUiState>(TasksUiState.Loading)
    val uiState: StateFlow<TasksUiState> = _uiState.asStateFlow()

    init {
        loadTasks()
    }

    fun loadTasks() {
        viewModelScope.launch {
            _uiState.value = TasksUiState.Loading
            getTasksUseCase()
                .onSuccess { overview ->
                    _uiState.value = if (overview.tasks.isEmpty()) {
                        TasksUiState.Empty
                    } else {
                        TasksUiState.Content(tasks = overview.tasks, balance = overview.balance)
                    }
                }
                .onFailure { error ->
                    _uiState.value = TasksUiState.Error(error.message ?: "Bir hata oluştu")
                }
        }
    }

    fun completeTask(task: Task) = setTaskStatus(task, TaskStatus.DONE)

    fun setTaskStatus(task: Task, status: TaskStatus) {
        viewModelScope.launch {
            if (status == TaskStatus.DONE) {
                completeTaskUseCase(task)
                    .onSuccess { result ->
                        _uiState.update { state ->
                            if (state !is TasksUiState.Content) return@update state
                            val updatedTasks = state.tasks.map {
                                if (it.id == result.task.id) result.task else it
                            }
                            state.copy(
                                tasks = updatedTasks,
                                balance = state.balance + result.awardedPoints,
                                rewardAmount = result.awardedPoints.takeIf { it > 0 },
                            )
                        }
                    }
            } else {
                _uiState.update { state ->
                    if (state !is TasksUiState.Content) return@update state
                    val updatedTasks = state.tasks.map {
                        if (it.id == task.id) it.copy(status = status) else it
                    }
                    state.copy(tasks = updatedTasks)
                }
            }
        }
    }

    fun setFilter(filter: String) {
        _uiState.update { state ->
            if (state is TasksUiState.Content) state.copy(selectedFilter = filter) else state
        }
    }

    fun consumeReward() {
        _uiState.update { state ->
            if (state is TasksUiState.Content) state.copy(rewardAmount = null) else state
        }
    }
}
