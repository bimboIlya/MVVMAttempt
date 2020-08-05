package com.example.mvvmattempt.data.source.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mvvmattempt.data.Picture
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface PictureDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pictures: List<Picture>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPicture(picture: Picture): Completable

    @Query("SELECT * FROM picture_table WHERE id = :picId")
    fun getPictureById(picId: Long): Single<Picture>

    @Query("SELECT * FROM picture_table WHERE id = :picId")
    fun observePictureById(picId: Long): Observable<Picture>

    @Query("SELECT * FROM picture_table WHERE album_id = :albumId")
    fun getPicturesByAlbumId(albumId: Long): Single<List<Picture>>

    @Query("SELECT * FROM picture_table WHERE album_id = :albumId")
    fun observePicturesByAlbumId(albumId: Long): Observable<List<Picture>>

    @Query("DELETE FROM picture_table")
    fun deleteAll(): Completable
}