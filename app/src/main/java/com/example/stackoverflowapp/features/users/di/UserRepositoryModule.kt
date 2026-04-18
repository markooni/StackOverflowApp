package com.example.stackoverflowapp.features.users.di

import com.example.stackoverflowapp.features.users.data.repository.FollowRepositoryImpl
import com.example.stackoverflowapp.features.users.data.repository.UserRepositoryImpl
import com.example.stackoverflowapp.features.users.domain.repository.FollowRepository
import com.example.stackoverflowapp.features.users.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindFollowRepository(impl: FollowRepositoryImpl): FollowRepository
}
