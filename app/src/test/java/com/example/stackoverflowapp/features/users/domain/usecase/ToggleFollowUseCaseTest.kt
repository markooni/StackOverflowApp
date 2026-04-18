package com.example.stackoverflowapp.features.users.domain.usecase

import com.example.stackoverflowapp.core.domain.error.AppError
import com.example.stackoverflowapp.core.domain.result.AppResult
import com.example.stackoverflowapp.features.users.domain.repository.FollowRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ToggleFollowUseCaseTest {

    private val followRepository: FollowRepository = mockk(relaxUnitFun = true)
    private val toggle = ToggleFollowUseCase(followRepository)

    @Test
    fun `when not followed, follow is invoked`() = runTest {
        coEvery { followRepository.isFollowed(7) } returns false

        val result = toggle(userId = 7)

        assertEquals(AppResult.Success(Unit), result)
        coVerify(exactly = 1) { followRepository.follow(7) }
    }

    @Test
    fun `when already followed, unfollow is invoked`() = runTest {
        coEvery { followRepository.isFollowed(7) } returns true

        val result = toggle(userId = 7)

        assertEquals(AppResult.Success(Unit), result)
        coVerify(exactly = 1) { followRepository.unfollow(7) }
    }

    @Test
    fun `unexpected exception is wrapped as Failure`() = runTest {
        coEvery { followRepository.isFollowed(7) } throws IllegalStateException()

        val result = toggle(userId = 7)

        assertEquals(AppResult.Failure(AppError.Unknown), result)
    }

    @Test(expected = CancellationException::class)
    fun `cancellation propagates`(): Unit = runTest {
        coEvery { followRepository.isFollowed(7) } throws CancellationException()
        toggle(userId = 7)
    }
}
