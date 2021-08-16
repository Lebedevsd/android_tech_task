package com.nordpass.task.ui.list

import androidx.recyclerview.widget.DiffUtil
import com.nordpass.tt.usecase.Todo

class TodoDifferCallback : DiffUtil.ItemCallback<Todo>() {
    override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem == newItem
    }
}