package com.example.nesinecaseproject.domain.usecase

import com.example.nesinecaseproject.domain.model.Post
import com.example.nesinecaseproject.domain.repository.PostRepository
import javax.inject.Inject

class PostUseCase @Inject constructor(
    private val repository: PostRepository
) {
    suspend fun invoke() = repository.getPosts()

    suspend fun getPostById(id: Int) = repository.getPostById(id)

    suspend fun updatePost(post: Post) = repository.updatePost(post)
}