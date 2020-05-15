package com.example.mvvmattempt.network

import com.example.mvvmattempt.data.Comment
import com.example.mvvmattempt.data.Post
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path


interface PlaceholderApi {
    @GET("posts/{id}/comments")
    fun getComments(@Path("id") id: Long): Call<List<Comment>>

    @GET("posts")
    fun getPosts(@Query("userId") userId: Long): Call<List<Post>>
}