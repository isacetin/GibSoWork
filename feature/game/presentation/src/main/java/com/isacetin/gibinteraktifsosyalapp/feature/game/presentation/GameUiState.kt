package com.isacetin.gibinteraktifsosyalapp.feature.game.presentation

import com.isacetin.gibinteraktifsosyalapp.feature.game.domain.model.LeaderboardEntry

sealed interface GameUiState {
    data object Loading : GameUiState

    data class Content(
        val mode: GameMode = GameMode.Landing,
        val bestScore: Int = 0,
        val balance: Int = 0,
        val score: Int = 0,
        val timeLeftSeconds: Int = 30,
        val combo: Int = 1,
        val earnedPoints: Int = 0,
        val bannerMessage: String? = null,
        val coins: List<FallingCoin> = emptyList(),
        val basketX: Float = 0.5f,
        val leaderboard: List<LeaderboardEntry> = emptyList(),
    ) : GameUiState

    data class Error(val message: String) : GameUiState
}

enum class GameMode {
    Landing,
    Playing,
    Result,
    Leaderboard,
}

data class FallingCoin(
    val id: Int,
    val x: Float,
    val y: Float,
    val value: Int,
    val speed: Float,
)
