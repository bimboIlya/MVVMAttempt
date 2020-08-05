package com.example.mvvmattempt.data.source.api

import com.example.mvvmattempt.data.Comment
import com.example.mvvmattempt.data.Picture
import com.example.mvvmattempt.data.Post
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL = "https://jsonplaceholder.typicode.com/"

interface PlaceholderApi {

    @GET("posts")
    fun getPostsByUserId(@Query("userId") userId: Long): Single<List<Post>>

    @GET("posts/{id}/comments")
    fun getCommentsByPostId(@Path("id") postId: Long): Single<List<Comment>>

    @GET("photos")
    fun getPicturesByAlbumId(@Query("albumId") albumId: Long): Single<List<Picture>>
}