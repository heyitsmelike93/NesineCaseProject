package com.example.nesinecaseproject.presentation.post.list

import com.example.nesinecaseproject.domain.model.Post

data class PostListUIState(
    val isLoading: Boolean = false,
    val posts: List<Post> = emptyList(),
    val error: String? = null
)