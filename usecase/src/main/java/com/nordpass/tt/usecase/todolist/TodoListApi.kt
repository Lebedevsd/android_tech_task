package com.nordpass.tt.usecase.todolist

import com.nordpass.tt.usecase.Todo
import io.reactivex.rxjava3.core.Single

interface TodoListApi {
    fun get(): Single<List<Todo>>
}
