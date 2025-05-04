package com.ilsangtech.ilsang.feature.home.approval

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.model.RandomChallenge
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_regular
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_semibold
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray200
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.heading02
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.primary100
import com.ilsangtech.ilsang.designsystem.theme.primary300
import com.ilsangtech.ilsang.designsystem.theme.title01
import com.ilsangtech.ilsang.feature.home.BuildConfig
import com.ilsangtech.ilsang.feature.home.R
import com.ilsangtech.ilsang.feature.home.util.DateConverter

@Composable
fun ApprovalItem(challenge: RandomChallenge) {
fun ApprovalItem(
    challenge: RandomChallenge,
) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp,
                    vertical = 16.dp
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ApprovalItemHeader(
                userProfileImage = challenge.userProfileImage,
                userNickname = challenge.userNickName,
                createdAt = challenge.createdAt
            )
            ApprovalItemBody(
                title = challenge.missionTitle,
                challengeImage = challenge.receiptImageId
            )
            ApprovalItemFooter(
                isLiked = challenge.emoji?.isLike == true,
                isHated = challenge.emoji?.isHate == true,
                likeCount = challenge.likeCnt,
                hateCount = challenge.hateCnt,
                onLikeButtonClick = {},
                onHateButtonClick = {}
            )
        }
    }
}

@Composable
fun ApprovalItemHeader(
    userProfileImage: String?,
    userNickname: String,
    createdAt: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(CircleShape)
                .size(35.dp),
            model = userProfileImage,
            contentDescription = null,
            error = painterResource(R.drawable.default_user_profile),
            placeholder = painterResource(R.drawable.default_user_profile)
        )
        Spacer(Modifier.width(10.dp))
        Column {
            Text(
                text = userNickname,
                style = approvalItemNicknameTextStyle
            )
            Text(
                text = DateConverter.timeAgoSinceDate(createdAt),
                style = approvalItemTimeTextStyle
            )
        }
        Spacer(Modifier.weight(1f))
        Icon(
            modifier = Modifier.size(30.dp),
            painter = painterResource(R.drawable.more_vertical),
            tint = gray500,
            contentDescription = null
        )
    }
}

@Composable
fun ApprovalItemBody(
    title: String,
    challengeImage: String,

    ) {
    Column(
        modifier = Modifier.fillMaxWidth(),
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
    }
}

@Composable
fun ApprovalItemFooter(
    isLiked: Boolean,
    isHated: Boolean,
    likeCount: Int,
    hateCount: Int,
    onLikeButtonClick: () -> Unit,
    onHateButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ApprovalFeedbackButton(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(top = 11.dp, bottom = 9.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isHated) primary100 else gray100,
                    contentColor = if (isHated) primary300 else gray300
                ),
                onClick = onHateButtonClick
            ) {
                Icon(
                    modifier = Modifier
                        .padding(vertical = 3.dp)
                        .padding(start = 2.dp, end = 1.dp),
                    painter = painterResource(R.drawable.thumbs_down),
                    contentDescription = null
                )
            }

            ApprovalFeedbackButton(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(top = 9.dp, bottom = 11.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isLiked) primary else gray100,
                    contentColor = if (isLiked) Color.White else gray300
                ),
                onClick = onLikeButtonClick
            ) {
                Icon(
                    modifier = Modifier
                        .padding(vertical = 3.dp)
                        .padding(start = 1.dp, end = 2.dp),
                    painter = painterResource(R.drawable.thumbs_up),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun ApprovalFeedbackButton(
    modifier: Modifier,
    onClick: () -> Unit,
    contentPadding: PaddingValues,
    colors: ButtonColors,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        content = content,
        contentPadding = contentPadding,
        colors = colors,
        shape = RoundedCornerShape(12.dp),
        onClick = onClick
    )
}


private val approvalItemNicknameTextStyle = TextStyle(
    fontFamily = FontFamily(Font(pretendard_semibold)),
    fontSize = 14.sp,
    lineHeight = 12.sp,
    letterSpacing = 0.sp,
    color = gray500
)

private val approvalItemTimeTextStyle = TextStyle(
    fontFamily = FontFamily(Font(pretendard_regular)),
    fontSize = 12.sp,
    lineHeight = 12.sp,
    letterSpacing = 0.sp,
    color = gray500
)

@Preview(showBackground = true)
@Composable
fun ApprovalItemPreview() {
    val challenge = RandomChallenge(
        challengeId = "",
        createdAt = "3시간전",
        hateCnt = 1,
        likeCnt = 2,
        missionTitle = "바닐라라떼 마시기",
        receiptImageId = "",
        status = "",
        userNickName = "일상123",
        customerId = "",
        userProfileImage = "",
        viewCount = 4,
        emoji = null
    )
    ApprovalItem(challenge)
    ApprovalItem(
        challenge = challenge,
    )
}