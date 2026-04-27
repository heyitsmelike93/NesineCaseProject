package com.example.nesinecaseproject.navigation

sealed class Screen(val route: String) {

    object PostList : Screen("postList")

    object PostDetail : Screen("postDetail/{id}") {
        fun createRoute(id: Int) = "postDetail/$id"
    }
}