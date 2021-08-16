package com.nordpass.tt.usecase.todolist

import com.nordpass.tt.usecase.Todo
import io.reactivex.rxjava3.core.Completable
import org.threeten.bp.OffsetDateTime
import javax.inject.Inject

class ChangeCompletedUseCase @Inject constructor(
    private val storage: TodoStorage
) {
    fun updateCompletion(todo: Todo): Completable {
        return storage.save(listOf(todo.copy(isCompleted = !todo.isCompleted, updatedAt = OffsetDateTime.now()
            .toString())))
    }
}

