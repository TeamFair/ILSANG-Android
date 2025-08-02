package com.ilsangtech.ilsang.feature.my.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.drawscope.DrawScope
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
import com.ilsangtech.ilsang.core.model.Title
import com.ilsangtech.ilsang.core.util.XpLevelCalculator
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_bold
import com.ilsangtech.ilsang.designsystem.theme.badge01TextStyle
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.primary500
import com.ilsangtech.ilsang.designsystem.theme.toSp
import com.ilsangtech.ilsang.feature.my.BuildConfig
import com.ilsangtech.ilsang.feature.my.R

@Composable
internal fun UserProfileContent(
    modifier: Modifier = Modifier,
    myInfo: MyInfo,
    navigateToNicknameEdit: () -> Unit,
    onMyTitleClick: () -> Unit
) {
    MyInfoProfileContent(
        modifier = modifier,
        myInfo = myInfo,
        navigateToNicknameEdit = navigateToNicknameEdit,
        onMyTitleClick = onMyTitleClick
    )
}

@Composable
private fun MyInfoProfileContent(
    modifier: Modifier = Modifier,
    myInfo: MyInfo,
    navigateToNicknameEdit: () -> Unit,
    onMyTitleClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier.clickable(
                onClick = navigateToNicknameEdit,
                indication = null,
                interactionSource = null
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileImageWithBadge(
                profileImage = myInfo.profileImage,
                xpPoint = myInfo.xpPoint
            )
            Spacer(Modifier.height(15.5.dp))
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
        myInfo.title?.let { title ->
            MyTitleBadge(
                modifier = Modifier.padding(top = 8.dp),
                title = title,
                onClick = onMyTitleClick
            )
        }
    }
}

@Composable
private fun ProfileImageWithBadge(
    modifier: Modifier = Modifier,
    profileImage: String?,
    xpPoint: Int
) {
    Box(
        modifier = modifier
            .size(115.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.drawBehind {
                drawUserLevelProgress(xpPoint)
            },
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(85.5.dp)
                    .clip(CircleShape),
                model = profileImage?.let { BuildConfig.IMAGE_URL + it },
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.default_user_profile),
                error = painterResource(R.drawable.default_user_profile),
                contentDescription = "프로필 이미지"
            )
        }
        UserLevelBadge(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 0.dp),
            xpPoint = xpPoint
        )
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
            style = userProfileCardLevelTextStyle,
            fontSize = 19.5.dp.toSp(),
            lineHeight = 18.dp.toSp()
        )
    }
}

@Composable
private fun MyTitleBadge(
    modifier: Modifier = Modifier,
    title: Title,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(1000.dp),
        color = Color.White,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(
                vertical = 8.dp,
                horizontal = 10.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(
                    when (title.type) {
                        "STANDARD" -> R.drawable.icon_normal_title
                        "RARE" -> R.drawable.icon_rare_title
                        else -> R.drawable.icon_legend_title
                    }
                ),
                contentDescription = "칭호 아이콘",
                tint = Color.Unspecified
            )
            Text(
                text = title.name,
                style = badge01TextStyle,
                color = gray500
            )
        }
    }
}

private fun DrawScope.drawUserLevelProgress(xpPoint: Int) {
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
        sweepAngle = -(267f * XpLevelCalculator.getLevelProgress(xpPoint)),
        useCenter = false,
        style = Stroke(
            width = 9.dp.toPx(),
            cap = StrokeCap.Round,
        )
    )
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

@Preview(showBackground = true, heightDp = 400)
@Composable
fun MyInfoProfileContentPreview() {
    MyInfoProfileContent(
        myInfo = MyInfo(
            nickname = "김일상123닉네임 길이가 길어",
            email = "",
            profileImage = null,
            completeChallengeCount = 0,
            couponCount = 0,
            xpPoint = 52,
            status = "",
            title = Title(
                id = "",
                name = "세상을 움직이는 자",
                type = "STANDARD",
                condition = null,
                createdAt = ""
            ),
        ),
        navigateToNicknameEdit = {},
        onMyTitleClick = {}
    )
}