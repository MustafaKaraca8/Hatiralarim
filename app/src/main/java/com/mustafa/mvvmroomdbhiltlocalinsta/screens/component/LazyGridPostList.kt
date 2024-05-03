package com.mustafa.mvvmroomdbhiltlocalinsta.screens.component

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Delete
import androidx.compose.material.icons.sharp.Edit
import androidx.compose.material.icons.sharp.ThumbUp
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.gson.Gson
import com.mustafa.mvvmroomdbhiltlocalinsta.R
import com.mustafa.mvvmroomdbhiltlocalinsta.database.PostModel
import com.mustafa.mvvmroomdbhiltlocalinsta.events.PostEvent
import com.mustafa.mvvmroomdbhiltlocalinsta.navigation.NavigationItem
import com.mustafa.mvvmroomdbhiltlocalinsta.states.PostState
import com.mustafa.mvvmroomdbhiltlocalinsta.ui.theme.MvvmRoomDbHiltLocalInstaTheme

@Composable
fun LazyGridPostList(
    navHostController: NavHostController,
    state: PostState,
    paddingValues: PaddingValues,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(top = paddingValues.calculateTopPadding()),
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
    ) {
        items(state.posts) { postModel ->
            LazyGridListCard(
                postModel = postModel,
                navHostController = navHostController
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LazyGridPostListPrev() {
    MvvmRoomDbHiltLocalInstaTheme {

    }
}