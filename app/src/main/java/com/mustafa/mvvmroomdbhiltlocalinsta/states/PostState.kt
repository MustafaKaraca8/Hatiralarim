package com.mustafa.mvvmroomdbhiltlocalinsta.states

import com.mustafa.mvvmroomdbhiltlocalinsta.database.PostModel


data class PostState(
    val posts: List<PostModel> = emptyList(),
    val id: Int = -1,
    var postDescription: String = "",
    var postImageUrl: String = "",
    var likeCount: String = "0",
    val sortType: SortType = SortType.LIKES_ASCENDING
)
