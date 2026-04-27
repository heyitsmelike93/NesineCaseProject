package com.example.nesinecaseproject.domain.model

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
    val imageUrl: String,
)