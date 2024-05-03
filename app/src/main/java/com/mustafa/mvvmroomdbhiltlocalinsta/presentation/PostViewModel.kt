package com.mustafa.mvvmroomdbhiltlocalinsta.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustafa.mvvmroomdbhiltlocalinsta.database.PostModel
import com.mustafa.mvvmroomdbhiltlocalinsta.events.PostEvent
import com.mustafa.mvvmroomdbhiltlocalinsta.states.PostState
import com.mustafa.mvvmroomdbhiltlocalinsta.states.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {

    private val _sortType = MutableStateFlow(SortType.ID_DESCENDING)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _posts = _sortType.flatMapLatest { sortType ->
        when (sortType) {
            SortType.ID_DESCENDING -> {
                postRepository.getPostsSortedByIdDescending()
            }

            SortType.ID_ASCENDING -> {
                postRepository.getPostsSortedByIdAscending()
            }

            SortType.LIKES_DESCENDING -> {
                postRepository.getPostsSortedByLikesDescending()
            }

            SortType.LIKES_ASCENDING -> {
                postRepository.getPostsSortedByLikesAscending()
            }

            SortType.DESCRIPTION_LENGTH_DESCENDING -> {
                postRepository.getPostsSortedByDescriptionLengthDescending()
            }

            SortType.DESCRIPTION_LENGTH_ASCENDING -> {
                postRepository.getPostsSortedByDescriptionLengthAscending()
            }

            SortType.OBFUSCATED_DATE -> {
                postRepository.getPostsOrderedWithObfuscatedDate()
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    private val _state = MutableStateFlow(PostState())

    val state = combine(_state, _sortType, _posts) { state, sortType, posts ->
        state.copy(
            sortType = sortType,
            posts = posts,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PostState()
    )

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: PostEvent) {
        when (event) {
            PostEvent.InsertPost -> {
                val postDescription = state.value.postDescription
                val postImagePath = Uri.encode(state.value.postImageUrl)
                val likeCount = state.value.likeCount

                val date = LocalDate.now()
                val formatter = DateTimeFormatter.ofPattern("dd MM yyyy")
                val formattedDate = date.format(formatter)

                if (postDescription.isBlank() || postImagePath.isBlank()) {
                    return
                }

                val post = PostModel(
                    description = postDescription,
                    imagePath = postImagePath,
                    date = formattedDate,
                    likeCount = likeCount
                )

                viewModelScope.launch(Dispatchers.IO) {
                    postRepository.insertPost(post)
                }

                _state.update {
                    it.copy(
                        postDescription = "",
                        postImageUrl = "",
                        likeCount = "0"
                    )
                }

            }

            is PostEvent.UpdatePost -> {
                val postDescription = state.value.postDescription
                val postImagePath = Uri.encode(state.value.postImageUrl)
                val likeCount = state.value.likeCount
                _state.update {
                    it.copy(
                        id = event.id
                    )
                }
                val id = state.value.id

                val date = LocalDate.now()
                val formatter = DateTimeFormatter.ofPattern("dd MM yyyy")
                val formattedDate = date.format(formatter)


                if (postDescription.isBlank() || id == -1) {
                    return
                }
                viewModelScope.launch {
                    val post = PostModel(
                        id = id,
                        description = postDescription,
                        likeCount = likeCount,
                        imagePath = postImagePath,
                        date = "$formattedDate**"
                    )

                    postRepository.updatePost(post)
                }
                _state.update {
                    it.copy(
                        postDescription = "",
                        postImageUrl = "",
                        likeCount = "0"
                    )
                }
            }

            is PostEvent.DeletePost -> {
                viewModelScope.launch {
                    postRepository.deletePost(event.postModel)
                }
            }

            is PostEvent.SetPostDescription -> {
                _state.update {
                    it.copy(
                        postDescription = event.description
                    )
                }
            }

            is PostEvent.SetPostImagePath -> {
                _state.update {
                    it.copy(
                        postImageUrl = event.imagePath
                    )
                }
            }

            is PostEvent.SetLikeCount -> {
                _state.update {
                    it.copy(
                        likeCount = event.likeCount
                    )
                }
            }

            is PostEvent.SortPost -> {
                _sortType.value = event.sortType
            }
        }
    }
}