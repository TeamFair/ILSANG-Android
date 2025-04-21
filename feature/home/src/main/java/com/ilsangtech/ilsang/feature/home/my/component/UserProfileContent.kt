package com.ilsangtech.ilsang.feature.home.my.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.model.UserInfo
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_bold
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_regular
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray200
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.feature.home.BuildConfig
import com.ilsangtech.ilsang.feature.home.R
import com.ilsangtech.ilsang.feature.home.my.util.XpLevelCalculator

@Composable
fun UserProfileContent(
    userInfo: UserInfo,
    navigateToNicknameEdit: () -> Unit
) {
    MyInfoProfileContent(
        userInfo = userInfo,
        navigateToNicknameEdit = navigateToNicknameEdit
    )
}

@Composable
fun DefaultProfileCardContent(userInfo: UserInfo) {
    Row(
        modifier = Modifier.padding(18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.padding(top = 13.dp)) {
            AsyncImage(
                modifier = Modifier.size(57.dp),
                model = userInfo.profileImage?.let { BuildConfig.IMAGE_URL + it },
                error = painterResource(R.drawable.default_user_profile),
                placeholder = painterResource(R.drawable.default_user_profile),
                contentDescription = "프로필 이미지"
            )
            Box(
                modifier = Modifier
                    .width(57.dp)
                    .offset(y = (-13).dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .border(
                        width = 1.dp,
                        color = primary,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(
                        vertical = 4.dp,
                        horizontal = 6.dp
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Lv. " + XpLevelCalculator.getCurrentLevel(userInfo.xpPoint),
                    style = userProfileCardLevelTextStyle
                )
            }
        }
        Spacer(Modifier.width(19.dp))
        Column {
            NicknameWithUnderline(userInfo.nickname!!)
            Spacer(Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .height(8.dp)
                        .weight(1f),
                    color = primary,
                    trackColor = Color(0xFFEDEBEB),
                    progress = {
                        0.3f
                    },
                    gapSize = (-5).dp,
                    drawStopIndicator = {}
                )
                Spacer(Modifier.width(7.dp))
                Text(
                    text = "${XpLevelCalculator.getXpPointInCurrentLevel(userInfo.xpPoint)}XP",
                    style = userProfileCardXpTextStyle,
                    color = primary
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = "다음 레벨까지 "
                        + XpLevelCalculator.getRequiredXpPointForNextLevel(userInfo.xpPoint)
                        + "XP 남았어요!",
                style = userProfileCardLevelDescriptionTextStyle
            )
        }
    }
}


@Composable
fun MyInfoProfileContent(
    userInfo: UserInfo,
    navigateToNicknameEdit: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = null,
                onClick = navigateToNicknameEdit
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.padding(bottom = 4.dp)) {
            AsyncImage(
                modifier = Modifier.size(57.dp),
                model = userInfo.profileImage?.let { BuildConfig.IMAGE_URL + it },
                placeholder = painterResource(R.drawable.default_user_profile),
                error = painterResource(R.drawable.default_user_profile),
                contentDescription = "프로필 이미지"
            )
            Box(
                modifier = Modifier
                    .offset(y = 4.dp)
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(Color.Black)
                    .align(Alignment.BottomEnd),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.edit),
                    tint = gray100,
                    contentDescription = null
                )
            }
        }
        Spacer(Modifier.width(12.dp))
        Column {
            Text(
                text = userInfo.nickname!!,
                style = profileNicknameTextStyle,
            )
            Spacer(Modifier.height(4.dp))
            UserLevelBadge(xpPoint = userInfo.xpPoint)
        }
    }
}

@Composable
fun NicknameWithUnderline(nickname: String) {
    SubcomposeLayout { constraints ->

        val nicknameMeasurables = subcompose("nickname") {
            Text(
                text = nickname,
                style = profileNicknameTextStyle
            )
        }
        val nicknamePlaceables = nicknameMeasurables.map { it.measure(constraints) }
        val nicknameWidth = nicknamePlaceables.maxOf { it.width }
        val nicknameHeight = nicknamePlaceables.maxOf { it.height }

        val dividerMeasurables = subcompose("divider") {
            HorizontalDivider(
                modifier = Modifier.width(nicknameWidth.toDp()),
                color = gray200
            )
        }
        val dividerPlaceables = dividerMeasurables.map { it.measure(constraints) }
        val dividerHeight = dividerPlaceables.maxOf { it.height }

        val totalHeight = nicknameHeight + dividerHeight

        layout(nicknameWidth, totalHeight) {
            var y = 0
            nicknamePlaceables.forEach {
                it.placeRelative(0, y)
                y += it.height
            }
            dividerPlaceables.forEach {
                it.placeRelative(0, y + 1.dp.roundToPx())
            }
        }
    }
}

@Composable
fun UserLevelBadge(
    modifier: Modifier = Modifier,
    xpPoint: Int
) {
    Box(
        modifier = modifier
            .width(57.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .border(
                width = 1.dp,
                color = primary,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(
                vertical = 4.dp,
                horizontal = 6.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Lv. " + XpLevelCalculator.getCurrentLevel(xpPoint),
            style = userProfileCardLevelTextStyle
        )
    }
}

private val userProfileCardLevelTextStyle = TextStyle(
    fontFamily = FontFamily(Font(pretendard_bold)),
    fontSize = 13.sp,
    lineHeight = 12.sp,
    color = primary
)

private val profileNicknameTextStyle = TextStyle(
    fontFamily = FontFamily(Font(pretendard_bold)),
    fontSize = 16.sp,
    lineHeight = 18.sp,
    color = gray500
)

private val userProfileCardXpTextStyle = TextStyle(
    fontFamily = FontFamily(Font(pretendard_bold)),
    fontSize = 13.sp,
    lineHeight = 12.sp,
    color = primary
)

private val userProfileCardLevelDescriptionTextStyle = TextStyle(
    fontFamily = FontFamily(Font(pretendard_regular)),
    fontSize = 13.sp,
    lineHeight = 12.sp,
    color = gray500
)

@Preview
@Composable
fun UserProfileCardPreview() {
    UserProfileContent(
        UserInfo(
            accessToken = "",
            authorization = "",
            nickname = "김일상1234",
            email = "",
            profileImage = null,
            completeChallengeCount = 0,
            couponCount = 0,
            xpPoint = 160,
            status = ""
        )
    ) {}
}

@Preview(showBackground = true)
@Composable
fun MyInfoProfileContentPreview() {
    MyInfoProfileContent(
        UserInfo(
            accessToken = "",
            authorization = "",
            nickname = "김일상1234",
            email = "",
            profileImage = null,
            completeChallengeCount = 0,
            couponCount = 0,
            xpPoint = 160,
            status = ""
        )
    ) {}
}