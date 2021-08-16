package com.nordpass.tt.usecase.utils

import com.nordpass.tt.usecase.Todo
import org.threeten.bp.OffsetDateTime

class TodoFixtures {

    companion object {
        fun makeTodo(
            id: Int = 0,
            title: String = "",
            isCompleted: Boolean = false,
            createdAt: String = OffsetDateTime.now().toString(),
            userId: Int = 0,
            updatedAt: String = OffsetDateTime.now().toString()
        ): Todo {
            return Todo(id, title, isCompleted, createdAt, userId, updatedAt)
        }

    }
}
