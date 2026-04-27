package com.example.nesinecaseproject.data.remote.api

import com.example.nesinecaseproject.data.remote.dto.PostDto
import retrofit2.http.GET
import retrofit2.http.Path

interface PostApi {

    @GET("posts")
    suspend fun getPosts(): List<PostDto>

    @GET("posts/{id}")
    suspend fun getPostById(
        @Path("id") id: Int
    ): PostDto

}