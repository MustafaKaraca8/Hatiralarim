package com.mustafa.mvvmroomdbhiltlocalinsta.screens.component

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.sharp.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.mustafa.mvvmroomdbhiltlocalinsta.database.PostModel
import com.mustafa.mvvmroomdbhiltlocalinsta.navigation.NavigationItem

@Composable
fun LazyGridListCard(
    postModel: PostModel,
    navHostController: NavHostController,
) {

    val uriToImagePath = Uri.decode(postModel.imagePath)
    val postJson = Gson().toJson(postModel, PostModel::class.java)
    Column(
        modifier = Modifier.clickable {
            navHostController.navigate(
                NavigationItem.PostDetailScreen.withArgs(
                    postJson
                )
            )
        }
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
        ) {
            AsyncImage(
                model = uriToImagePath,
                contentDescription = "",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 2.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.padding(2.dp),
                    text = postModel.description,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis

                )
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        imageVector = Icons.Sharp.Star,
                        contentDescription = "",
                        modifier = Modifier.size(25.dp),
                        colorFilter = ColorFilter.tint(
                            Color(255, 214, 0, 255)
                        )
                    )
                    Text(
                        text = postModel.likeCount,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
                            .graphicsLayer {
                                rotationZ = -90f
                            }
                            .clickable {
                                navHostController.navigate(
                                    NavigationItem.AddPostScreen.withArgs(postJson)
                                )
                            },
                        colorFilter = ColorFilter.tint(Color(96, 136, 160, 255))
                    )
                }

            }
        }
    }

}
