package com.isacetin.gibinteraktifsosyalapp.feature.events.domain.model

sealed class EventException(message: String, val code: Int) : Exception(message) {
    data object CapacityFull : EventException("Kapasite dolu", 409)
    data object AlreadyJoined : EventException("Bu etkinliğe zaten katıldın", 409)
    data class Validation(override val message: String) : EventException(message, 400)
}
