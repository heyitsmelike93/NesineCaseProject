package com.example.nesinecaseproject.data.repository

import com.example.nesinecaseproject.data.remote.api.PostApi
import com.example.nesinecaseproject.data.remote.mapper.toDomain
import com.example.nesinecaseproject.domain.model.Post
import com.example.nesinecaseproject.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val api: PostApi
) : PostRepository {
    override suspend fun getPosts(): List<Post> {
        return api.getPosts().map { it.toDomain() }
    }

    override suspend fun getPostById(id: Int): Post {
        return api.getPostById(id).toDomain()
    }
}