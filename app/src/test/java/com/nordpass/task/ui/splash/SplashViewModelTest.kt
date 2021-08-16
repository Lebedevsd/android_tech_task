package com.nordpass.task.ui.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nordpass.task.R
import com.nordpass.task.ui.base.ErrorMessage
import com.nordpass.tt.usecase.todolist.SyncTodoUseCase
import io.reactivex.rxjava3.subjects.CompletableSubject
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class SplashViewModelTest {
    @get:Rule var rule: TestRule = InstantTaskExecutorRule()

    private val completableEmitter = CompletableSubject.create()
    private val syncTodoUseCase: SyncTodoUseCase = mock {
        on { sync() } doReturn completableEmitter
    }
    private lateinit var viewModel: SplashViewModel


    private val completed = mutableListOf<Unit>()
    private val isLoading = mutableListOf<Boolean>()
    private val err = mutableListOf<ErrorMessage>()

    @Before
    fun setup() {
        viewModel = SplashViewModel(syncTodoUseCase)
        viewModel.completed.observeForever { completed.add(it) }
        viewModel.isLoading.observeForever { isLoading.add(it) }
        viewModel.error.observeForever{ err.add(it)}
    }

    @Test
    fun whenCreated_startsToFetchData() {
        completableEmitter.onComplete()

        assert(isLoading == listOf(true, false))
        assert(completed == listOf(Unit))
    }

    @Test
    fun whenFails_showError() {
        val error = IllegalArgumentException("test")

        completableEmitter.onError(error)

        assert(isLoading == listOf(true, false))
        assert(completed.isEmpty())
        assert(err == listOf(ErrorMessage(R.string.error_generic)))
    }
}
