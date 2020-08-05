package com.example.mvvmattempt.data.source.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mvvmattempt.data.Post
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(posts: List<Post>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(post: Post): Completable

    @Query("SELECT * FROM post_table WHERE id =:postId")
    fun getPostById(postId: Long): Single<Post>

    @Query("SELECT * FROM post_table WHERE id =:postId")
    fun observePostById(postId: Long): Observable<Post>

    @Query("SELECT * FROM post_table WHERE user_id = :userId")
    fun getPostsByUserId(userId: Long): Single<List<Post>>

    @Query("SELECT * FROM post_table WHERE user_id = :userId")
    fun observePostsByUserId(userId: Long): Observable<List<Post>>

    @Query("DELETE FROM post_table")
    fun deleteAll(): Completable

}
