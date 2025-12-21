package com.ilsangtech.ilsang.feature.approval.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.subTitle02

@Composable
internal fun ApprovalItemStatsRow(
    modifier: Modifier = Modifier,
    likeCount: Int,
    commentCount: Int,
    shareCount: Int,
    isLike: Boolean,
    onLikeButtonClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clickable(
                        indication = null,
                        interactionSource = null,
                        onClick = onLikeButtonClick
                    ),
            ) {
                Icon(
                    painter = if (isLike) {
                        painterResource(R.drawable.icon_filled_like)
                    } else {
                        painterResource(R.drawable.icon_like)
                    },
                    tint = if (isLike) primary else gray400,
                    contentDescription = null
                )
            }
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = likeCount.toString(),
                style = subTitle02,
                color = gray400
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.icon_chat),
                tint = gray400,
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = commentCount.toString(),
                style = subTitle02,
                color = gray400
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.size(30.dp),
                painter = painterResource(R.drawable.icon_share),
                tint = gray400,
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = shareCount.toString(),
                style = subTitle02,
                color = gray400
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ApprovalItemStatsRowPreview() {
    ApprovalItemStatsRow(
        likeCount = 10,
        commentCount = 20,
        shareCount = 20,
        isLike = false,
        onLikeButtonClick = {}
    )
}