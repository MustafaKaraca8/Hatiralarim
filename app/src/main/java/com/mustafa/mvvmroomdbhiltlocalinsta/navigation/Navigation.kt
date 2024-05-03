package com.mustafa.mvvmroomdbhiltlocalinsta.navigation

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.mustafa.mvvmroomdbhiltlocalinsta.database.PostModel
import com.mustafa.mvvmroomdbhiltlocalinsta.events.PostEvent
import com.mustafa.mvvmroomdbhiltlocalinsta.presentation.PostViewModel
import com.mustafa.mvvmroomdbhiltlocalinsta.screens.AddPostScreen
import com.mustafa.mvvmroomdbhiltlocalinsta.screens.MainScreen
import com.mustafa.mvvmroomdbhiltlocalinsta.screens.PostDetailsScreen
import com.mustafa.mvvmroomdbhiltlocalinsta.states.PostState
import com.mustafa.mvvmroomdbhiltlocalinsta.utility.DefaultValues

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(
    startDestination: String = NavigationItem.MainScreen.route,
    state: PostState,
    onEvent: (PostEvent) -> Unit,
    context: Context
) {

    val navHostController = rememberNavController()

    NavHost(navController = navHostController, startDestination = startDestination) {
        composable(route = NavigationItem.MainScreen.route) {
            MainScreen(navHostController = navHostController, state = state, onEvent = onEvent)
        }

        composable(
            route = NavigationItem.AddPostScreen.route + "/{post_model}",
            arguments = listOf(navArgument("post_model") { type = NavType.StringType })
        ) {
            val json = it.arguments?.getString("post_model")

            if (json.equals(DefaultValues.DEFAULT_VALUE)) {
                AddPostScreen(
                    navHostController = navHostController,
                    state = state,
                    onEvent = onEvent,
                    context = context,
                    post = null
                )
            } else {
                val post = Gson().fromJson(json, PostModel::class.java)
                AddPostScreen(
                    navHostController = navHostController,
                    state = state,
                    onEvent = onEvent,
                    context = context,
                    post = post
                )
            }

        }

        composable(
            route = NavigationItem.PostDetailScreen.route + "/{post_model}",
            arguments = listOf(navArgument("post_model") { type = NavType.StringType })
        ) {
            val json = it.arguments?.getString("post_model")
            val post = Gson().fromJson(json, PostModel::class.java)
            PostDetailsScreen(post = post)
        }
    }

}