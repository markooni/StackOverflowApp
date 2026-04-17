package com.example.stackoverflowapp.features.users.presentation.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.stackoverflowapp.R
import com.example.stackoverflowapp.core.domain.error.AppError

@Composable
fun AppError.toUiText(): String = when (this) {
    AppError.Network -> stringResource(R.string.error_network)
    AppError.Timeout -> stringResource(R.string.error_timeout)
    AppError.RateLimited -> stringResource(R.string.error_rate_limited)
    AppError.Server -> stringResource(R.string.error_server)
    is AppError.Http -> stringResource(R.string.error_http_code, code)
    AppError.Unknown -> stringResource(R.string.error_unexpected)
}
