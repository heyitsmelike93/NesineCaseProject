package com.example.nesinecaseproject.navigation

sealed class Screen(val route: String) {

    object PostList : Screen("postList")
}