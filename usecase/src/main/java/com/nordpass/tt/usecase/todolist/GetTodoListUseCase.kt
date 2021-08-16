package com.nordpass.tt.usecase.todolist

import com.nordpass.tt.usecase.Todo
import com.nordpass.tt.usecase.utils.toDate
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class GetTodoListUseCase @Inject constructor(
    private val storage: TodoStorage
) {
    fun get(): Flowable<List<Todo>> {
        return storage.getAll().map { todos ->
            todos.sortedWith(compareBy({ !it.isCompleted }, { it.updatedAt.toDate() })).reversed()
        }
    }
}


