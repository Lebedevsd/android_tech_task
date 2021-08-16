package com.nordpass.task.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseViewModel : ViewModel() {
    private val disposables = CompositeDisposable()
    val error = SingleLiveEvent<ErrorMessage>()

    protected fun Disposable.attach() {
        disposables.add(this)
    }

    protected open fun handleError(error: Throwable) {
        this.error.postValue(ErrorMessageMapper.map(error))
    }

    override fun onCleared() {
        disposables.clear()
    }
}
