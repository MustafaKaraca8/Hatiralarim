package com.mustafa.mvvmroomdbhiltlocalinsta

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mustafa.mvvmroomdbhiltlocalinsta.navigation.Navigation
import com.mustafa.mvvmroomdbhiltlocalinsta.presentation.PostViewModel
import com.mustafa.mvvmroomdbhiltlocalinsta.ui.theme.MvvmRoomDbHiltLocalInstaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        val postViewModel: PostViewModel by viewModels()
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val state by postViewModel.state.collectAsState()

            if (context is ComponentActivity) {
                context.window.statusBarColor = Color(246, 245, 249, 255).toArgb()
                context.window.navigationBarColor = Color(246, 245, 249, 255).toArgb()
            }

            SetStatusBarIconsColor(isLight = true)

            MvvmRoomDbHiltLocalInstaTheme {
                Navigation(
                    state = state,
                    onEvent = postViewModel::onEvent,
                    context = context
                )
            }
        }
    }
}

@Composable
fun SetStatusBarIconsColor(isLight: Boolean) {
    val view = LocalView.current
    DisposableEffect(view) {
        val originalFlags = view.systemUiVisibility
        if (!view.isInEditMode) {
            val newFlags = if (isLight) {
                originalFlags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                originalFlags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
            view.systemUiVisibility = newFlags
        }
        onDispose {
            view.systemUiVisibility = originalFlags
        }
    }
}

