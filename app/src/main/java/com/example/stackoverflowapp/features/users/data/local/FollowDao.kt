package com.example.stackoverflowapp.features.users.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FollowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFollow(followEntity: FollowEntity)

    @Query(
        "DELETE FROM ${FollowedUsersTableConstants.TABLE_NAME} " +
            "WHERE ${FollowedUsersTableConstants.COLUMN_USER_ID} = :userId"
    )
    suspend fun deleteFollow(userId: Int)

    @Query(
        "SELECT EXISTS(SELECT 1 FROM ${FollowedUsersTableConstants.TABLE_NAME} " +
            "WHERE ${FollowedUsersTableConstants.COLUMN_USER_ID} = :userId)"
    )
    suspend fun isFollowed(userId: Int): Boolean

    @Query(
        "SELECT ${FollowedUsersTableConstants.COLUMN_USER_ID} " +
            "FROM ${FollowedUsersTableConstants.TABLE_NAME}"
    )
    fun observeAllFollowedIds(): Flow<List<Int>>
}
