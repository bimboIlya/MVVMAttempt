package com.example.mvvmattempt.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "picture_table")
data class Picture(

    @ColumnInfo(name = "album_id")
    val albumId: Long,
    
    @PrimaryKey
    val id: Long,

    val title: String,
    val url: String,

    @ColumnInfo(name = "thumbnail_url")
    val thumbnailUrl: String
)