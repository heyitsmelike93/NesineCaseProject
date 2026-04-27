package com.example.nesinecaseproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nesinecaseproject.presentation.post.list.PostListScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.PostList.route
    ) {

        composable(
            Screen.PostList.route
        ) {
            PostListScreen(navController = navController)
        }
    }
}