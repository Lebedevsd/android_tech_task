package com.nordpass.tt.api.backend.todo

import com.nordpass.tt.usecase.Todo
import org.threeten.bp.OffsetDateTime
import javax.inject.Inject

internal class TodoResponseMapper @Inject constructor() {

    fun map(response: TodoResponse): Todo? {
        val id = response.id ?: return null
        val userId = response.userId ?: return null
        val title = response.title ?: return null
        val completed = response.completed ?: false
        return Todo(
            id = id,
            title = title,
            isCompleted = completed,
            createdAt = response.createdAt ?: OffsetDateTime.now().toString(),
            userId =  userId,
            updatedAt = response.updatedAt ?: OffsetDateTime.now().toString(),
        )
    }
}
