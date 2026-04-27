package com.example.nesinecaseproject.presentation.post.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nesinecaseproject.domain.model.Post
import com.example.nesinecaseproject.domain.usecase.PostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val postUseCase: PostUseCase
) : ViewModel() {

    private val postId: Int = savedStateHandle["id"]!!

    private val _uiState = MutableStateFlow(PostDetailUIState())
    val uiState: StateFlow<PostDetailUIState> = _uiState

    init {
        getPostById(postId)
    }
    fun getPostById(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val post = postUseCase.getPostById(id)
                _uiState.update { it.copy(post = post, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun updatePost(updatedPost: Post) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val post = postUseCase.updatePost(updatedPost)

                _uiState.update {
                    it.copy(
                        post = post,
                        isUpdated = true
                    )
                }

            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }
}