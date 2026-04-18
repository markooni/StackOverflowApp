package com.example.stackoverflowapp.features.users.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = FollowedUsersTableConstants.TABLE_NAME)
data class FollowEntity(
    @PrimaryKey
    @ColumnInfo(name = FollowedUsersTableConstants.COLUMN_USER_ID)
    val userId: Int
)
