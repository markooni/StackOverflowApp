package com.example.stackoverflowapp.features.users.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stackoverflowapp.core.domain.error.AppError
import com.example.stackoverflowapp.core.domain.result.AppResult
import com.example.stackoverflowapp.features.users.domain.model.User
import com.example.stackoverflowapp.features.users.domain.usecase.GetTopUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface UserListUiState {
    data object Loading : UserListUiState
    data class Success(val users: List<User>) : UserListUiState
    data class Error(val error: AppError) : UserListUiState
}

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getTopUsersUseCase: GetTopUsersUseCase
) : ViewModel() {

    private val apiUsers = MutableStateFlow<List<User>>(emptyList())
    private val isLoading = MutableStateFlow(true)
    private val errorMessage = MutableStateFlow<AppError?>(null)

    val uiState: StateFlow<UserListUiState> =
        combine(isLoading, errorMessage, apiUsers) { loading, error, users ->
            when {
                loading -> UserListUiState.Loading
                error != null -> UserListUiState.Error(error = error)
                else -> UserListUiState.Success(users)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UserListUiState.Loading
        )

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null

            when (val result = getTopUsersUseCase()) {
                is AppResult.Success -> {
                    apiUsers.value = result.data
                }
                is AppResult.Failure -> {
                    errorMessage.value = result.error
                }
            }

            isLoading.value = false
        }
    }
}
