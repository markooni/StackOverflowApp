package com.example.stackoverflowapp.features.users.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.stackoverflowapp.core.ui.theme.SoOrange
import com.example.stackoverflowapp.core.ui.theme.StackUsersTheme

@Composable
fun LoadingState(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = SoOrange
        )
    }
}

@Preview
@Composable
private fun LoadingStatePreview() {
    StackUsersTheme {
        LoadingState()
    }
}