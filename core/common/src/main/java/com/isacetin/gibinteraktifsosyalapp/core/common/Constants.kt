package com.isacetin.gibinteraktifsosyalapp.core.common

/**
 * Boundary values shared between production code and tests so both rely on the
 * same source of truth (see docs/02_API_TEST.md §4).
 */
object Constants {
    /** Points awarded per Jira story point when a task is marked "done". */
    const val POINT_PER_SP = 10

    const val STORY_POINTS_MIN = 0
    const val STORY_POINTS_MAX = 21

    const val EVENT_TITLE_MIN_LENGTH = 1
    const val EVENT_TITLE_MAX_LENGTH = 80

    const val EVENT_CAPACITY_MIN = 1
    const val EVENT_CAPACITY_MAX = 500

    /** Demo user id used until :feature:auth provides a real session (Faz 4). */
    const val DEMO_USER_ID = "00000000-0000-0000-0000-000000000001"
}
