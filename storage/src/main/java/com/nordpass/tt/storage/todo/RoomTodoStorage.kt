package com.nordpass.tt.storage.todo

import com.nordpass.tt.usecase.Todo
import com.nordpass.tt.usecase.common.Io
import com.nordpass.tt.usecase.todolist.TodoStorage
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

internal class RoomTodoStorage @Inject constructor(
    private val dao: TodoDao,
    private val mapper: TodoMapper,
    @Io private val scheduler: Scheduler
) : TodoStorage {

    override fun save(todoList: List<Todo>): Completable {
        return dao.updateOrCreate(todoList.map(mapper::map))
            .subscribeOn(scheduler)
    }

    override fun getAll(): Flowable<List<Todo>> {
        return dao.getAll()
            .map { list -> list.map(mapper::map) }
            .subscribeOn(scheduler)
    }

    override fun getById(id: Int): Flowable<Todo> {
        return dao.getById(id)
            .map(mapper::map)
            .subscribeOn(scheduler)
    }

    override fun removeById(ids: List<Int>): Completable {
        return dao.remove(ids)
            .subscribeOn(scheduler)
    }
}
