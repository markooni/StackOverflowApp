package com.example.stackoverflowapp.features.users.remote

object StackOverflowApiConstants {
    const val USERS_ENDPOINT = "2.2/users"

    object Query {
        const val PAGE = "page"
        const val PAGE_SIZE = "pagesize"
        const val ORDER = "order"
        const val SORT = "sort"
        const val SITE = "site"
    }

    object Default {
        const val PAGE = 1
        const val PAGE_SIZE = 20
        const val ORDER = "desc"
        const val SORT = "reputation"
        const val SITE = "stackoverflow"
    }
}