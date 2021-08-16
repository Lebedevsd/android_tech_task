package com.nordpass.tt.usecase

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Todo(
    val id: Int,
    val title: String,
    val isCompleted: Boolean,
    val createdAt: String,
    val userId: Int,
    val updatedAt: String
) : Parcelable
