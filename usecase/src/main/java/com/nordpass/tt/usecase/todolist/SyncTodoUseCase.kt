package com.nordpass.tt.usecase.todolist

import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class SyncTodoUseCase @Inject constructor(
    private val api: TodoListApi,
    private val storage: TodoStorage
) {
    fun sync(): Completable {
        return api.get().flatMapCompletable(storage::save)
    }
}
