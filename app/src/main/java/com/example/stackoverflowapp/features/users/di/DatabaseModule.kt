package com.example.stackoverflowapp.features.users.di

import android.content.Context
import androidx.room.Room
import com.example.stackoverflowapp.features.users.data.local.FollowDao
import com.example.stackoverflowapp.features.users.data.local.SoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideSoDatabase(@ApplicationContext context: Context): SoDatabase {
        return Room.databaseBuilder(
            context,
            SoDatabase::class.java,
            "stack_users_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFollowDao(database: SoDatabase): FollowDao {
        return database.followDao()
    }
}