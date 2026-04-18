package com.example.stackoverflowapp.features.users.domain.usecase

import com.example.stackoverflowapp.features.users.domain.model.User
import com.example.stackoverflowapp.features.users.domain.repository.FollowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class ObserveUsersWithFollowStateUseCase @Inject constructor(
    private val followRepository: FollowRepository,
) {
    operator fun invoke(rawUsers: Flow<List<User>>): Flow<List<User>> =
        combine(rawUsers, followRepository.observeFollowedIds()) { users, followedIds ->
            users.map { user -> user.copy(isFollowed = user.userId in followedIds) }
        }
}
