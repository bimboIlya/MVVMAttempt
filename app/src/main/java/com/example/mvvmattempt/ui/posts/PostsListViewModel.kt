package com.example.mvvmattempt.ui.posts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmattempt.R
import com.example.mvvmattempt.data.Post
import com.example.mvvmattempt.network.NetworkService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostsListViewModel : ViewModel() {
    private val placeholderApi by lazy { NetworkService.getService() }

    private val _postsList =  MutableLiveData<List<Post>>()
    private val _isLoading =  MutableLiveData<Boolean>()
    private val _errorMessageId = MutableLiveData<Int>()

    val postsList: LiveData<List<Post>> = _postsList
    val isLoading: LiveData<Boolean> = _isLoading
    val errorMessageId: LiveData<Int> = _errorMessageId

    init {
        loadPosts()
    }

    fun loadPosts() {
        loadingStarted()

        placeholderApi.getPosts(USER_ID).enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                Log.d(TAG, "Success network call")
                _postsList.value = response.body()
                loadingStopped()
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.d(TAG, "Failed network call")
                loadingStopped()
                loadingFailed()
            }
        })
    }

    private fun loadingStarted() {
        _isLoading.value = true
    }

    private fun loadingStopped() {
        _isLoading.value = false
    }

    private fun loadingFailed() {
        _errorMessageId.value = R.string.loading_error
    }


    companion object{
        const val USER_ID = 1L
        const val TAG = "PostsListViewModel"
    }
}