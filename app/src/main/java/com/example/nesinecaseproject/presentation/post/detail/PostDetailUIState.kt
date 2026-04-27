package com.example.nesinecaseproject.presentation.post.detail

import com.example.nesinecaseproject.domain.model.Post

data class PostDetailUIState(
    val isLoading: Boolean = false,
    val post: Post? = null,
    val error: String? = null,
    val isUpdated: Boolean = false,
    val isUpdating: Boolean = false,
    val isEditing: Boolean = false,
    val editTitle: String = "",
    val editBody: String = "",
)
