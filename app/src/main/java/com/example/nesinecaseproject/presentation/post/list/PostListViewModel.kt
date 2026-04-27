package com.example.nesinecaseproject.presentation.post.list

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
class PostListViewModel @Inject constructor(
    private val postUseCase: PostUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PostListUIState())
    val uiState: StateFlow<PostListUIState> = _uiState.asStateFlow()

    init {
        getPosts()
    }

    private fun getPosts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val posts = postUseCase.invoke()
                _uiState.update {
                    it.copy(posts = posts, isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }
}