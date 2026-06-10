package com.isacetin.gibinteraktifsosyalapp.feature.events.domain.model

import java.time.Instant

data class Event(
    val id: String,
    val title: String,
    val description: String,
    val location: String,
    val startsAt: Instant,
    val capacity: Int,
    val participantCount: Int,
    val isJoined: Boolean,
    val rewardPoints: Int = EVENT_REWARD_POINTS,
) {
    val isFull: Boolean
        get() = participantCount >= capacity
}

const val EVENT_REWARD_POINTS = 15
