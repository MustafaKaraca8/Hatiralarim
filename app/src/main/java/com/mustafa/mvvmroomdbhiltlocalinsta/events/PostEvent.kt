package com.mustafa.mvvmroomdbhiltlocalinsta.events

import com.mustafa.mvvmroomdbhiltlocalinsta.database.PostModel
import com.mustafa.mvvmroomdbhiltlocalinsta.states.SortType

sealed interface PostEvent {

    data object InsertPost : PostEvent
    data class UpdatePost(val id: Int) : PostEvent
    data class DeletePost(val postModel: PostModel) : PostEvent
    data class SetPostDescription(val description: String) : PostEvent
    data class SetPostImagePath(val imagePath: String) : PostEvent
    data class SetLikeCount(val likeCount: String) : PostEvent
    data class SortPost(val sortType: SortType) : PostEvent
}