package com.example.stackoverflowapp.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val AppColorScheme = lightColorScheme(
    primary = SoOrange,
    onPrimary = SoSurface,
    secondary = SoGray,
    onSecondary = SoSurface,
    background = SoBackground,
    onBackground = SoTextPrimary,
    surface = SoSurface,
    onSurface = SoTextPrimary,
    surfaceVariant = SoBackground,
    onSurfaceVariant = SoTextSecondary,
    error = SoRed,
    onError = SoSurface
)

@Composable
fun StackOverflowTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = Typography,
        content = content
    )
}
