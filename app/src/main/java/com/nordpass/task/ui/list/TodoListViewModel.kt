package com.nordpass.task.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nordpass.task.ui.base.BaseViewModel
import com.nordpass.task.ui.base.SingleLiveEvent
import com.nordpass.tt.usecase.Todo
import com.nordpass.tt.usecase.todolist.GetTodoListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel
@Inject constructor(
    getTodoListUseCase: GetTodoListUseCase
) : BaseViewModel() {
    private val _items = MutableLiveData<List<Todo>>()
    private val _showItem = SingleLiveEvent<Todo>()

    val items: LiveData<List<Todo>> = _items
    val showItem: LiveData<Todo> = _showItem

    init {
        getTodoListUseCase.get()
            .subscribeBy(onNext = _items::postValue, onError = ::handleError)
            .attach()
    }

    fun onItemClicked(todo: Todo) {
        _showItem.postValue(todo)
    }
}
