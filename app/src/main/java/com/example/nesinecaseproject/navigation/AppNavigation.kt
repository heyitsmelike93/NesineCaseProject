package com.example.nesinecaseproject.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nesinecaseproject.presentation.post.detail.PostDetailScreen
import com.example.nesinecaseproject.presentation.post.detail.PostDetailViewModel
import com.example.nesinecaseproject.presentation.post.list.PostListScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = Screen.PostList.route
    ) {

        composable(
            Screen.PostList.route
        ) {
            PostListScreen(
                navController = navController, onPostClick = { postId ->
                    navController.navigate(
                        Screen.PostDetail.createRoute(postId)
                    )
                })
        }

        composable(
            Screen.PostDetail.route, arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                })
        ) {
            val viewModel: PostDetailViewModel = hiltViewModel()
            PostDetailScreen(navController = navController, viewModel = viewModel)
        }
    }
}