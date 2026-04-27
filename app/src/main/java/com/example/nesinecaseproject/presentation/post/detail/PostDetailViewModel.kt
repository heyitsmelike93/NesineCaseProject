package com.example.nesinecaseproject.presentation.post.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nesinecaseproject.domain.usecase.PostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val postUseCase: PostUseCase
) : ViewModel() {

    private val postId: Int = checkNotNull(savedStateHandle["id"]) { "postId is required" }

    private val _uiState = MutableStateFlow(PostDetailUIState())
    val uiState: StateFlow<PostDetailUIState> = _uiState.asStateFlow()

    init {
        getPostById(postId)
    }

    private fun getPostById(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val post = postUseCase.getPostById(id)
                _uiState.update { it.copy(post = post, editTitle = post.title, editBody = post.body) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun retryLoad() {
        getPostById(postId)
    }

    fun updatePost() {
        val state = _uiState.value
        val post = state.post ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(isUpdating = true) }
            try {
                val updated = postUseCase.updatePost(post.copy(title = state.editTitle, body = state.editBody))
                _uiState.update { it.copy(post = updated, isUpdated = true, isEditing = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            } finally {
                _uiState.update { it.copy(isUpdating = false) }
            }
        }
    }

    fun toggleEditing() {
        _uiState.update { it.copy(isEditing = !it.isEditing) }
    }

    fun updateTitle(title: String) {
        _uiState.update { it.copy(editTitle = title) }
    }

    fun updateBody(body: String) {
        _uiState.update { it.copy(editBody = body) }
    }

    fun resetUpdateState() {
        _uiState.update { it.copy(isUpdated = false) }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
