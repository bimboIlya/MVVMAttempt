package com.example.mvvmattempt.ui.post

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmattempt.R
import com.example.mvvmattempt.data.Comment
import com.example.mvvmattempt.network.NetworkService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostViewModel : ViewModel() {
    private val placeholderApi by lazy { NetworkService.getService() }
    private val _commentList = MutableLiveData<List<Comment>>()
    private val _isLoading = MutableLiveData<Boolean>()
    private val _errorMessageId = MutableLiveData<Int>()

    val postsList: LiveData<List<Comment>> = _commentList
    val isLoading: LiveData<Boolean> = _isLoading
    val errorMessageId: LiveData<Int> = _errorMessageId

//    init { loadPosts() }

    // id нужно инжектить в конструктор, но в этой версии я этого делать не буду
    // обе ViewModel почти идентичны, нужно наследоваться от базовой версии
    fun loadComments(postId: Long) {
        loadingStarted()

        placeholderApi.getComments(postId).enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                Log.d(TAG, "Success network call")
                _commentList.value = response.body()
                loadingStopped()
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
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


    companion object {
        const val TAG = "PostViewModel"
    }

}