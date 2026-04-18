package com.example.stackoverflowapp.features.users.di

import com.example.stackoverflowapp.features.users.data.remote.StackOverflowApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserApiModule {

    @Provides
    @Singleton
    fun provideStackOverflowApi(retrofit: Retrofit): StackOverflowApi =
        retrofit.create(StackOverflowApi::class.java)
}
