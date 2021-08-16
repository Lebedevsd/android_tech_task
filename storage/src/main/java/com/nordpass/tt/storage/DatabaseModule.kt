package com.nordpass.tt.storage

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Singleton @Provides
    internal fun provideDatabase(context: Context): TodoDatabase {
        return Room.databaseBuilder(context, TodoDatabase::class.java, "db")
            .fallbackToDestructiveMigration()
            .addMigrations(Migrations.migration_1_2, Migrations.migration_2_3)
            .build()
    }
}
