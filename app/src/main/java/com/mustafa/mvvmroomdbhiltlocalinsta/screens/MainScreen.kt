package com.mustafa.mvvmroomdbhiltlocalinsta.screens


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material.icons.sharp.ArrowDropDown
import androidx.compose.material.icons.sharp.Edit
import androidx.compose.material.icons.sharp.Favorite
import androidx.compose.material.icons.sharp.FavoriteBorder
import androidx.compose.material.icons.sharp.KeyboardArrowDown
import androidx.compose.material.icons.sharp.KeyboardArrowUp
import androidx.compose.material.icons.sharp.MoreVert
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mustafa.mvvmroomdbhiltlocalinsta.R
import com.mustafa.mvvmroomdbhiltlocalinsta.events.PostEvent
import com.mustafa.mvvmroomdbhiltlocalinsta.navigation.NavigationItem
import com.mustafa.mvvmroomdbhiltlocalinsta.screens.component.LazyGridPostList
import com.mustafa.mvvmroomdbhiltlocalinsta.states.PostState
import com.mustafa.mvvmroomdbhiltlocalinsta.states.SortType


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navHostController: NavHostController,
    state: PostState,
    onEvent: (PostEvent) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Hatıralarım",
                        fontSize = 27.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.SansSerif,
                        color = Color(105, 105, 105, 255),
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(249, 248, 252, 255)
                ),
                actions = {
                    Image(
                        imageVector = Icons.Sharp.MoreVert,
                        contentDescription = "Sort",
                        modifier = Modifier.clickable {
                            expanded = !expanded
                        })
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        SortType.values().forEach { sortType ->
                            DropdownMenuItem(text = {
                                Text(
                                    text = sortType.sortName,
                                    fontSize = 16.sp
                                )
                            }, onClick = {
                                onEvent(PostEvent.SortPost(sortType))
                                expanded = false
                            })
                        }

                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    state.postDescription = ""
                    navHostController.navigate(NavigationItem.AddPostScreen.withArgs(""))
                },
                containerColor = Color(96, 95, 100, 255),
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Sharp.Add, contentDescription = "")
            }
        }

    ) {

        if (state.posts.isEmpty()) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                Image(
                    painter = painterResource(id = R.drawable.add_image),
                    contentDescription = "",
                    modifier = Modifier
                        .size(200.dp)
                        .clip(
                            RoundedCornerShape(20.dp)
                        )
                        .shadow(
                            elevation = 2.dp,
                            shape = RoundedCornerShape(8.dp),
                            spotColor = Color.LightGray
                        ),
                    alpha = 0.16f
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Henüz Bir Hatıra Kaydetmediniz \nAşşağıda Bulunan",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.alpha(0.4f)
                )
                Spacer(modifier = Modifier.height(15.dp))
                Icon(
                    imageVector = Icons.TwoTone.Add,
                    contentDescription = "",
                    modifier = Modifier.alpha(0.4f)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Butonuna Basıp \nBir Hatıra Kaydederek Başlayabilirsiniz",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.alpha(0.4f)
                )
            }
        } else {
            LazyGridPostList(
                navHostController = navHostController,
                state = state,
                paddingValues = it,
            )
        }

    }
}
