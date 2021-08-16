package com.nordpass.tt.api

import com.nordpass.tt.api.backend.todo.TodoApiService
import com.nordpass.tt.usecase.todolist.TodoListApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal interface ApiModule {
    @Binds
    fun bindTodoApi(service: TodoApiService): TodoListApi
}