package com.example.mvvmattempt.ui.post

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmattempt.R
import com.example.mvvmattempt.data.Comment
import com.example.mvvmattempt.data.source.repository.PostRepository
import com.example.mvvmattempt.ui.common.LoaderViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class PostViewModel @ViewModelInject constructor(
    private val repo: PostRepository
) : LoaderViewModel() {


    private val _commentList = MutableLiveData<List<Comment>>()
    val commentList: LiveData<List<Comment>> = _commentList

    private val _postId = MutableLiveData<Long>()
    val postId: LiveData<Long> = _postId


    fun setPostIdAndGetComments(id: Long) {
        _postId.value = id
        load(id)
    }

    override fun load(id: Long) {
        disposables.add(
            repo.getCommentsByPostId(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loadingStarted() }
                .subscribe(
                    {
                        Timber.d("onSuccess()")
                        _commentList.value = it
                        loadingStopped()
                    },
                    {
                        Timber.d("onError()")
                        loadingFailed()
                    }
                )
        )
    }

    override fun retry() {
        postId.value?.let { load(it) }
    }

    override fun loadingFailed() {
        loadingStopped()
        setMessageId(R.string.loading_error)
    }
}