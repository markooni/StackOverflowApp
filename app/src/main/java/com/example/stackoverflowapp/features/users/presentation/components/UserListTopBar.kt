package com.example.stackoverflowapp.features.users.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import com.example.stackoverflowapp.R
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.stackoverflowapp.core.ui.desingsystem.SoDimens
import com.example.stackoverflowapp.core.ui.desingsystem.SoTypography

@Composable
fun UserListTopBar() {
    Text(
        text = stringResource(R.string.top_users_title),
        style = SoTypography.SoTopBar,
        modifier = Modifier
            .statusBarsPadding()
            .padding(
                horizontal = SoDimens.SoSpacingLg,
                vertical = SoDimens.SoSpacingMd
            )
    )
}