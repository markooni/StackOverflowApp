package com.example.stackoverflowapp.core.domain.error

sealed interface AppError {
    data object Network : AppError
    data object Timeout : AppError
    data object RateLimited : AppError
    data object Server : AppError
    data class Http(val code: Int) : AppError
    data object Unknown : AppError
}
