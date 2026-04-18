package com.example.stackoverflowapp.core.ui.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle

object SoTypography {

    val SoUserName: TextStyle
        @Composable get() = MaterialTheme.typography.titleMedium

    val SoReputation: TextStyle
        @Composable get() = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

    val SoError: TextStyle
        @Composable get() = MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

    val SoTopBar: TextStyle
        @Composable get() = MaterialTheme.typography.titleLarge.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

    val SoFollowLabel: TextStyle
        @Composable get() = MaterialTheme.typography.labelMedium
}
