package com.example.stackoverflowapp.core.ui.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.stackoverflowapp.R
import com.example.stackoverflowapp.core.ui.designsystem.SoDimens
import com.example.stackoverflowapp.core.ui.designsystem.SoTypography
import com.example.stackoverflowapp.core.ui.formatters.formatReputation
import com.example.stackoverflowapp.core.ui.theme.StackOverflowTheme

@Composable
fun UserCard(
    avatarUrl: String?,
    displayName: String,
    reputation: Int,
    isFollowed: Boolean,
    onFollowToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = SoDimens.SoCardElevation),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SoDimens.SoSpacingLg),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(SoDimens.SoSpacingMd),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(avatarUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(R.string.avatar_description, displayName),
                    modifier = Modifier
                        .size(SoDimens.SoAvatarSize)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = displayName,
                        style = SoTypography.SoUserName,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = stringResource(
                            R.string.reputation_format,
                            formatReputation(reputation)
                        ),
                        style = SoTypography.SoReputation
                    )
                }
            }

            SoFollowButton(
                modifier = Modifier.padding(start = SoDimens.SoSpacingSm),
                isFollowed = isFollowed,
                onToggle = onFollowToggle,
                userName = displayName
            )
        }
    }
}

@Preview
@Composable
private fun UserCardFollowedPreview() {
    StackOverflowTheme {
        UserCard(
            avatarUrl = null,
            displayName = "Arnel Maric",
            reputation = 1_200_000,
            isFollowed = true,
            onFollowToggle = {}
        )
    }
}
