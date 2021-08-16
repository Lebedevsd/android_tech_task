package com.nordpass.tt.usecase.todolist

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argThat
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nordpass.tt.usecase.utils.TodoFixtures.Companion.makeTodo
import io.reactivex.rxjava3.core.Completable
import org.junit.Before
import org.junit.Test
import org.threeten.bp.OffsetDateTime

class ChangeCompletedUseCaseTest {
    private lateinit var useCase: ChangeCompletedUseCase
    private val storage: TodoStorage = mock()

    @Before
    fun setup() {
        useCase = ChangeCompletedUseCase(storage)
    }

    @Test
    fun updateCompletion_ChangesIsCompletedAndUpdatedAt() {
        val yesterday = OffsetDateTime.now().minusDays(1)
        val item = makeTodo(isCompleted = true, updatedAt = yesterday.toString())
        given(storage.save(any())).willReturn(Completable.complete())

        useCase.updateCompletion(item)
            .test()
            .assertComplete()
            .assertNoErrors()

        verify(storage).save(argThat {
            this.size == 1 &&
                    this[0].isCompleted == !item.isCompleted &&
                    OffsetDateTime.parse(this[0].updatedAt).isAfter(yesterday)
        })
    }
}
