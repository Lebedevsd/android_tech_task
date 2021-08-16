package com.nordpass.task.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nordpass.task.ui.base.BaseViewModel
import com.nordpass.task.ui.base.SingleLiveEvent
import com.nordpass.tt.usecase.Todo
import com.nordpass.tt.usecase.todolist.GetTodoItemUseCase
import com.nordpass.tt.usecase.todolist.ChangeCompletedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class TodoDetailsViewModel
@Inject constructor(
    private val changeCompletedUseCase: ChangeCompletedUseCase,
    private val getTodoItemUseCase: GetTodoItemUseCase
) : BaseViewModel() {

    private val _showItem = SingleLiveEvent<Todo>()

    val item = MutableLiveData<Todo>()
    val editItem: LiveData<Todo> = _showItem

    fun init(item: Todo) {
        getTodoItemUseCase.get(item.id)
            .subscribeBy(onNext = {
                this.item.postValue(it)
            }, onError = ::handleError)
            .attach()
    }

    fun onFinishedClicked() {
        changeFinishedStatus()
    }

    fun onTodoClicked() {
        changeFinishedStatus()
    }

    fun onEditClicked() {
        item.value?.let {
            _showItem.postValue(it)
        }
    }


    private fun changeFinishedStatus() {
        item.value?.let {
            changeCompletedUseCase.updateCompletion(it)
                .subscribeBy(onComplete = {}, onError = ::handleError)
                .attach()
        }
    }

}
