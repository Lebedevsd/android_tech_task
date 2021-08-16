package com.nordpass.task.ui.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nordpass.task.ui.base.BaseViewModel
import com.nordpass.task.ui.base.SingleLiveEvent
import com.nordpass.tt.usecase.Todo
import com.nordpass.tt.usecase.todolist.ChangeCompletedUseCase
import com.nordpass.tt.usecase.todolist.ChangeTitleUseCase
import com.nordpass.tt.usecase.todolist.GetTodoItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class TodoEditViewModel
@Inject constructor(
    private val getTodoItemUseCase: GetTodoItemUseCase,
    private val changeTitleUseCase: ChangeTitleUseCase
) : BaseViewModel() {

    val item = MutableLiveData<Todo>()
    val saveFinished = SingleLiveEvent<Unit>()

    fun init(item: Todo) {
        getTodoItemUseCase.get(item.id)
            .subscribeBy(onNext = {
                this.item.postValue(it)
            }, onError = ::handleError)
            .attach()
    }

    fun onSaveClicked(title: String) {
        item.value?.let {
            changeTitleUseCase.updateTitle(it, title)
                .subscribeBy(onComplete = { saveFinished.postValue(Unit) }, onError = ::handleError)
                .attach()
        }
    }

}
