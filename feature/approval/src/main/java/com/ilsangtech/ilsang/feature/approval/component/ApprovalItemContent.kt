package com.ilsangtech.ilsang.feature.approval.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.badge01TextStyle
import com.ilsangtech.ilsang.designsystem.theme.gray200
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.heading02
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.title01
import com.ilsangtech.ilsang.feature.approval.BuildConfig

@Composable
internal fun ApprovalItemContent(
    modifier: Modifier = Modifier,
    title: String,
    challengeImage: String,
    createdAt: String,
    areaName: String,
    likeCount: Int,
    hateCount: Int
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = title,
            style = title01,
            overflow = TextOverflow.Ellipsis
        )
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(5 / 4f)
                .clip(RoundedCornerShape(12.dp)),
            model = BuildConfig.IMAGE_URL + challengeImage,
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = createdAt,
                style = TextStyle(
                    fontFamily = pretendardFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    lineHeight = 12.sp
                ),
                color = gray500
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .size(18.dp)
                        .padding(
                            top = 2.4.dp, bottom = 2.72.dp,
                            start = 4.2.dp, end = 4.09.dp
                        ),
                    painter = painterResource(R.drawable.icon_metro),
                    tint = Color.Unspecified,
                    contentDescription = "지역"
                )
                Text(
                    text = areaName,
                    style = badge01TextStyle,
                    color = gray500
                )
            }
        }
        Row(modifier = Modifier.height(24.dp)) {
            Box(modifier = Modifier.size(24.dp)) {
                Icon(
                    modifier = Modifier.align(Alignment.TopStart),
                    painter = painterResource(R.drawable.thumbs_up),
                    tint = gray200,
                    contentDescription = null
                )
            }
            Spacer(Modifier.width(4.dp))
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = likeCount.toString(),
                style = heading02,
                color = gray200
            )
            Spacer(Modifier.width(16.dp))
            Box(modifier = Modifier.size(24.dp)) {
                Icon(
                    modifier = Modifier.align(Alignment.BottomStart),
                    painter = painterResource(R.drawable.thumbs_down),
                    tint = gray200,
                    contentDescription = null
                )
            }
            Spacer(Modifier.width(4.dp))
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = hateCount.toString(),
                style = heading02,
                color = gray200
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ApprovalItemContentPreview() {
    ApprovalItemContent(
        modifier = Modifier.padding(20.dp),
        title = "우리 동네 사진 챌린지",
        challengeImage = "https://picsum.photos/200/300",
        createdAt = "2025.04.12 12:00",
        areaName = "서울",
        likeCount = 100,
        hateCount = 10
    )
}