package com.example.stackoverflowapp.features.users.domain.usecase

import com.example.stackoverflowapp.core.domain.usecase.BaseUseCase
import com.example.stackoverflowapp.core.domain.result.AppResult
import com.example.stackoverflowapp.features.users.domain.model.User
import com.example.stackoverflowapp.features.users.domain.repository.UserRepository
import javax.inject.Inject

class GetTopUsersUseCase @Inject constructor(
    private val repository: UserRepository
) : BaseUseCase<Unit, AppResult<List<User>>>() {

    override suspend fun execute(params: Unit): AppResult<List<User>> {
        return repository.getTopUsers()
    }

    suspend operator fun invoke(): AppResult<List<User>> {
        return super.invoke(Unit)
    }
}
