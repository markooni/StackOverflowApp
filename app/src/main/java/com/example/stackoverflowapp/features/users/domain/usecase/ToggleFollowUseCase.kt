package com.example.stackoverflowapp.features.users.domain.usecase

import com.example.stackoverflowapp.core.domain.error.AppError
import com.example.stackoverflowapp.core.domain.result.AppResult
import com.example.stackoverflowapp.features.users.domain.repository.FollowRepository
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class ToggleFollowUseCase @Inject constructor(
    private val followRepository: FollowRepository,
) {
    suspend operator fun invoke(userId: Int): AppResult<Unit> = try {
        if (followRepository.isFollowed(userId)) {
            followRepository.unfollow(userId)
        } else {
            followRepository.follow(userId)
        }
        AppResult.Success(Unit)
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        AppResult.Failure(AppError.Unknown)
    }
}
