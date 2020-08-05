package com.example.mvvmattempt.data.source.repository

import android.annotation.SuppressLint
import com.example.mvvmattempt.data.Comment
import com.example.mvvmattempt.data.Post
import com.example.mvvmattempt.data.source.api.PlaceholderApi
import com.example.mvvmattempt.data.source.db.PostDao
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
@SuppressLint("CheckResult")  // subscriptions without reference get GC'ed after completion, no need to unsubscribe
class PostRepository @Inject constructor(
    private val dao: PostDao,
    private val api: PlaceholderApi
) {

    init {
        Timber.d("PostRepo created")
    }


    /**
     * Try to download from Internet, if there's problem -> get data from DB
     */
    fun getPostsByUserId(userId: Long): Single<List<Post>> =
        api.getPostsByUserId(userId)
            .doOnSuccess { insertPostsInDb(it) }
            .onErrorResumeNext(dao.getPostsByUserId(userId))

    private fun insertPostsInDb(posts: List<Post>) {
        dao.insertAll(posts)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(
                { Timber.d("Inserted ${posts.size} items in db") },
                { Timber.d("Error inserting items in db") })
    }

    fun getCommentsByPostId(postId: Long): Single<List<Comment>> =
        api.getCommentsByPostId(postId)

    fun deleteAll() {
        dao.deleteAll()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(
                { Timber.d("Deleted posts_table") },
                { Timber.d("Error deleting table") }
            )
    }
}