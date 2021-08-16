package com.nordpass.task.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nordpass.task.ui.TodoFixtures
import com.nordpass.task.ui.TodoFixtures.Companion.makeTodo
import com.nordpass.task.ui.base.ErrorMessage
import com.nordpass.tt.usecase.Todo
import com.nordpass.tt.usecase.todolist.GetTodoListUseCase
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.subjects.BehaviorSubject
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class TodoListViewModelTest {
    @get:Rule var rule: TestRule = InstantTaskExecutorRule()

    private val listEmitter = BehaviorSubject.create<List<Todo>>()
    private val getTodoListUseCase: GetTodoListUseCase = mock {
        on { get() } doReturn listEmitter.toFlowable(BackpressureStrategy.BUFFER)
    }
    private lateinit var viewModel: TodoListViewModel

    private val items = mutableListOf<List<Todo>>()
    private val showItem = mutableListOf<Todo>()
    private val err = mutableListOf<ErrorMessage>()

    @Before
    fun setup() {
        viewModel = TodoListViewModel(getTodoListUseCase)
        viewModel.items.observeForever { items.add(it) }
        viewModel.showItem.observeForever { showItem.add(it) }
        viewModel.error.observeForever { err.add(it) }
    }

    @Test
    fun whenCreated_loadsListOfToDos() {
        val list = listOf(makeTodo())
        listEmitter.onNext(list)

        assert(items[0] == list)
        assert(err.isEmpty())
        assert(showItem.isEmpty())
    }

    @Test
    fun whenItemClicked_ThisItemEmitted() {
        val todo = makeTodo()

        viewModel.onItemClicked(todo)

        assert(err.isEmpty())
        assert(showItem[0] == todo)
    }
}
