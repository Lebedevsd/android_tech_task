package com.nordpass.tt.storage

import com.nordpass.tt.storage.todo.RoomTodoStorage
import com.nordpass.tt.usecase.todolist.TodoStorage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal interface StorageModule {

    @Binds
    fun bindTodoStorage(storage: RoomTodoStorage): TodoStorage
}
