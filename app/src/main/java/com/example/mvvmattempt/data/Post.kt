package com.example.mvvmattempt.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "post_table")
data class Post(
    @ColumnInfo(name = "user_id")
    val userId: Long,

    @PrimaryKey
    val id: Long,

    val title: String,
    val body: String
) : Parcelable