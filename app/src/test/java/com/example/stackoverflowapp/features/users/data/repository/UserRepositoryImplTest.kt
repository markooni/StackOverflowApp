package com.example.stackoverflowapp.features.users.data.repository

import com.example.stackoverflowapp.core.domain.error.AppError
import com.example.stackoverflowapp.core.domain.result.AppResult
import com.example.stackoverflowapp.features.users.data.remote.StackOverflowApi
import com.example.stackoverflowapp.features.users.data.remote.dto.UserDto
import com.example.stackoverflowapp.features.users.data.remote.dto.UsersResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class UserRepositoryImplTest {

    private val api: StackOverflowApi = mockk()
    private val repository = UserRepositoryImpl(api)

    @Test
    fun `success maps DTOs to domain models`() = runTest {
        coEvery { api.getTopUsers() } returns UsersResponse(
            items = listOf(UserDto(1, "Jon", "img", 100)),
        )

        val result = repository.getTopUsers()

        result as AppResult.Success
        assertEquals(1, result.data.size)
        assertEquals("Jon", result.data[0].displayName)
        assertEquals(100, result.data[0].reputation)
    }

    @Test
    fun `socket timeout maps to Timeout`() = runTest {
        coEvery { api.getTopUsers() } throws SocketTimeoutException()
        assertEquals(AppError.Timeout, (repository.getTopUsers() as AppResult.Failure).error)
    }

    @Test
    fun `unknown host maps to Network`() = runTest {
        coEvery { api.getTopUsers() } throws UnknownHostException()
        assertEquals(AppError.Network, (repository.getTopUsers() as AppResult.Failure).error)
    }

    @Test
    fun `generic IO maps to Network`() = runTest {
        coEvery { api.getTopUsers() } throws IOException("boom")
        assertEquals(AppError.Network, (repository.getTopUsers() as AppResult.Failure).error)
    }

    @Test
    fun `429 maps to RateLimited`() = runTest {
        coEvery { api.getTopUsers() } throws httpException(429)
        assertEquals(AppError.RateLimited, (repository.getTopUsers() as AppResult.Failure).error)
    }

    @Test
    fun `5xx maps to Server`() = runTest {
        coEvery { api.getTopUsers() } throws httpException(503)
        assertEquals(AppError.Server, (repository.getTopUsers() as AppResult.Failure).error)
    }

    @Test
    fun `other 4xx maps to Http with code`() = runTest {
        coEvery { api.getTopUsers() } throws httpException(418)
        val error = (repository.getTopUsers() as AppResult.Failure).error
        assertTrue(error is AppError.Http)
        assertEquals(418, (error as AppError.Http).code)
    }

    @Test
    fun `unexpected exception maps to Unknown`() = runTest {
        coEvery { api.getTopUsers() } throws IllegalStateException("boom")
        assertEquals(AppError.Unknown, (repository.getTopUsers() as AppResult.Failure).error)
    }

    @Test(expected = CancellationException::class)
    fun `cancellation is rethrown not swallowed`(): Unit = runTest {
        coEvery { api.getTopUsers() } throws CancellationException("cancelled")
        repository.getTopUsers()
        fail("Expected CancellationException to propagate")
    }

    private fun httpException(code: Int): HttpException = HttpException(
        Response.error<Any>(code, "".toResponseBody("text/plain".toMediaType())),
    )
}
