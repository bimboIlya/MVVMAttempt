package com.example.mvvmattempt.ui.posts

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmattempt.R
import com.example.mvvmattempt.data.Post
import com.example.mvvmattempt.data.source.repository.PostRepository
import com.example.mvvmattempt.ui.common.LoaderViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class PostsListViewModel @ViewModelInject constructor(
    private val repo: PostRepository
) : LoaderViewModel() {


    private val _postsList = MutableLiveData<List<Post>>()
    val postsList: LiveData<List<Post>> = _postsList

    init {
        load(USER_ID)
    }

    override fun load(id: Long) {
        disposables.add(
            repo.getPostsByUserId(USER_ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loadingStarted() }
                .subscribe(
                    { posts ->
                        Timber.d("Success getting ${posts.size} posts")
                        if (posts.isNotEmpty()) {     // Room returns empty list if
                            loadingStopped()          // there are no entries
                            _postsList.value = posts
                        } else {
                            loadingFailed()
                        }
                    },
                    {
                        Timber.d("Failed getting posts")
                        loadingFailed()
                    }
                )
        )
    }

    override fun loadingFailed() {
        loadingStopped()
        setMessageId(R.string.loading_error)
    }

    override fun retry() {
        load(USER_ID)
    }


    companion object {
        const val USER_ID = 1L  // constant id for simplicity
    }
}