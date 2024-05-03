package com.mustafa.mvvmroomdbhiltlocalinsta.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Star
import androidx.compose.material.icons.sharp.ThumbUp
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.mustafa.mvvmroomdbhiltlocalinsta.R
import com.mustafa.mvvmroomdbhiltlocalinsta.database.PostModel
import com.mustafa.mvvmroomdbhiltlocalinsta.events.PostEvent
import com.mustafa.mvvmroomdbhiltlocalinsta.navigation.NavigationItem
import com.mustafa.mvvmroomdbhiltlocalinsta.screens.component.AddAndEditView
import com.mustafa.mvvmroomdbhiltlocalinsta.states.PostState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddPostScreen(
    navHostController: NavHostController,
    onEvent: (PostEvent) -> Unit,
    state: PostState,
    context: Context,
    post: PostModel?
) {
    val isPostEmpty = if (post == null) true else false

    fun requestPermission(uri: Uri) {
        val takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        context.contentResolver.takePersistableUriPermission(uri, takeFlags)
    }

    var imageUri by remember {
        mutableStateOf(
            if (isPostEmpty) {
                null
            } else {
                val imagePath = post?.imagePath
                if (imagePath.isNullOrEmpty()) {
                    null
                } else {
                    Uri.parse(imagePath)
                }
            }
        )
    }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                imageUri = it
                onEvent(PostEvent.SetPostImagePath(imagePath = imageUri.toString()))
                requestPermission(uri = it)
            }
        }
    )
    val dateTime = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("dd MM yyyy")
    val formattedDate = dateTime.format(formatter)

    if (isPostEmpty) {
        AddAndEditView(
            galleryLauncher = galleryLauncher,
            imageUri = imageUri,
            state = state,
            onEvent = onEvent,
            formattedDate = formattedDate,
            navHostController = navHostController,
            post = null
        )
    } else {
        var isInitialized by remember { mutableStateOf(false) }

        if (post != null && !isInitialized) {
            onEvent(PostEvent.SetPostDescription(post.description))
            onEvent(PostEvent.SetPostImagePath(Uri.decode(imageUri.toString())))
            state.likeCount = post.likeCount
            isInitialized = true
        }
        AddAndEditView(
            galleryLauncher = galleryLauncher,
            imageUri = imageUri,
            state = state,
            onEvent = onEvent,
            formattedDate = formattedDate,
            navHostController = navHostController,
            post = post
        )
    }
}
