package com.example.stackoverflowapp.features.users.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.stackoverflowapp.core.ui.desingsystem.SoDimens
import com.example.stackoverflowapp.core.ui.desingsystem.components.UserCard
import com.example.stackoverflowapp.core.ui.theme.StackUsersTheme
import com.example.stackoverflowapp.features.users.presentation.components.ErrorState
import com.example.stackoverflowapp.features.users.presentation.components.LoadingState
import com.example.stackoverflowapp.features.users.presentation.viewModel.UserListUiState
import com.example.stackoverflowapp.features.users.presentation.viewModel.UserListViewModel
import com.example.stackoverflowapp.features.users.domain.model.User
import com.example.stackoverflowapp.features.users.presentation.components.UserListTopBar
import com.example.stackoverflowapp.features.users.presentation.model.toUiText


@Composable
fun UserListScreen(
    viewModel: UserListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    UserListContent(
        uiState = uiState,
        onRetry = viewModel::loadUsers
    )
}

@Composable
private fun UserListContent(
    uiState: UserListUiState,
    onRetry: () -> Unit
) {
    Scaffold(
        topBar = { UserListTopBar() }
    ) { paddingValues ->
        when (val state = uiState) {
            is UserListUiState.Loading -> {
                LoadingState(
                    modifier = Modifier.padding(paddingValues)
                )
            }
            is UserListUiState.Error -> {
                ErrorState(
                    message = state.error.toUiText(),
                    onRetry = onRetry,
                    modifier = Modifier.padding(paddingValues)
                )
            }
            is UserListUiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .testTag("user_list"),
                    contentPadding = PaddingValues(SoDimens.SoSpacingLg),
                    verticalArrangement = Arrangement.spacedBy(SoDimens.SoSpacingSm)
                ) {
                    items(
                        items = state.users,
                        key = { it.userId }
                    ) { user ->
                        UserCard(
                            avatarUrl = user.profileImage,
                            displayName = user.displayName,
                            reputation = user.reputation,
                            isFollowed = user.isFollowed,
                            onFollowToggle = {  }
                        )
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun UserListScreenPreview() {
    StackUsersTheme {
        UserListContent(
            uiState = UserListUiState.Success(
                users = listOf(
                    User(
                        userId = 1,
                        displayName = "Jon Skeet",
                        profileImage = null,
                        reputation = 1_454_978,
                        isFollowed = false
                    ),
                    User(
                        userId = 2,
                        displayName = "Gordon Linoff",
                        profileImage = null,
                        reputation = 1_200_000,
                        isFollowed = true
                    )
                )
            ),
            onRetry = {}
        )
    }
}
