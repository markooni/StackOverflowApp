package com.example.stackoverflowapp.features.users.data.repository

import com.example.stackoverflowapp.core.domain.error.AppError
import com.example.stackoverflowapp.core.domain.result.AppResult
import com.example.stackoverflowapp.features.users.data.remote.StackOverflowApi
import com.example.stackoverflowapp.features.users.data.remote.mapper.toDomainModels
import com.example.stackoverflowapp.features.users.domain.model.User
import com.example.stackoverflowapp.features.users.domain.repository.UserRepository
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: StackOverflowApi,
) : UserRepository {

    override suspend fun getTopUsers(): AppResult<List<User>> = try {
        val response = api.getTopUsers()
        AppResult.Success(response.items.toDomainModels())
    } catch (e: CancellationException) {
        throw e
    } catch (e: SocketTimeoutException) {
        AppResult.Failure(AppError.Timeout)
    } catch (e: UnknownHostException) {
        AppResult.Failure(AppError.Network)
    } catch (e: HttpException) {
        AppResult.Failure(httpErrorFor(e.code()))
    } catch (e: IOException) {
        AppResult.Failure(AppError.Network)
    } catch (e: Exception) {
        AppResult.Failure(AppError.Unknown)
    }

    private fun httpErrorFor(code: Int): AppError = when {
        code == 429 -> AppError.RateLimited
        code in 500..599 -> AppError.Server
        else -> AppError.Http(code)
    }
}
