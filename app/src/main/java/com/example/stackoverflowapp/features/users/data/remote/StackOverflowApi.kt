package com.example.stackoverflowapp.features.users.data.remote

import com.example.stackoverflowapp.features.users.data.remote.dto.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface StackOverflowApi {
    @GET(StackOverflowApiConstants.USERS_ENDPOINT)
    suspend fun getTopUsers(
        @Query(StackOverflowApiConstants.Query.PAGE)
        page: Int = StackOverflowApiConstants.Default.PAGE,

        @Query(StackOverflowApiConstants.Query.PAGE_SIZE)
        pageSize: Int = StackOverflowApiConstants.Default.PAGE_SIZE,

        @Query(StackOverflowApiConstants.Query.ORDER)
        order: String = StackOverflowApiConstants.Default.ORDER,

        @Query(StackOverflowApiConstants.Query.SORT)
        sort: String = StackOverflowApiConstants.Default.SORT,

        @Query(StackOverflowApiConstants.Query.SITE)
        site: String = StackOverflowApiConstants.Default.SITE
    ): UsersResponse
}
