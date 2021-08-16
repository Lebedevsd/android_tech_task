package com.nordpass.task.ui.list

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.nordpass.tt.usecase.Todo

class TodoListAdapter(
    private val listener: (Todo) -> Unit
) : ListAdapter<Todo, TodoViewHolder>(TodoDifferCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }
}