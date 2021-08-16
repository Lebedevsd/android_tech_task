package com.nordpass.tt.usecase.todolist

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argThat
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nordpass.tt.usecase.utils.TodoFixtures
import io.reactivex.rxjava3.core.Completable
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.threeten.bp.OffsetDateTime

class ChangeTitleUseCaseTest {
    private lateinit var useCase: ChangeTitleUseCase
    private val storage: TodoStorage = mock()

    @Before
    fun setup() {
        useCase = ChangeTitleUseCase(storage)
    }

    @Test
    fun updateCompletion_ChangesIsCompletedAndUpdatedAt() {
        val yesterday = OffsetDateTime.now().minusDays(1)
        val item = TodoFixtures.makeTodo(title = "test", updatedAt = yesterday.toString())
        val newTitle = "new Titile"
        given(storage.save(any())).willReturn(Completable.complete())

        useCase.updateTitle(item, newTitle)
            .test()
            .assertComplete()
            .assertNoErrors()

        verify(storage).save(argThat {
            this.size == 1 &&
                    this[0].title != item.title &&
                    this[0].title == newTitle &&
                    OffsetDateTime.parse(this[0].updatedAt).isAfter(yesterday)
        })
    }
}

