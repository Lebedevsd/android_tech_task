package com.nordpass.tt.usecase.todolist

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nordpass.tt.usecase.utils.TodoFixtures
import io.reactivex.rxjava3.core.Flowable
import org.junit.Before
import org.junit.Test

class GetTodoItemUseCaseTest {
    private lateinit var useCase: GetTodoItemUseCase
    private val storage: TodoStorage = mock()

    @Before
    fun setup() {
        useCase = GetTodoItemUseCase(storage)
    }

    @Test
    fun getTodo_returnsLatestItemWithSameId() {
        val item = TodoFixtures.makeTodo(id = 1)
        given(storage.getById(1)).willReturn(Flowable.just(item))

        useCase.get(1)
            .test()
            .assertValue { it == item }
            .assertNoErrors()

        verify(storage).getById(1)
    }
}
