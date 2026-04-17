package com.example.stackoverflowapp.features.users.remote.mapper


import com.example.stackoverflowapp.features.users.domain.model.User
import com.example.stackoverflowapp.features.users.remote.dto.UserDto

fun UserDto.toDomainModel(): User {
    return User(
        userId = userId,
        displayName = displayName,
        profileImage = profileImage,
        reputation = reputation
    )
}

fun List<UserDto>.toDomainModels(): List<User> {
    return map { it.toDomainModel() }
}
