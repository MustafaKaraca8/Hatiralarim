package com.mustafa.mvvmroomdbhiltlocalinsta.screens

import android.net.Uri
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mustafa.mvvmroomdbhiltlocalinsta.R
import com.mustafa.mvvmroomdbhiltlocalinsta.database.PostModel
import com.mustafa.mvvmroomdbhiltlocalinsta.events.PostEvent
import com.mustafa.mvvmroomdbhiltlocalinsta.navigation.NavigationItem
import com.mustafa.mvvmroomdbhiltlocalinsta.ui.theme.MvvmRoomDbHiltLocalInstaTheme

@Composable
fun PostDetailsScreen(
    post: PostModel,
) {
    val uriToImagePath = Uri.decode(post.imagePath)
    val scrollState = rememberScrollState()
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
                .padding(horizontal = 14.dp, vertical = 8.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 12.dp
            ),
        ) {
            AsyncImage(
                modifier = Modifier.size(400.dp, 350.dp),
                model = uriToImagePath,
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }

        Text(
            text = "Hatıra Yazısı",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Serif
        )
        Text(
            text = post.description,
            modifier = Modifier
                .size(400.dp, 450.dp)
                .border(width = 3.dp, color = Color.LightGray, shape = RoundedCornerShape(10.dp))
                .padding(10.dp)
                .verticalScroll(scrollState),
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            color = Color.DarkGray,
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
            )
            Text(
                text = post.likeCount,
                fontWeight = FontWeight.SemiBold,
                fontSize = 19.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Tarih: ${post.date}",
                fontWeight = FontWeight.Medium,
                fontSize = 19.sp,
            )
        }
    }
}
