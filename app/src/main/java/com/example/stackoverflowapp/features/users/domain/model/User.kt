package com.example.stackoverflowapp.features.users.domain.model

data class User(
    val userId: Int,
    val displayName: String,
    val profileImage: String?,
    val reputation: Int,
    val isFollowed: Boolean = false
)