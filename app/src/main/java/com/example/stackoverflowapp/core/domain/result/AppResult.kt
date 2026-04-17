package com.example.stackoverflowapp.core.domain.result

import com.example.stackoverflowapp.core.domain.error.AppError

sealed interface AppResult<out T> {
    data class Success<T>(val data: T) : AppResult<T>
    data class Failure(val error: AppError) : AppResult<Nothing>
}
