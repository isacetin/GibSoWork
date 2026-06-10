package com.isacetin.gibinteraktifsosyalapp.feature.tasks.domain.model

/** Mirrors the `jira_tasks.status` check constraint (`todo`/`in_progress`/`done`). */
enum class TaskStatus(val apiValue: String) {
    TODO("todo"),
    IN_PROGRESS("in_progress"),
    DONE("done"),
    ;

    companion object {
        fun fromApiValue(value: String): TaskStatus =
            entries.firstOrNull { it.apiValue == value } ?: TODO
    }
}
