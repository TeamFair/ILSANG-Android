package com.ilsangtech.ilsang.feature.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.model.Challenge
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.feature.profile.component.UserChallengeInfoCard

@Composable
internal fun ChallengeScreen(
    receiptImageId: String?,
    questImageId: String?,
    likeCount: Int,
    title: String,
    popBackStack: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            ChallengeScreenHeader(onBackButtonClick = popBackStack)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = BuildConfig.IMAGE_URL + receiptImageId,
                    contentScale = ContentScale.Fit,
                    contentDescription = null
                )
                UserChallengeInfoCard(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(bottom = 16.dp)
                        .navigationBarsPadding()
                        .align(Alignment.BottomCenter),
                    questImageId = questImageId,
                    likeCount = likeCount,
                    title = title
                )
            }
        }
    }
}

@Composable
private fun ChallengeScreenHeader(
    onBackButtonClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 45.dp)
            .padding(
                horizontal = 12.dp,
                vertical = 7.dp
            )
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable(
                    onClick = onBackButtonClick,
                    indication = null,
                    interactionSource = null
                ),
            painter = painterResource(R.drawable.icon_back),
            contentDescription = null
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "챌린지 정보",
            style = TextStyle(
                fontFamily = pretendardFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = gray500
            )
        )
    }
}

@Preview
@Composable
private fun ChallengeScreenPreview() {
    val challenge = Challenge(
        challengeId = "",
        createdAt = "",
        hateCnt = 12,
        likeCnt = 13,
        missionTitle = "겨울 간식 먹기",
        questImage = "",
        receiptImageId = "",
        status = "",
        userNickName = "",
        viewCount = 1302
    )

    ChallengeScreen(
        receiptImageId = challenge.receiptImageId,
        questImageId = challenge.questImage,
        likeCount = challenge.likeCnt,
        title = challenge.missionTitle,
        popBackStack = {}
    )
}