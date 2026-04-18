package com.example.stackoverflowapp.features.users.presentation.viewmodel

import com.example.stackoverflowapp.core.domain.error.AppError
import com.example.stackoverflowapp.features.users.domain.model.User

data class UserListUiState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = true,
    val error: AppError? = null,
) {
    val showFullScreenLoader: Boolean get() = isLoading && users.isEmpty()
    val showFullScreenError: Boolean get() = error != null && users.isEmpty()
    val isRefreshing: Boolean get() = isLoading && users.isNotEmpty()
}

sealed interface UserListEvent {
    data class FollowFailed(val userId: Int, val error: AppError) : UserListEvent
    data class RefreshFailed(val error: AppError) : UserListEvent
}
