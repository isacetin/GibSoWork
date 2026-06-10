package com.isacetin.gibinteraktifsosyalapp.feature.events.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.model.CreateEventInput
import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.model.Event
import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.model.EventException
import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.usecase.CreateEventUseCase
import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.usecase.GetEventsUseCase
import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.usecase.JoinEventUseCase
import com.isacetin.gibinteraktifsosyalapp.feature.events.domain.usecase.LeaveEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase,
    private val createEventUseCase: CreateEventUseCase,
    private val joinEventUseCase: JoinEventUseCase,
    private val leaveEventUseCase: LeaveEventUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<EventsUiState>(EventsUiState.Loading)
    val uiState: StateFlow<EventsUiState> = _uiState.asStateFlow()

    init {
        loadEvents()
    }

    fun loadEvents() {
        viewModelScope.launch {
            _uiState.value = EventsUiState.Loading
            getEventsUseCase()
                .onSuccess { overview ->
                    _uiState.value = EventsUiState.Content(
                        events = overview.events.ifEmpty { seedEvents() },
                        balance = overview.balance,
                    )
                }
                .onFailure { error ->
                    _uiState.value = EventsUiState.Error(error.message ?: "Etkinlikler yüklenemedi")
                }
        }
    }

    fun selectEvent(event: Event) {
        updateContent { it.copy(selectedEvent = event, showCreateForm = false, bannerMessage = null) }
    }

    fun closeDetail() {
        updateContent { it.copy(selectedEvent = null) }
    }

    fun showCreateForm() {
        updateContent { it.copy(showCreateForm = true, selectedEvent = null, createForm = CreateEventForm()) }
    }

    fun dismissCreateForm() {
        updateContent { it.copy(showCreateForm = false) }
    }

    fun dismissBanner() {
        updateContent { it.copy(bannerMessage = null) }
    }

    fun updateTitle(value: String) {
        updateContent { it.copy(createForm = it.createForm.copy(title = value, titleError = null)) }
    }

    fun updateDescription(value: String) {
        updateContent { it.copy(createForm = it.createForm.copy(description = value)) }
    }

    fun updateLocation(value: String) {
        updateContent { it.copy(createForm = it.createForm.copy(location = value)) }
    }

    fun increaseCapacity() {
        updateContent { it.copy(createForm = it.createForm.copy(capacity = (it.createForm.capacity + 1).coerceAtMost(500))) }
    }

    fun decreaseCapacity() {
        updateContent { it.copy(createForm = it.createForm.copy(capacity = (it.createForm.capacity - 1).coerceAtLeast(1))) }
    }

    fun createEvent() {
        val state = _uiState.value as? EventsUiState.Content ?: return
        val form = state.createForm
        val input = CreateEventInput(
            title = form.title,
            description = form.description,
            location = form.location,
            startsAt = Instant.now().plusSeconds(2 * 24 * 60 * 60),
            capacity = form.capacity,
        )

        viewModelScope.launch {
            createEventUseCase(input)
                .onSuccess { event ->
                    updateContent {
                        it.copy(
                            events = listOf(event) + it.events,
                            showCreateForm = false,
                            bannerMessage = "Etkinlik yayınlandı",
                        )
                    }
                }
                .onFailure { error ->
                    updateContent {
                        it.copy(
                            createForm = it.createForm.copy(
                                titleError = if (error.message == "Başlık boş olamaz") error.message else null,
                                dateError = if (error.message == "Geçmiş tarih") error.message else null,
                            ),
                            bannerMessage = if (error.message !in setOf("Başlık boş olamaz", "Geçmiş tarih")) {
                                error.message ?: "Etkinlik oluşturulamadı"
                            } else {
                                null
                            },
                        )
                    }
                }
        }
    }

    fun toggleJoin(event: Event) {
        viewModelScope.launch {
            if (event.isJoined) {
                leaveEventUseCase(event).onSuccess { updated -> replaceEvent(updated) }
            } else {
                joinEventUseCase(event)
                    .onSuccess { updated ->
                        replaceEvent(updated)
                        updateContent { it.copy(balance = it.balance + updated.rewardPoints, bannerMessage = "+${updated.rewardPoints} puan kazandın") }
                    }
                    .onFailure { error ->
                        updateContent { it.copy(bannerMessage = error.toEventMessage()) }
                    }
            }
        }
    }

    private fun replaceEvent(event: Event) {
        updateContent { state ->
            state.copy(
                events = state.events.map { if (it.id == event.id) event else it },
                selectedEvent = state.selectedEvent?.let { if (it.id == event.id) event else it },
            )
        }
    }

    private fun updateContent(transform: (EventsUiState.Content) -> EventsUiState.Content) {
        _uiState.update { state -> if (state is EventsUiState.Content) transform(state) else state }
    }

    private fun Throwable.toEventMessage(): String = when (this) {
        EventException.CapacityFull -> "Bu etkinlik dolu — bekleme listesine yazıl."
        EventException.AlreadyJoined -> message.orEmpty()
        is EventException.Validation -> message
        else -> message ?: "İşlem tamamlanamadı"
    }

    private fun seedEvents(): List<Event> = listOf(
        Event(
            id = "seed-coffee",
            title = "Cuma Kahve Molası",
            description = "Haftanın yorgunluğunu birlikte atalım! Taze kahve, atıştırmalık ve bol sohbet.",
            location = "Teras Kafe, 4. kat",
            startsAt = Instant.now().plusSeconds(2 * 24 * 60 * 60),
            capacity = 12,
            participantCount = 8,
            isJoined = true,
        ),
        Event(
            id = "seed-game",
            title = "Ofis Oyun Turnuvası",
            description = "Sosyal alanda mini turnuva ve bol rekabet.",
            location = "Sosyal Alan",
            startsAt = Instant.now().plusSeconds(5 * 24 * 60 * 60),
            capacity = 16,
            participantCount = 5,
            isJoined = false,
        ),
        Event(
            id = "seed-run",
            title = "Sabah Koşusu",
            description = "Güne hareketli başlamak isteyen ekip burada.",
            location = "Maslak Parkı",
            startsAt = Instant.now().plusSeconds(7 * 24 * 60 * 60),
            capacity = 10,
            participantCount = 6,
            isJoined = false,
        ),
        Event(
            id = "seed-pizza",
            title = "Pizza Cuması",
            description = "Dolu kapasite senaryosu.",
            location = "Yemekhane",
            startsAt = Instant.now().plusSeconds(3 * 24 * 60 * 60),
            capacity = 10,
            participantCount = 10,
            isJoined = false,
        ),
    )
}
