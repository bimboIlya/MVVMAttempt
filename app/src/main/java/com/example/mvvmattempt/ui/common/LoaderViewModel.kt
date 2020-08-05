package com.example.mvvmattempt.ui.common

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class LoaderViewModel : ViewModel() {

    /**
     * Indicates if data still being loaded
     */
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    /**
     * Emits [Event] of an App Resource Id
     * For example, can be used to display Toast or Snackbar in UI
     */
    private val _message = MutableLiveData<Event<Int>>()
    val message: LiveData<Event<Int>> = _message

    protected val disposables = CompositeDisposable()


    protected fun setMessageId(@StringRes messageId: Int) {
        _message.value = Event(messageId)
    }

    protected fun loadingStarted() {
        _isLoading.value = true
    }

    protected fun loadingStopped() {
        _isLoading.value = false
    }

    protected abstract fun loadingFailed()

    protected abstract fun load(id: Long)

    abstract fun retry()

    override fun onCleared() {
        disposables.clear()
    }
}