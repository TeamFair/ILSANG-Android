package com.ilsangtech.ilsang.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.designsystem.theme.caption02
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.tapBoldTextStyle
import com.ilsangtech.ilsang.feature.home.BuildConfig
import com.ilsangtech.ilsang.feature.home.R

@Composable
internal fun HomeTabHeader(
    modifier: Modifier = Modifier,
    profileImageId: String?,
    metroName: String?,
    areaName: String?,
    onProfileClick: () -> Unit,
    onMyZoneClick: () -> Unit,
    onIsZoneClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .statusBarsPadding()
            .padding(top = 8.dp, bottom = 16.dp)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        LogoAndProfileImage(
            profileImageId = profileImageId,
            onProfileClick = onProfileClick
        )
        MyZoneSelectionRow(
            metroName = metroName,
            areaName = areaName,
            onMyZoneClick = onMyZoneClick,
            onIsZoneClick = onIsZoneClick
        )
    }
}

@Composable
private fun LogoAndProfileImage(
    profileImageId: String?,
    onProfileClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.home_ilsang_logo),
            tint = Color.Unspecified,
            contentDescription = "일상 로고"
        )
        AsyncImage(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .clickable(
                    onClick = onProfileClick,
                    indication = null,
                    interactionSource = null
                ),
            model = BuildConfig.IMAGE_URL + profileImageId,
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.default_user_profile),
            contentDescription = null
        )
    }
}

@Composable
private fun MyZoneSelectionRow(
    metroName: String?,
    areaName: String?,
    onMyZoneClick: () -> Unit,
    onIsZoneClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.clickable(
                onClick = onMyZoneClick,
                indication = null,
                interactionSource = null
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                modifier = Modifier
                    .size(30.dp)
                    .padding(
                        top = 4.dp,
                        bottom = 4.53.dp,
                        start = 7.dp,
                        end = 6.81.dp
                    ),
                painter = painterResource(com.ilsangtech.ilsang.designsystem.R.drawable.icon_metro),
                tint = Color.Unspecified,
                contentDescription = "광역 지역"
            )
            Text(
                text = metroName ?: "내 지역을 선택하세요",
                style = tapBoldTextStyle,
                color = gray500
            )
            Icon(
                painter = painterResource(com.ilsangtech.ilsang.designsystem.R.drawable.icon_under),
                tint = Color.Unspecified,
                contentDescription = null
            )
        }
        Row(
            modifier = Modifier.clickable(
                onClick = onIsZoneClick,
                indication = null,
                interactionSource = null
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "내 일상존:",
                style = caption02,
                color = gray400
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(300.dp))
                    .background(primary)
            ) {
                Row(
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = areaName ?: "선택하기",
                        style = caption02,
                        color = Color.White
                    )
                    if (areaName == null) {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            painter = painterResource(
                                com.ilsangtech.ilsang.designsystem.R.drawable.icon_right
                            ),
                            tint = Color.White,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeTabHeaderPreview() {
    HomeTabHeader(
        profileImageId = "your_image_id",
        metroName = "성남",
        areaName = null,
        onProfileClick = {},
        onMyZoneClick = {},
        onIsZoneClick = {}
    )
}
