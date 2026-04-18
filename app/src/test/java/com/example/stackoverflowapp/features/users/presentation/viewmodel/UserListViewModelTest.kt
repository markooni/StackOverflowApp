package com.example.stackoverflowapp.features.users.presentation.viewmodel

import app.cash.turbine.test
import com.example.stackoverflowapp.core.domain.error.AppError
import com.example.stackoverflowapp.core.domain.result.AppResult
import com.example.stackoverflowapp.features.users.domain.model.User
import com.example.stackoverflowapp.features.users.domain.repository.FollowRepository
import com.example.stackoverflowapp.features.users.domain.repository.UserRepository
import com.example.stackoverflowapp.features.users.domain.usecase.ObserveUsersWithFollowStateUseCase
import com.example.stackoverflowapp.features.users.domain.usecase.ToggleFollowUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val userRepository: UserRepository = mockk()
    private val followRepository: FollowRepository = mockk()
    private val toggleFollow: ToggleFollowUseCase = mockk()

    private val followedIds = MutableStateFlow<Set<Int>>(emptySet())

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        every { followRepository.observeFollowedIds() } returns followedIds
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial load success exposes users with follow state`() = runTest(testDispatcher) {
        val users = listOf(User(1, "Jon", null, 100), User(2, "Linus", null, 200))
        coEvery { userRepository.getTopUsers() } returns AppResult.Success(users)
        followedIds.value = setOf(2)

        val vm = newViewModel()
        advanceUntilIdle()

        val state = vm.uiState.value
        assertEquals(2, state.users.size)
        assertFalse(state.users[0].isFollowed)
        assertTrue(state.users[1].isFollowed)
        assertFalse(state.isLoading)
        assertNull(state.error)
    }

    @Test
    fun `initial load failure surfaces full-screen error`() = runTest(testDispatcher) {
        coEvery { userRepository.getTopUsers() } returns AppResult.Failure(AppError.Network)

        val vm = newViewModel()
        advanceUntilIdle()

        val state = vm.uiState.value
        assertTrue(state.users.isEmpty())
        assertTrue(state.showFullScreenError)
        assertEquals(AppError.Network, state.error)
    }

    @Test
    fun `refresh failure with existing data preserves data and emits event`() =
        runTest(testDispatcher) {
            val users = listOf(User(1, "Jon", null, 100))
            coEvery { userRepository.getTopUsers() } returnsMany listOf(
                AppResult.Success(users),
                AppResult.Failure(AppError.Timeout),
            )

            val vm = newViewModel()
            advanceUntilIdle()
            assertEquals(users.map { it.userId }, vm.uiState.value.users.map { it.userId })

            vm.events.test {
                vm.loadUsers()
                advanceUntilIdle()
                val event = awaitItem()
                assertEquals(UserListEvent.RefreshFailed(AppError.Timeout), event)
                cancelAndIgnoreRemainingEvents()
            }

            val state = vm.uiState.value
            assertEquals(1, state.users.size)
            assertNull(state.error)
        }

    @Test
    fun `toggle follow failure emits FollowFailed event`() = runTest(testDispatcher) {
        coEvery { userRepository.getTopUsers() } returns AppResult.Success(emptyList())
        val user = User(42, "Alice", null, 50)
        coEvery { toggleFollow(42) } returns AppResult.Failure(AppError.Unknown)

        val vm = newViewModel()
        advanceUntilIdle()

        vm.events.test {
            vm.onToggleFollow(user)
            advanceUntilIdle()
            assertEquals(
                UserListEvent.FollowFailed(userId = 42, error = AppError.Unknown),
                awaitItem(),
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `concurrent loadUsers calls coalesce`() = runTest(testDispatcher) {
        var callCount = 0
        coEvery { userRepository.getTopUsers() } coAnswers {
            callCount++
            AppResult.Success(emptyList())
        }

        val vm = newViewModel()
        vm.loadUsers()
        vm.loadUsers()
        advanceUntilIdle()

        // init triggers one load; the explicit calls above coalesce while in-flight.
        assertEquals(1, callCount)
    }

    private fun newViewModel(): UserListViewModel = UserListViewModel(
        userRepository = userRepository,
        observeUsersWithFollowState = ObserveUsersWithFollowStateUseCase(followRepository),
        toggleFollow = toggleFollow,
    )
}
