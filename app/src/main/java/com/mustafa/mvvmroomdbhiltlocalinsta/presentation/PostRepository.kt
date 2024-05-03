package com.mustafa.mvvmroomdbhiltlocalinsta.presentation

import com.mustafa.mvvmroomdbhiltlocalinsta.database.PostModel
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    suspend fun insertPost(postModel: PostModel)

    suspend fun updatePost(postModel: PostModel)

    suspend fun deletePost(postModel: PostModel)

    fun getPostsSortedByIdDescending(): Flow<List<PostModel>>

    fun getPostsSortedByIdAscending(): Flow<List<PostModel>>

    fun getPostsSortedByLikesDescending(): Flow<List<PostModel>>

    fun getPostsSortedByLikesAscending(): Flow<List<PostModel>>

    fun getPostsSortedByDescriptionLengthDescending(): Flow<List<PostModel>>

    fun getPostsSortedByDescriptionLengthAscending(): Flow<List<PostModel>>

    fun getPostsOrderedWithObfuscatedDate(): Flow<List<PostModel>>
}