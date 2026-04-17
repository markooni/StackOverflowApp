package com.example.stackoverflowapp.features.users.domain.repository

import com.example.stackoverflowapp.core.domain.result.AppResult
import com.example.stackoverflowapp.features.users.domain.model.User

interface UserRepository {
    suspend fun getTopUsers(): AppResult<List<User>>
}
