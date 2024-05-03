package com.mustafa.mvvmroomdbhiltlocalinsta.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PostModel::class], version = 1)
abstract class PostDatabase : RoomDatabase() {
    abstract val postDao: PostDao
}