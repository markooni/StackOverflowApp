package com.example.stackoverflowapp.features.users.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stackoverflowapp.core.domain.result.AppResult
import com.example.stackoverflowapp.features.users.domain.model.User
import com.example.stackoverflowapp.features.users.domain.repository.UserRepository
import com.example.stackoverflowapp.features.users.domain.usecase.ObserveUsersWithFollowStateUseCase
import com.example.stackoverflowapp.features.users.domain.usecase.ToggleFollowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val observeUsersWithFollowState: ObserveUsersWithFollowStateUseCase,
    private val toggleFollow: ToggleFollowUseCase,
) : ViewModel() {

    private val rawUsers = MutableStateFlow<List<User>>(emptyList())

    private val _uiState = MutableStateFlow(UserListUiState())
    val uiState = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<UserListEvent>(
        replay = 0,
        extraBufferCapacity = 8,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val events: Flow<UserListEvent> = _events.asSharedFlow()

    private var loadJob: Job? = null

    init {
        viewModelScope.launch {
            observeUsersWithFollowState(rawUsers).collect { enriched ->
                _uiState.update { it.copy(users = enriched) }
            }
        }
        loadUsers()
    }

    fun loadUsers() {
        if (loadJob?.isActive == true) return
        loadJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            when (val result = userRepository.getTopUsers()) {
                is AppResult.Success -> {
                    rawUsers.value = result.data
                    _uiState.update { it.copy(isLoading = false, error = null) }
                }
                is AppResult.Failure -> {
                    val hadData = _uiState.value.users.isNotEmpty()
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = if (hadData) null else result.error,
                        )
                    }
                    if (hadData) {
                        _events.tryEmit(UserListEvent.RefreshFailed(result.error))
                    }
                }
            }
        }
    }

    fun onToggleFollow(user: User) {
        viewModelScope.launch {
            val result = toggleFollow(user.userId)
            if (result is AppResult.Failure) {
                _events.tryEmit(UserListEvent.FollowFailed(user.userId, result.error))
            }
        }
    }
}
