package com.example.stackoverflowapp.core.ui.desingsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.example.stackoverflowapp.R
import com.example.stackoverflowapp.core.ui.desingsystem.SoDimens
import com.example.stackoverflowapp.core.ui.desingsystem.SoTypography
import com.example.stackoverflowapp.core.ui.theme.SoOrange
import com.example.stackoverflowapp.core.ui.theme.StackUsersTheme

@Composable
fun SoFollowButton(
    isFollowed: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
    userName: String = ""
) {
    if (isFollowed) {
        val unfollowDescription = stringResource(R.string.unfollow_button_description, userName)
        OutlinedButton(
            onClick = onToggle,
            modifier = modifier.semantics { contentDescription = unfollowDescription },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = SoOrange
            )
        ) {
            FollowButtonContent(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(SoDimens.SoIconSizeSm)
                    )
                },
                label = stringResource(R.string.following)
            )
        }
    } else {
        val followDescription = stringResource(R.string.follow_button_description, userName)
        Button(
            onClick = onToggle,
            modifier = modifier.semantics { contentDescription = followDescription },
            colors = ButtonDefaults.buttonColors(
                containerColor = SoOrange
            )
        ) {
            FollowButtonContent(
                icon = {
                    Icon(
                        imageVector = Icons.Default.PersonAdd,
                        contentDescription = null,
                        modifier = Modifier.size(SoDimens.SoIconSizeSm)
                    )
                },
                label = stringResource(R.string.follow)
            )
        }
    }
}

@Composable
private fun FollowButtonContent(
    icon: @Composable () -> Unit,
    label: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(SoDimens.SoSpacingXs),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon()
        Text(
            text = label,
            style = SoTypography.SoFollowLabel
        )
    }
}

@Preview
@Composable
private fun FollowButtonUnfollowedPreview() {
    StackUsersTheme {
        SoFollowButton(isFollowed = false, onToggle = {}, userName = "Arnel Maric")
    }
}
