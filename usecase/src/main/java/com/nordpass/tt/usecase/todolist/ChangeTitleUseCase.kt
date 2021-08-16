package com.nordpass.tt.usecase.todolist

import com.nordpass.tt.usecase.Todo
import io.reactivex.rxjava3.core.Completable
import org.threeten.bp.OffsetDateTime
import javax.inject.Inject

class ChangeTitleUseCase @Inject constructor(
    private val storage: TodoStorage
) {
    fun updateTitle(todo: Todo, title: String): Completable {
        return storage.save(listOf(todo.copy(title = title, updatedAt = OffsetDateTime.now()
            .toString())))
    }
}
