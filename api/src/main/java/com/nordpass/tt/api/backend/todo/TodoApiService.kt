package com.nordpass.tt.api.backend.todo

import com.nordpass.tt.api.backend.BackendApi
import com.nordpass.tt.usecase.Todo
import com.nordpass.tt.usecase.todolist.TodoListApi
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

internal class TodoApiService @Inject constructor(
    private val api: BackendApi,
    private val mapper: TodoResponseMapper
) : TodoListApi {
    override fun get(): Single<List<Todo>> {
        return api.getTodoList(1)
            .map { response -> response.data?.mapNotNull(mapper::map) ?: emptyList() }
    }
}
