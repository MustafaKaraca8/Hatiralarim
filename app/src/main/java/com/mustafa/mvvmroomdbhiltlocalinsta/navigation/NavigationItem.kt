package com.mustafa.mvvmroomdbhiltlocalinsta.navigation

import com.mustafa.mvvmroomdbhiltlocalinsta.utility.DefaultValues

sealed class NavigationItem(val route: String) {
    data object MainScreen : NavigationItem(NavItem.MAIN_SCREEN.name)
    data object AddPostScreen : NavigationItem(NavItem.ADD_POST_SCREEN.name)
    data object PostDetailScreen : NavigationItem(NavItem.POST_SCREEN.name)

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                if (arg.isNotEmpty()) {
                    append("/$arg")
                } else {
                    append("/${DefaultValues.DEFAULT_VALUE}") // Varsayılan veya hata oluşturmayan bir değer ekleyin
                }
            }
        }
    }
}