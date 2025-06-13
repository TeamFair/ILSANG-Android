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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.model.MyInfo
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_bold
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.feature.home.BuildConfig
import com.ilsangtech.ilsang.feature.home.R
import com.ilsangtech.ilsang.core.util.XpLevelCalculator

@Composable
fun UserProfileContent(
    myInfo: MyInfo,
    navigateToNicknameEdit: () -> Unit
) {
    MyInfoProfileContent(
        myInfo = myInfo,
        navigateToNicknameEdit = navigateToNicknameEdit
    )
}

@Composable
fun MyInfoProfileContent(
    myInfo: MyInfo,
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
                modifier = Modifier
                    .size(57.dp)
                    .clip(CircleShape),
                model = myInfo.profileImage?.let { BuildConfig.IMAGE_URL + it },
                contentScale = ContentScale.Crop,
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
                text = myInfo.nickname!!,
                style = profileNicknameTextStyle,
            )
            Spacer(Modifier.height(4.dp))
            UserLevelBadge(xpPoint = myInfo.xpPoint)
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

@Preview
@Composable
fun UserProfileCardPreview() {
    UserProfileContent(
        MyInfo(
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
        MyInfo(
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