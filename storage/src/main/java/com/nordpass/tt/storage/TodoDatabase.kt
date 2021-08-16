package com.nordpass.tt.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nordpass.tt.storage.todo.TodoDao
import com.nordpass.tt.storage.todo.TodoEntity

@Database(
    version = 3,
    entities = [TodoEntity::class]
)
internal abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}
