package com.example.stackoverflowapp.features.users.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FollowEntity::class],
    version = 1, exportSchema = false
)

abstract class SoDatabase : RoomDatabase() {
    abstract fun followDao(): FollowDao
}