package com.nordpass.tt.usecase.todolist

import com.nordpass.tt.usecase.Todo
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetTodoItemUseCase @Inject constructor(
    private val storage: TodoStorage
) {
    fun get(id: Int): Flowable<Todo> {
        return storage.getById(id)
    }
}
