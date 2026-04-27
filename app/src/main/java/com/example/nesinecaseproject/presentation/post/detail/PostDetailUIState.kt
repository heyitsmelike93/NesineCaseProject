package com.example.nesinecaseproject.presentation.post.detail

import com.example.nesinecaseproject.domain.model.Post

data class PostDetailUIState(
    var isLoading: Boolean = false,
    var post: Post? = null,
    var error: String? = null

)