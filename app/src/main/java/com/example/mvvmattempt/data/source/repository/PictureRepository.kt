package com.example.mvvmattempt.data.source.repository

import android.annotation.SuppressLint
import com.example.mvvmattempt.data.Picture
import com.example.mvvmattempt.data.source.api.PlaceholderApi
import com.example.mvvmattempt.data.source.db.PictureDao
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
@SuppressLint("CheckResult")  // subscriptions without reference get GC'ed after completion, no need to unsubscribe
class PictureRepository @Inject constructor(
    private val dao: PictureDao,
    private val api: PlaceholderApi
) {

    init {
        Timber.d( "PictureRepo created")
    }

    /**
     * Try to download from Internet, if there's problem -> get data from DB
     */
    fun getPicturesByAlbumId(albumId: Long): Single<List<Picture>> =
        api.getPicturesByAlbumId(albumId)
            .doOnSuccess { insertPostsInDb(it) }
            .onErrorResumeNext(dao.getPicturesByAlbumId(albumId))

    private fun insertPostsInDb(pics: List<Picture>) {
        dao.insertAll(pics)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(
                { Timber.d("Inserted ${pics.size} items in db") },
                { Timber.d("Error inserting items in db") })
    }

    fun deleteAll() {
        dao.deleteAll()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(
                { Timber.d("Deleted picture_table") },
                { Timber.d("Error deleting table") }
            )
    }
}