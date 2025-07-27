package com.ilsangtech.ilsang.feature.my.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.model.MyInfo
import com.ilsangtech.ilsang.core.util.XpLevelCalculator
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_bold
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.feature.my.BuildConfig
import com.ilsangtech.ilsang.feature.my.R

@Composable
internal fun UserProfileContent(
    modifier: Modifier = Modifier,
    myInfo: MyInfo,
    navigateToNicknameEdit: () -> Unit
) {
    MyInfoProfileContent(
        modifier = modifier,
        myInfo = myInfo,
        navigateToNicknameEdit = navigateToNicknameEdit
    )
}

@Composable
private fun MyInfoProfileContent(
    modifier: Modifier = Modifier,
    myInfo: MyInfo,
    navigateToNicknameEdit: () -> Unit
) {
    Column(
        modifier = modifier.clickable(
            onClick = navigateToNicknameEdit,
            indication = null,
            interactionSource = null
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .drawBehind {
                    drawCircle(
                        radius = ((115 - 9).dp.toPx() / 2),
                        color = gray100,
                        style = Stroke(width = 9.dp.toPx())
                    )
                    drawArc(
                        size = Size(
                            width = (115 - 9).dp.toPx(),
                            height = (115 - 9).dp.toPx()
                        ),
                        topLeft = Offset(
                            -this.center.x / 4,
                            -this.center.y / 4
                        ),
                        color = primary500,
                        startAngle = 43f,
                        sweepAngle = -(267f * XpLevelCalculator.getLevelProgress(myInfo.xpPoint)),
                        useCenter = false,
                        style = Stroke(
                            width = 9.dp.toPx(),
                            cap = StrokeCap.Round,
                        )
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(85.5.dp)
                    .clip(CircleShape),
                model = myInfo.profileImage?.let { BuildConfig.IMAGE_URL + it },
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.default_user_profile),
                error = painterResource(R.drawable.default_user_profile),
                contentDescription = "프로필 이미지"
            )
        }
        UserLevelBadge(
            modifier = Modifier.offset(y = (-15).dp),
            xpPoint = myInfo.xpPoint
        )
        Spacer(Modifier.width(15.5.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = myInfo.nickname.orEmpty(),
                style = profileNicknameTextStyle
            )
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(Color.Black)
            ) {
                Icon(
                    modifier = Modifier.align(Alignment.Center),
                    painter = painterResource(R.drawable.edit),
                    tint = gray100,
                    contentDescription = null
                )
            }

        }
    }
}

@Composable
private fun UserLevelBadge(
    modifier: Modifier = Modifier,
    xpPoint: Int
) {
    Box(
        modifier = modifier
            .width(85.5.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(Color.White)
            .border(
                width = 1.dp,
                color = primary,
                shape = RoundedCornerShape(30.dp)
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
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 19.5.sp,
    lineHeight = 18.sp,
    color = primary
)

private val profileNicknameTextStyle = TextStyle(
    fontFamily = FontFamily(Font(pretendard_bold)),
    fontSize = 16.sp,
    lineHeight = 18.sp,
    color = gray500
)

@Preview(showBackground = true)
@Composable
fun MyInfoProfileContentPreview() {
    MyInfoProfileContent(
        myInfo = MyInfo(
            nickname = "김일상123닉네임 길이가 길어",
            email = "",
            profileImage = null,
            completeChallengeCount = 0,
            couponCount = 0,
            xpPoint = 160,
            status = "",
            title = null,
        )
    ) {}
}