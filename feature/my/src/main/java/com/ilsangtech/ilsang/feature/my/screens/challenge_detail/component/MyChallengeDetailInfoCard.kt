package com.ilsangtech.ilsang.feature.my.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_bold
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_regular
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_semibold
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.primary100
import com.ilsangtech.ilsang.feature.my.BuildConfig
import com.ilsangtech.ilsang.feature.my.R
import com.ilsangtech.ilsang.feature.my.screens.challenge_detail.MyChallengeDetailUiState
import java.util.Locale

@Composable
fun MyChallengeDetailInfoCard(
    modifier: Modifier = Modifier,
    challenge: MyChallengeDetailUiState
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 20.dp,
                    horizontal = 19.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(primary100),
                model = BuildConfig.IMAGE_URL + challenge.questImageId,
                contentDescription = null
            )
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    text = challenge.title,
                    style = myChallengeInfoCardTitleTextStyle
                )
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier.size(12.dp),
                        painter = painterResource(R.drawable.eye),
                        tint = gray500,
                        contentDescription = null
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = String.format(
                            locale = Locale.KOREA,
                            format = "%,d",
                            challenge.viewCount
                        ),
                        style = myChallengeInfoCardBodyTextStyle
                    )
                    Box(
                        modifier = Modifier.width(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        VerticalDivider(
                            modifier = Modifier.height(12.dp),
                            color = gray300
                        )
                    }
                    Icon(
                        modifier = Modifier.size(12.dp),
                        painter = painterResource(R.drawable.like),
                        tint = gray500,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = String.format(
                            locale = Locale.KOREA,
                            format = "%,d",
                            challenge.likeCount
                        ),
                        style = myChallengeInfoCardBodyTextStyle
                    )
                }
            }
            Spacer(Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .size(
                        width = 40.dp,
                        height = 20.dp
                    )
                    .clip(RoundedCornerShape(12.dp))
                    .background(gray300),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "1/1",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(pretendard_semibold)),
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        color = Color.White
                    )
                )
            }
        }
    }
}

private val myChallengeInfoCardTitleTextStyle = TextStyle(
    fontFamily = FontFamily(Font(pretendard_bold)),
    fontSize = 19.sp,
    lineHeight = 1.3.em,
    color = Color.Black
)

private val myChallengeInfoCardBodyTextStyle = TextStyle(
    fontFamily = FontFamily(Font(pretendard_regular)),
    fontSize = 12.sp,
    lineHeight = 16.sp,
    color = gray500
)

@Preview
@Composable
fun MyChallengeDetailInfoCardPreview() {
    MyChallengeDetailInfoCard(
        challenge = MyChallengeDetailUiState(
            challengeId = "",
            likeCount = 13,
            title = "겨울 간식 먹기",
            questImageId = "",
            receiptImageId = "",
            viewCount = 1302
        )
    )
}