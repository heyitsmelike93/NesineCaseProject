package com.example.nesinecaseproject.domain.usecase

import com.example.nesinecaseproject.domain.repository.PostRepository
import javax.inject.Inject

class PostUseCase @Inject constructor(
    private val repository: PostRepository
) {
    suspend fun invoke() = repository.getPosts()
}