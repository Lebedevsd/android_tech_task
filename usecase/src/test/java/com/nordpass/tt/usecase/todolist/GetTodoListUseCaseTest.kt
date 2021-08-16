package com.nordpass.tt.usecase.todolist

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nordpass.tt.usecase.utils.TodoFixtures.Companion.makeTodo
import io.reactivex.rxjava3.core.Flowable
import org.junit.Before
import org.junit.Test
import org.threeten.bp.OffsetDateTime

class GetTodoListUseCaseTest {
    private lateinit var useCase: GetTodoListUseCase
    private val storage: TodoStorage = mock()

    @Before
    fun setup() {
        useCase = GetTodoListUseCase(storage)
    }

    @Test
    fun getTodoList_returnsLatestLists() {
        val items = listOf(makeTodo())
        given(storage.getAll()).willReturn(Flowable.just(items))

        useCase.get()
            .test()
            .assertValue { it == items }
            .assertNoErrors()

        verify(storage).getAll()
    }

    @Test
    fun getTodoList_reordersNotCompletedFirst() {
        val completed = makeTodo(isCompleted = true)
        val notComleted = makeTodo(isCompleted = false)
        val items = listOf(completed, notComleted)
        given(storage.getAll()).willReturn(Flowable.just(items))

        useCase.get()
            .test()
            .assertValue { it == listOf(notComleted, completed) }
            .assertNoErrors()

        verify(storage).getAll()
    }

    @Test
    fun getTodoList_reordersByUpdatedAt() {
        val yesterday = makeTodo(updatedAt = OffsetDateTime.now().minusDays(1).toString())
        val today = makeTodo(updatedAt = OffsetDateTime.now().toString())
        val items = listOf(yesterday, today)
        given(storage.getAll()).willReturn(Flowable.just(items))

        useCase.get()
            .test()
            .assertValue { it == listOf(today, yesterday) }
            .assertNoErrors()

        verify(storage).getAll()
    }

    @Test
    fun getTodoList_reordersByUpdatedAtAndIsCompleted() {
        val yesterdayNotCompleted = makeTodo(updatedAt = OffsetDateTime.now().minusDays(1).toString(), isCompleted = false)
        val todayNotCompleted = makeTodo(updatedAt = OffsetDateTime.now().toString(), isCompleted = false)

        val yesterdayCompleted = makeTodo(updatedAt = OffsetDateTime.now().minusDays(1).toString(), isCompleted = true)
        val todayCompleted = makeTodo(updatedAt = OffsetDateTime.now().toString(), isCompleted = true)
        val items = listOf(yesterdayCompleted, todayCompleted, yesterdayNotCompleted, todayNotCompleted)
        given(storage.getAll()).willReturn(Flowable.just(items))

        useCase.get()
            .test()
            .assertValue { it == listOf(todayNotCompleted, yesterdayNotCompleted, todayCompleted, yesterdayCompleted) }
            .assertNoErrors()

        verify(storage).getAll()
    }
}
