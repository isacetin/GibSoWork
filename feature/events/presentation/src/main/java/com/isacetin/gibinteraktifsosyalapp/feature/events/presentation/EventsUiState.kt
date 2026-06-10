package com.isacetin.gibinteraktifsosyalapp.feature.events.presentation

import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.model.Event

sealed interface EventsUiState {
    data object Loading : EventsUiState

    data class Content(
        val events: List<Event>,
        val balance: Int,
        val selectedEvent: Event? = null,
        val showCreateForm: Boolean = false,
        val bannerMessage: String? = null,
        val createForm: CreateEventForm = CreateEventForm(),
    ) : EventsUiState

    data class Error(val message: String) : EventsUiState
}

data class CreateEventForm(
    val title: String = "",
    val description: String = "Haftalık takım buluşması ☕",
    val location: String = "",
    val capacity: Int = 12,
    val titleError: String? = null,
    val dateError: String? = null,
)
