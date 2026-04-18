package com.example.stackoverflowapp.features.users.data.repository

import com.example.stackoverflowapp.features.users.data.local.FollowDao
import com.example.stackoverflowapp.features.users.data.local.FollowEntity
import com.example.stackoverflowapp.features.users.domain.repository.FollowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FollowRepositoryImpl @Inject constructor(
    private val followDao: FollowDao,
) : FollowRepository {

    override fun observeFollowedIds(): Flow<Set<Int>> =
        followDao.observeAllFollowedIds().map { it.toSet() }

    override suspend fun isFollowed(userId: Int): Boolean =
        followDao.isFollowed(userId)

    override suspend fun follow(userId: Int) {
        followDao.insertFollow(FollowEntity(userId = userId))
    }

    override suspend fun unfollow(userId: Int) {
        followDao.deleteFollow(userId)
    }
}
