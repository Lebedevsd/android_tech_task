package com.nordpass.task.ui.edit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nordpass.task.ui.TodoFixtures
import com.nordpass.task.ui.base.ErrorMessage
import com.nordpass.task.ui.details.TodoDetailsViewModel
import com.nordpass.tt.usecase.Todo
import com.nordpass.tt.usecase.todolist.ChangeCompletedUseCase
import com.nordpass.tt.usecase.todolist.ChangeTitleUseCase
import com.nordpass.tt.usecase.todolist.GetTodoItemUseCase
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.CompletableSubject
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class TodoEditViewModelTest {
    @get:Rule var rule: TestRule = InstantTaskExecutorRule()

    private val completableEmitter = CompletableSubject.create()
    private val todoEmitter = BehaviorSubject.create<Todo>()
    private val changeTitleUseCase: ChangeTitleUseCase = mock {
        on { updateTitle(any(), any()) } doReturn completableEmitter
    }
    private val getTodoItemUseCase: GetTodoItemUseCase = mock {
        on { get(any()) } doReturn todoEmitter.toFlowable(BackpressureStrategy.BUFFER)
    }

    private lateinit var viewModel: TodoEditViewModel

    private val item = mutableListOf<Todo>()
    private val saveFinished = mutableListOf<Unit>()
    private val err = mutableListOf<ErrorMessage>()

    @Before
    fun setup() {
        viewModel = TodoEditViewModel(getTodoItemUseCase, changeTitleUseCase)
        viewModel.item.observeForever { item.add(it) }
        viewModel.saveFinished.observeForever { saveFinished.add(it) }
        viewModel.error.observeForever { err.add(it) }
    }

    @Test
    fun whenCreated_loadsTodoItem() {
        val todo = TodoFixtures.makeTodo()
        todoEmitter.onNext(todo)
        viewModel.init(todo)

        assert(item[0] == todo)
        assert(err.isEmpty())
        assert(saveFinished.isEmpty())
    }

    @Test
    fun whenSaveClicked_saveFinishedIsEmitted() {
        val todo = TodoFixtures.makeTodo()
        todoEmitter.onNext(todo)
        viewModel.init(todo)

        completableEmitter.onComplete()
        viewModel.onSaveClicked("test")

        assert(err.isEmpty())
        assert(saveFinished[0] == Unit)
    }

}
