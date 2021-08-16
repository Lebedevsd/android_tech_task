package com.nordpass.tt.storage

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.nordpass.tt.storage.todo.TodoDao

internal object Migrations {

    val migration_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            val table = TodoDao.TABLE_NAME
            database.execSQL(
                "ALTER TABLE $table ADD COLUMN createdAt TEXT NOT NULL DEFAULT ''"
            )
        }
    }

    val migration_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            val table = TodoDao.TABLE_NAME
            database.execSQL("ALTER TABLE $table ADD COLUMN userId INTEGER NOT NULL DEFAULT 0")
            database.execSQL("ALTER TABLE $table ADD COLUMN updatedAt TEXT NOT NULL DEFAULT ''")
        }
    }
}
