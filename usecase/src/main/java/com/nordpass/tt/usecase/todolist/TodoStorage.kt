package com.nordpass.tt.usecase.todolist

import com.nordpass.tt.usecase.Todo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

interface TodoStorage {
    fun save(todoList: List<Todo>): Completable

    fun getAll(): Flowable<List<Todo>>

    fun getById(id: Int): Flowable<Todo>

    fun removeById(ids: List<Int>): Completable
}
