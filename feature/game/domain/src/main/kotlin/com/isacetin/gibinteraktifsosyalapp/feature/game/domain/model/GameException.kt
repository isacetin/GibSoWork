package com.isacetin.gibinteraktifsosyalapp.feature.game.domain.model

sealed class GameException(
    message: String,
    val code: Int,
) : Exception(message) {
    data class Validation(override val message: String = "Geçersiz oyun skoru") : GameException(message, 400)
}
