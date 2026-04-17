package com.example.stackoverflowapp.features.users.remote.repository

import com.example.stackoverflowapp.core.domain.error.AppError
import com.example.stackoverflowapp.core.domain.result.AppResult
import com.example.stackoverflowapp.features.users.domain.model.User
import com.example.stackoverflowapp.features.users.domain.repository.UserRepository
import com.example.stackoverflowapp.features.users.remote.StackOverflowApi
import com.example.stackoverflowapp.features.users.remote.mapper.toDomainModels
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import retrofit2.HttpException

class UserRepositoryImpl @Inject constructor(
    private val api: StackOverflowApi,
) : UserRepository {

    override suspend fun getTopUsers(): AppResult<List<User>> = try {
        val response = api.getTopUsers()
        AppResult.Success(response.items.toDomainModels())
    } catch (e: SocketTimeoutException) {
        AppResult.Failure(AppError.Timeout)
    } catch (e: UnknownHostException) {
        AppResult.Failure(AppError.Network)
    } catch (e: IOException) {
        AppResult.Failure(AppError.Network)
    } catch (e: HttpException) {
        when (e.code()) {
            429 -> AppResult.Failure(AppError.RateLimited)
            in 500..599 -> AppResult.Failure(AppError.Server)
            else -> AppResult.Failure(AppError.Http(e.code()))
        }
    } catch (e: Exception) {
        AppResult.Failure(AppError.Unknown)
    }
}
