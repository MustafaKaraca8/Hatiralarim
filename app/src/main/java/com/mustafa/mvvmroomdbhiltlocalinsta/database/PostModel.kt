package com.mustafa.mvvmroomdbhiltlocalinsta.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PostModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val description: String,
    val imagePath: String,
    val likeCount: String,
    val date: String
)

