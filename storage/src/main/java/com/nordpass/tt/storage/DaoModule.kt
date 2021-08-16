package com.nordpass.tt.storage

import com.nordpass.tt.storage.todo.TodoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DaoModule {
    @Provides
    internal fun provideTodoDao(db: TodoDatabase): TodoDao = db.todoDao()
}