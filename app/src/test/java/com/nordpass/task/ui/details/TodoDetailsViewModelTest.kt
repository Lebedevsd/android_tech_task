package com.nordpass.task.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nordpass.task.ui.TodoFixtures
import com.nordpass.task.ui.base.ErrorMessage
import com.nordpass.tt.usecase.Todo
import com.nordpass.tt.usecase.todolist.ChangeCompletedUseCase
import com.nordpass.tt.usecase.todolist.GetTodoItemUseCase
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.CompletableSubject
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class TodoDetailsViewModelTest {
    @get:Rule var rule: TestRule = InstantTaskExecutorRule()

    private val completableEmitter = CompletableSubject.create()
    private val todoEmitter = BehaviorSubject.create<Todo>()
    private val changeCompletedUseCase: ChangeCompletedUseCase = mock {
        on { updateCompletion(any()) } doReturn completableEmitter
    }
    private val getTodoItemUseCase: GetTodoItemUseCase = mock {
        on { get(any()) } doReturn todoEmitter.toFlowable(BackpressureStrategy.BUFFER)
    }

    private lateinit var viewModel: TodoDetailsViewModel

    private val item = mutableListOf<Todo>()
    private val editItem = mutableListOf<Todo>()
    private val err = mutableListOf<ErrorMessage>()

    @Before
    fun setup() {
        viewModel = TodoDetailsViewModel(changeCompletedUseCase, getTodoItemUseCase)
        viewModel.item.observeForever { item.add(it) }
        viewModel.editItem.observeForever { editItem.add(it) }
        viewModel.error.observeForever { err.add(it) }
    }

    @Test
    fun whenCreated_loadsTodoItem() {
        val todo = TodoFixtures.makeTodo()
        todoEmitter.onNext(todo)
        viewModel.init(todo)

        assert(item[0] == todo)
        assert(err.isEmpty())
        assert(editItem.isEmpty())
    }

    @Test
    fun whenEditClicked_ThisItemEmitted() {
        val todo = TodoFixtures.makeTodo()
        todoEmitter.onNext(todo)
        viewModel.init(todo)

        viewModel.onEditClicked()

        assert(err.isEmpty())
        assert(editItem[0] == todo)
    }

    @Test
    fun whenFinishedClicked_itemSaved() {
        val todo = TodoFixtures.makeTodo()
        todoEmitter.onNext(todo)
        viewModel.init(todo)

        completableEmitter.onComplete()
        viewModel.onFinishedClicked()

        assert(err.isEmpty())
        verify(changeCompletedUseCase).updateCompletion(todo)
    }

    @Test
    fun whenTodoClicked_itemSaved() {
        val todo = TodoFixtures.makeTodo()
        todoEmitter.onNext(todo)
        viewModel.init(todo)

        completableEmitter.onComplete()
        viewModel.onTodoClicked()

        assert(err.isEmpty())
        verify(changeCompletedUseCase).updateCompletion(todo)
    }
}

