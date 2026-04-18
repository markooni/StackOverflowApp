package com.example.stackoverflowapp.features.users.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.stackoverflowapp.R
import com.example.stackoverflowapp.core.ui.designsystem.SoDimens
import com.example.stackoverflowapp.core.ui.designsystem.components.UserCard
import com.example.stackoverflowapp.core.ui.theme.StackOverflowTheme
import com.example.stackoverflowapp.features.users.domain.model.User
import com.example.stackoverflowapp.features.users.presentation.components.ErrorState
import com.example.stackoverflowapp.features.users.presentation.components.LoadingState
import com.example.stackoverflowapp.features.users.presentation.components.UserListTopBar
import com.example.stackoverflowapp.features.users.presentation.model.toUiText
import com.example.stackoverflowapp.features.users.presentation.viewmodel.UserListEvent
import com.example.stackoverflowapp.features.users.presentation.viewmodel.UserListUiState
import com.example.stackoverflowapp.features.users.presentation.viewmodel.UserListViewModel
import androidx.compose.runtime.LaunchedEffect

@Composable
fun UserListScreen(
    viewModel: UserListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val refreshFailedMessage = stringResource(R.string.error_refresh_failed)
    val followFailedMessage = stringResource(R.string.error_follow_failed)

    LaunchedEffect(viewModel) {
        viewModel.events.collect { event ->
            val message = when (event) {
                is UserListEvent.RefreshFailed -> refreshFailedMessage
                is UserListEvent.FollowFailed -> followFailedMessage
            }
            snackbarHostState.showSnackbar(message)
        }
    }

    UserListContent(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onRetry = viewModel::loadUsers,
        onToggleFollow = viewModel::onToggleFollow,
    )
}

@Composable
private fun UserListContent(
    uiState: UserListUiState,
    snackbarHostState: SnackbarHostState,
    onRetry: () -> Unit,
    onToggleFollow: (User) -> Unit,
) {
    Scaffold(
        topBar = { UserListTopBar() },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { paddingValues ->
        when {
            uiState.showFullScreenLoader -> {
                LoadingState(modifier = Modifier.padding(paddingValues))
            }
            uiState.showFullScreenError -> {
                uiState.error?.let { error ->
                    ErrorState(
                        message = error.toUiText(),
                        onRetry = onRetry,
                        modifier = Modifier.padding(paddingValues),
                    )
                }
            }
            else -> {
                Box(modifier = Modifier.padding(paddingValues)) {
                    if (uiState.isRefreshing) {
                        LinearProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.TopCenter),
                        )
                    }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("user_list"),
                        contentPadding = PaddingValues(SoDimens.SoSpacingLg),
                        verticalArrangement = Arrangement.spacedBy(SoDimens.SoSpacingSm),
                    ) {
                        items(
                            items = uiState.users,
                            key = { it.userId },
                        ) { user ->
                            UserCard(
                                avatarUrl = user.profileImage,
                                displayName = user.displayName,
                                reputation = user.reputation,
                                isFollowed = user.isFollowed,
                                onFollowToggle = { onToggleFollow(user) },
                            )
                        }
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun UserListScreenPreview() {
    StackOverflowTheme {
        UserListContent(
            uiState = UserListUiState(
                users = listOf(
                    User(1, "Jon Skeet", null, 1_454_978, isFollowed = false),
                    User(2, "Gordon Linoff", null, 1_200_000, isFollowed = true),
                ),
                isLoading = false,
            ),
            snackbarHostState = remember { SnackbarHostState() },
            onRetry = {},
            onToggleFollow = {},
        )
    }
}
