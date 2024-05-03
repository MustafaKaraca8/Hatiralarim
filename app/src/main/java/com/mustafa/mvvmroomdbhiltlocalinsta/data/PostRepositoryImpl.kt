package com.mustafa.mvvmroomdbhiltlocalinsta.data

import com.mustafa.mvvmroomdbhiltlocalinsta.database.PostDao
import com.mustafa.mvvmroomdbhiltlocalinsta.database.PostModel
import com.mustafa.mvvmroomdbhiltlocalinsta.presentation.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(private val postDao: PostDao) : PostRepository {

    override suspend fun insertPost(postModel: PostModel) {
        postDao.insertPost(postModel)
    }

    override suspend fun updatePost(postModel: PostModel) {
        postDao.updatePost(postModel)
    }

    override suspend fun deletePost(postModel: PostModel) {
        postDao.deletePost(postModel)
    }

    override fun getPostsSortedByIdDescending(): Flow<List<PostModel>> {
        return postDao.getPostsByIdDescending()
    }

    override fun getPostsSortedByIdAscending(): Flow<List<PostModel>> {
        return postDao.getPostsByIdAscending()
    }

    override fun getPostsSortedByLikesDescending(): Flow<List<PostModel>> {
        return postDao.getPostsByLikesDescending()
    }

    override fun getPostsSortedByLikesAscending(): Flow<List<PostModel>> {
        return postDao.getPostsByLikesAscending()
    }

    override fun getPostsSortedByDescriptionLengthDescending(): Flow<List<PostModel>> {
        return postDao.getPostsByDescriptionLengthDescending()
    }

    override fun getPostsSortedByDescriptionLengthAscending(): Flow<List<PostModel>> {
        return postDao.getPostsByDescriptionLengthAscending()
    }

    override fun getPostsOrderedWithObfuscatedDate(): Flow<List<PostModel>> {
        return postDao.getPostsWithObfuscatedDate()
    }
}