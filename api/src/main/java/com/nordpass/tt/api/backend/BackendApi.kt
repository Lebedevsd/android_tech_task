package com.nordpass.tt.api.backend

import com.nordpass.tt.api.backend.todo.TodoListResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

internal interface BackendApi {
    @GET("public-api/todos")
    fun getTodoList(@Query("page") page: Int): Single<TodoListResponse>
}
