package com.example.nesinecaseproject.domain.repository

import com.example.nesinecaseproject.domain.model.Post

interface PostRepository {
    suspend fun getPosts(): List<Post>
}