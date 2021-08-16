package com.nordpass.tt.usecase.todolist

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nordpass.tt.usecase.Todo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test

class SyncTodoUseCaseTest {
    private lateinit var useCase: SyncTodoUseCase
    private val api: TodoListApi = mock()
    private val storage: TodoStorage = mock()

    @Before
    fun setup() {
        useCase = SyncTodoUseCase(api, storage)
    }

    @Test
    fun sync_fetchedItemsAreStored() {
        val items = listOf<Todo>(mock(), mock(), mock())
        given(api.get()).willReturn(Single.just(items))
        given(storage.save(any())).willReturn(Completable.complete())

        useCase.sync()
            .test()
            .assertComplete()
            .assertNoErrors()

        verify(storage).save(items)
    }
}
