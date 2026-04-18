package com.example.stackoverflowapp.features.users.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName(UserDtoConstants.ITEMS) val items: List<UserDto>
)
