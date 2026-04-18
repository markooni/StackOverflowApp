package com.example.stackoverflowapp.features.users.domain.repository

import kotlinx.coroutines.flow.Flow

interface FollowRepository {
    fun observeFollowedIds(): Flow<Set<Int>>
    suspend fun isFollowed(userId: Int): Boolean
    suspend fun follow(userId: Int)
    suspend fun unfollow(userId: Int)
}
