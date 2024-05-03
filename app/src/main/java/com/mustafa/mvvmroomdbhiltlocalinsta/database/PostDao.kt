package com.mustafa.mvvmroomdbhiltlocalinsta.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPost(postModel: PostModel)

    @Update
    suspend fun updatePost(postModel: PostModel)

    @Delete
    suspend fun deletePost(postModel: PostModel)

    // ID'ye göre azalan sırayla postları getirir
    @Query("SELECT * FROM postmodel ORDER BY id DESC")
    fun getPostsByIdDescending(): Flow<List<PostModel>>

    // ID'ye göre artan sırayla postları getirir
    @Query("SELECT * FROM postmodel ORDER BY id ASC")
    fun getPostsByIdAscending(): Flow<List<PostModel>>

    // Beğeni sayısına göre azalan sırayla postları getirir
    @Query("SELECT * FROM postmodel ORDER BY likeCount DESC")
    fun getPostsByLikesDescending(): Flow<List<PostModel>>

    // Beğeni sayısına göre artan sırayla postları getirir
    @Query("SELECT * FROM postmodel ORDER BY likeCount ASC")
    fun getPostsByLikesAscending(): Flow<List<PostModel>>

    // Açıklama uzunluğuna göre azalan sırayla postları getirir
    @Query("SELECT * FROM postmodel ORDER BY LENGTH(description) DESC")
    fun getPostsByDescriptionLengthDescending(): Flow<List<PostModel>>

    // Açıklama uzunluğuna göre artan sırayla postları getirir
    @Query("SELECT * FROM postmodel ORDER BY LENGTH(description) ASC")
    fun getPostsByDescriptionLengthAscending(): Flow<List<PostModel>>

    // Düzenlenmiş kayıtları bulmak için
    @Query("SELECT * FROM postmodel WHERE SUBSTR(date, -2, 2) = '**' ORDER BY id ASC")
    fun getPostsWithObfuscatedDate(): Flow<List<PostModel>>
}
