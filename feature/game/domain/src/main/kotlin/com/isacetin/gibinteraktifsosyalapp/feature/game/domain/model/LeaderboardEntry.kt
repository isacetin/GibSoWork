package com.isacetin.gibinteraktifsosyalapp.feature.game.domain.model

data class LeaderboardEntry(
    val rank: Int,
    val displayName: String,
    val score: Int,
    val isCurrentUser: Boolean = false,
)
