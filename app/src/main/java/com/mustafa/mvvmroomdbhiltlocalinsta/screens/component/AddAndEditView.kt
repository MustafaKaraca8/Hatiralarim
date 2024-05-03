package com.mustafa.mvvmroomdbhiltlocalinsta.screens.component

import android.net.Uri
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Delete
import androidx.compose.material.icons.sharp.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
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
import com.mustafa.mvvmroomdbhiltlocalinsta.R
import com.mustafa.mvvmroomdbhiltlocalinsta.database.PostModel
import com.mustafa.mvvmroomdbhiltlocalinsta.events.PostEvent
import com.mustafa.mvvmroomdbhiltlocalinsta.navigation.NavigationItem
import com.mustafa.mvvmroomdbhiltlocalinsta.states.PostState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAndEditView(
    galleryLauncher: ManagedActivityResultLauncher<String, Uri?>,
    imageUri: Uri?,
    state: PostState,
    onEvent: (PostEvent) -> Unit,
    formattedDate: String,
    navHostController: NavHostController,
    post: PostModel?
) {

    var likeCounter by remember { mutableIntStateOf(state.likeCount.toInt()) }
    var insertOrUpdate by remember { mutableStateOf(if (post == null) false else true) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(246, 245, 249, 255)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        ElevatedCard(
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 14.dp, vertical = 8.dp)
                .clickable {
                    galleryLauncher.launch("image/*")
                },
            elevation = CardDefaults.cardElevation(
                defaultElevation = 12.dp
            ),
        ) {
            AsyncImage(
                modifier = Modifier.size(400.dp, 350.dp),
                model = if (!insertOrUpdate) {
                    imageUri ?: R.drawable.selectimage
                } else state.postImageUrl,
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }

        Text(
            text = "Hatıra Yazısı",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp),
            fontSize = 25.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Start
        )
        OutlinedTextField(
            value = state.postDescription,
            onValueChange = { onEvent(PostEvent.SetPostDescription(it)) },
            modifier = Modifier.size(400.dp, 350.dp),
            placeholder = { Text(text = "Hatıranı yaz..") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color(245, 244, 248, 255),
                focusedBorderColor = Color(245, 244, 248, 255),
            ),
            textStyle = TextStyle.Default.copy(
                fontSize = 20.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.Normal
            )

        )

        Row(
            Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = Icons.Sharp.Star,
                contentDescription = "",
                colorFilter = ColorFilter.tint(
                    Color(255, 214, 0, 255)
                ),
                modifier = Modifier
                    .size(50.dp)
                    .padding(horizontal = 5.dp, vertical = 2.dp)
                    .clickable {
                        if (likeCounter < 10) {
                            likeCounter += 1
                            onEvent(PostEvent.SetLikeCount(likeCounter.toString()))
                        } else
                            return@clickable
                    }
            )
            Text(
                text = if (state.likeCount.equals("null")) "0" else likeCounter.toString(),
                fontWeight = FontWeight.SemiBold,
                fontSize = 19.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Tarih: $formattedDate",
                fontWeight = FontWeight.Medium,
                fontSize = 19.sp,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            ElevatedButton(
                onClick = {
                    if (!insertOrUpdate) {
                        onEvent(PostEvent.SetLikeCount(likeCounter.toString()))
                        onEvent(PostEvent.InsertPost)
                    } else {
                        onEvent(PostEvent.SetLikeCount(likeCounter.toString()))
                        onEvent(PostEvent.UpdatePost(post?.id!!))
                    }
                    navHostController.navigate(NavigationItem.MainScreen.route)
                },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color(91, 128, 228, 255),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = if (!insertOrUpdate) "Hatıranı Kaydet" else "Hatıranı Güncelle",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Medium,
                )
            }


            if (!insertOrUpdate) {
                return
            } else {
                Spacer(modifier = Modifier.weight(1f))
                ElevatedButton(
                    onClick = {
                        onEvent(PostEvent.DeletePost(post!!))
                        navHostController.navigate(NavigationItem.MainScreen.route)
                    },
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = Color(221, 91, 91, 255),
                        contentColor = Color.White
                    )
                ) {
                    Image(
                        imageVector = Icons.Sharp.Delete,
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(Color(255, 255, 255, 255))
                    )
                }
            }
        }
    }
}