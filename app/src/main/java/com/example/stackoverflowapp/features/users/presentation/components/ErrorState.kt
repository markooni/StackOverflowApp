package com.example.stackoverflowapp.features.users.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.stackoverflowapp.R
import com.example.stackoverflowapp.core.ui.designsystem.SoDimens
import com.example.stackoverflowapp.core.ui.designsystem.SoTypography
import com.example.stackoverflowapp.core.ui.theme.SoGray
import com.example.stackoverflowapp.core.ui.theme.StackOverflowTheme

@Composable
fun ErrorState(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = SoDimens.SoSpacingLg,
            alignment = Alignment.CenterVertically
        )
    ) {
        Icon(
            imageVector = Icons.Default.CloudOff,
            contentDescription = stringResource(R.string.error_icon_description),
            modifier = Modifier.size(SoDimens.SoSpacing4xl),
            tint = SoGray
        )

        Text(
            text = message,
            style = SoTypography.SoError,
            color = SoGray,
            textAlign = TextAlign.Center
        )

        Button(
            onClick = onRetry,
        ) {
            Text(text = stringResource(R.string.retry))
        }
    }
}

@Preview
@Composable
private fun ErrorStatePreview() {
    StackOverflowTheme {
        ErrorState(
            message = "Unable to connect. Please check your internet connection.",
            onRetry = {}
        )
    }
}