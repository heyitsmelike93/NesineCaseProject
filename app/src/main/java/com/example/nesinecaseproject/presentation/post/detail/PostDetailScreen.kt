package com.example.nesinecaseproject.presentation.post.detail

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen(
    viewModel: PostDetailViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.isUpdated) {
        if (uiState.isUpdated) {
            snackbarHostState.showSnackbar("Update successful")
            viewModel.resetUpdateState()
        }
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        contentWindowInsets = WindowInsets.systemBars,
        topBar = {
            TopAppBar(
                title = { Text("Post Detail") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                },
                actions = {
                    if (uiState.post != null) {
                        IconButton(
                            onClick = { if (!uiState.isUpdating) viewModel.toggleEditing() }
                        ) {
                            Icon(Icons.Default.Edit, null)
                        }
                    }
                }
            )
        }
    ) { padding ->

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.post == null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Something went wrong.")
                        Spacer(Modifier.height(12.dp))
                        Button(onClick = { viewModel.retryLoad() }) {
                            Text("Retry")
                        }
                    }
                }
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .imePadding()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(6.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {

                            Text(
                                "TITLE",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Spacer(Modifier.height(6.dp))

                            Crossfade(uiState.isEditing) { editing ->
                                if (editing) {
                                    TextField(
                                        value = uiState.editTitle,
                                        onValueChange = { viewModel.updateTitle(it) },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(MaterialTheme.colorScheme.surfaceVariant)
                                            .padding(12.dp)
                                    ) {
                                        Text(uiState.editTitle)
                                    }
                                }
                            }

                            Spacer(Modifier.height(16.dp))

                            Text(
                                "DESCRIPTION",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Spacer(Modifier.height(6.dp))

                            Crossfade(uiState.isEditing) { editing ->
                                if (editing) {
                                    TextField(
                                        value = uiState.editBody,
                                        onValueChange = { viewModel.updateBody(it) },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(MaterialTheme.colorScheme.surfaceVariant)
                                            .padding(12.dp)
                                    ) {
                                        Text(uiState.editBody)
                                    }
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(20.dp))

                    if (uiState.isEditing) {
                        Button(
                            onClick = { viewModel.updatePost() },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !uiState.isUpdating
                        ) {
                            Text("Submit")
                        }
                    }
                }

                if (uiState.isUpdating) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.4f)),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}
