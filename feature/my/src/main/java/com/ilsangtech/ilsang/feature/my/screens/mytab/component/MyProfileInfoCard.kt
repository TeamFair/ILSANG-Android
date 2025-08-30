package com.ilsangtech.ilsang.feature.my.screens.mytab.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.model.title.Title
import com.ilsangtech.ilsang.core.model.title.TitleGrade
import com.ilsangtech.ilsang.core.model.title.TitleType
import com.ilsangtech.ilsang.core.ui.title.TitleGradeIcon
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.badge01TextStyle
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.primary500
import com.ilsangtech.ilsang.designsystem.theme.tapRegularTextStyle
import com.ilsangtech.ilsang.designsystem.theme.toSp
import com.ilsangtech.ilsang.feature.my.BuildConfig
import com.ilsangtech.ilsang.feature.my.screens.mytab.model.MyProfileInfoUiModel

@Composable
internal fun MyProfileInfoCard(
    modifier: Modifier = Modifier,
    myProfileInfo: MyProfileInfoUiModel,
    onProfileEditButtonClick: () -> Unit,
    onTitleClick: () -> Unit,
    onMissionHistoryButtonClick: () -> Unit,
    onFavoriteQuestButtonClick: () -> Unit,
    onCouponButtonClick: () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileImageWithLevel(
                profileImageId = myProfileInfo.profileImageId,
                progress = myProfileInfo.levelProgress,
                level = myProfileInfo.level
            )
            MyNicknameInfoRow(
                modifier = Modifier.padding(top = 16.dp),
                nickname = myProfileInfo.nickname,
                onNicknameEditButtonClick = onProfileEditButtonClick
            )
            myProfileInfo.title?.let {
                MyTitleBadge(
                    modifier = Modifier.padding(top = 8.dp),
                    title = myProfileInfo.title,
                    onTitleClick = onTitleClick
                )
            }
            MyProfileInfoBottomItemsRow(
                onMissionHistoryButtonClick = onMissionHistoryButtonClick,
                onFavoriteQuestButtonClick = onFavoriteQuestButtonClick,
                onCouponButtonClick = onCouponButtonClick
            )
        }
    }
}

@Composable
private fun ProfileImageWithLevel(
    modifier: Modifier = Modifier,
    profileImageId: String?,
    progress: Float,
    level: Int
) {
    Box(
        modifier = modifier.size(115.5.dp),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            modifier = Modifier
                .drawBehind {
                    drawUserLevelProgress(progress)
                }
                .size(85.5.dp)
                .clip(CircleShape),
            model = BuildConfig.IMAGE_URL + profileImageId,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.default_user_profile),
            error = painterResource(R.drawable.default_user_profile),
            contentDescription = "프로필 이미지"
        )

        Box(
            modifier = Modifier
                .padding(top = 15.dp)
                .align(Alignment.BottomCenter)
                .width(85.5.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(Color.White)
                .border(
                    width = 1.5.dp,
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
                text = "Lv. $level",
                style = TextStyle(
                    fontFamily = pretendardFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 19.5.dp.toSp(),
                    lineHeight = 18.dp.toSp()
                ),
                color = primary
            )
        }
    }
}

@Composable
private fun MyNicknameInfoRow(
    modifier: Modifier = Modifier,
    nickname: String,
    onNicknameEditButtonClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = nickname,
            style = TextStyle(
                fontFamily = pretendardFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                lineHeight = 18.sp
            ),
            color = gray500
        )
        IconButton(
            modifier = Modifier.size(18.dp),
            colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Black),
            onClick = onNicknameEditButtonClick
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_nickname_edit),
                tint = Color.Unspecified,
                contentDescription = null
            )
        }
    }
}

@Composable
fun MyTitleBadge(
    modifier: Modifier = Modifier,
    title: Title,
    onTitleClick: () -> Unit
) {
    Surface(
        modifier = modifier,
        border = BorderStroke(width = 1.dp, color = gray100),
        shape = CircleShape,
        color = Color.White,
        onClick = onTitleClick
    ) {
        Row(
            modifier = Modifier.padding(
                vertical = 8.dp,
                horizontal = 10.dp
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            TitleGradeIcon(
                modifier = Modifier.size(20.dp),
                titleGrade = title.grade
            )
            Text(
                text = title.name,
                style = badge01TextStyle,
                color = gray500
            )
        }
    }
}

@Composable
private fun MyProfileInfoBottomItem(
    modifier: Modifier = Modifier,
    title: String,
    icon: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier,
        color = Color.Transparent,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier.padding(top = 14.dp, bottom = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                icon()
                Text(
                    text = title,
                    style = tapRegularTextStyle,
                    color = gray500
                )
            }
        }
    }
}

@Composable
private fun MyProfileInfoBottomItemsRow(
    modifier: Modifier = Modifier,
    onMissionHistoryButtonClick: () -> Unit,
    onFavoriteQuestButtonClick: () -> Unit,
    onCouponButtonClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(top = 21.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MyProfileInfoBottomItem(
            modifier = Modifier.weight(1f),
            title = "수행한 퀘스트",
            icon = {
                Icon(
                    modifier = Modifier
                        .size(36.dp)
                        .padding(8.dp),
                    painter = painterResource(R.drawable.selected_quest),
                    tint = Color.Unspecified,
                    contentDescription = null
                )
            },
            onClick = onMissionHistoryButtonClick
        )
        VerticalDivider(
            modifier = Modifier.height(48.5.dp),
            color = gray100
        )
        MyProfileInfoBottomItem(
            modifier = Modifier.weight(1f),
            title = "즐겨찾기 퀘스트",
            icon = {
                Icon(
                    modifier = Modifier
                        .size(36.dp),
                    painter = painterResource(R.drawable.icon_my_favorite_quest),
                    tint = Color.Unspecified,
                    contentDescription = null
                )
            },
            onClick = onFavoriteQuestButtonClick
        )
        VerticalDivider(
            modifier = Modifier.height(48.5.dp),
            color = gray100
        )
        MyProfileInfoBottomItem(
            modifier = Modifier.weight(1f),
            title = "쿠폰",
            icon = {
                Icon(
                    modifier = Modifier.size(36.dp),
                    painter = painterResource(R.drawable.icon_coupon),
                    tint = Color.Unspecified,
                    contentDescription = null
                )
            },
            onClick = onCouponButtonClick
        )
    }
}

private fun DrawScope.drawUserLevelProgress(progress: Float) {
    drawCircle(
        radius = ((115.5 - 9).dp.toPx() / 2),
        color = gray100,
        style = Stroke(width = 9.dp.toPx())
    )
    drawArc(
        size = Size(
            width = (115.5 - 9).dp.toPx(),
            height = (115.5 - 9).dp.toPx()
        ),
        topLeft = Offset(
            -this.center.x / 4,
            -this.center.y / 4
        ),
        color = primary500,
        startAngle = 267f,
        sweepAngle = -(267f * progress),
        useCenter = false,
        style = Stroke(
            width = 9.dp.toPx(),
            cap = StrokeCap.Round,
        )
    )
}

@Preview
@Composable
private fun MyProfileInfoCardPreview() {
    val myProfileInfo = MyProfileInfoUiModel(
        nickname = "김일상123닉네임 길이가 길어",
        profileImageId = "some_image_id",
        levelProgress = 0.5f,
        level = 5,
        title = Title(
            name = "세상을 움직이는 자",
            grade = TitleGrade.Standard,
            type = TitleType.Contribution
        )
    )
    MyProfileInfoCard(
        myProfileInfo = myProfileInfo,
        onProfileEditButtonClick = {},
        onTitleClick = {},
        onMissionHistoryButtonClick = {},
        onFavoriteQuestButtonClick = {},
        onCouponButtonClick = {}
    )
}
