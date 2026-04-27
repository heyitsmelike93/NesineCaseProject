package com.example.nesinecaseproject.data.remote.mapper

import com.example.nesinecaseproject.data.remote.dto.PostDto
import com.example.nesinecaseproject.domain.model.Post

fun PostDto.toDomain(): Post {
    return Post(
        userId = userId,
        id = id,
        title = title,
        body = body,
        imageUrl = "https://picsum.photos/300/300?random=$id"
    )
}

fun Post.toDto() : PostDto {
    return PostDto(
        userId = userId,
        id = id,
        title = title,
        body = body,
    )
}
