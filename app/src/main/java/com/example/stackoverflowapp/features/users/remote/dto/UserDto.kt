package com.example.stackoverflowapp.features.users.remote.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName(UserDtoConstants.USER_ID) val userId: Int,
    @SerializedName(UserDtoConstants.DISPLAY_NAME) val displayName: String,
    @SerializedName(UserDtoConstants.PROFILE_IMAGE) val profileImage: String?,
    @SerializedName(UserDtoConstants.REPUTATION) val reputation: Int
)
