package com.example.mvvmattempt.data.source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mvvmattempt.data.Picture
import com.example.mvvmattempt.data.Post

@Database(
    entities = [Picture::class, Post::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pictureDao(): PictureDao
    abstract fun postDao(): PostDao
}