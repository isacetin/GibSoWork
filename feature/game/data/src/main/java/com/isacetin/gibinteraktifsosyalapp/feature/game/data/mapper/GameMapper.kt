package com.isacetin.gibinteraktifsosyalapp.feature.game.data.mapper

import com.isacetin.gibinteraktifsosyalapp.core.common.Constants
import com.isacetin.gibinteraktifsosyalapp.feature.game.data.remote.dto.LeaderboardEntryDto
import com.isacetin.gibinteraktifsosyalapp.feature.game.domain.model.LeaderboardEntry

fun LeaderboardEntryDto.toDomain(rank: Int): LeaderboardEntry =
    LeaderboardEntry(
        rank = rank,
        displayName = displayName,
        score = score,
        isCurrentUser = displayName.equals("Ahmet K.", ignoreCase = true) ||
            displayName.equals(Constants.DEMO_USER_ID, ignoreCase = true),
    )
