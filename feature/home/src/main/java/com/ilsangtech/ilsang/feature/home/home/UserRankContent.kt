package com.ilsangtech.ilsang.feature.home.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.model.UserRank
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_bold
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_semibold
import com.ilsangtech.ilsang.designsystem.theme.caption01
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.title01
import com.ilsangtech.ilsang.feature.home.BuildConfig
import com.ilsangtech.ilsang.feature.home.R

@Composable
fun UserRankContent(
    rankList: List<UserRank>,
    navigateToRankingTab: () -> Unit,
    navigateToProfile: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "유저 랭킹",
                style = userRankContentTitleStyle
            )
            Spacer(Modifier.weight(1f))
            Icon(
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = null,
                    onClick = navigateToRankingTab
                ),
                painter = painterResource(R.drawable.user_rank_content_right_icon),
                tint = gray500,
                contentDescription = null
            )

        }
        Spacer(Modifier.height(12.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(rankList) { userRank ->
                UserRankCard(
                    userRank = userRank,
                    onClickRankCard = { navigateToProfile(userRank.customerId) }
                )
            }
        }
    }
}

private val userRankContentTitleStyle = TextStyle(
    fontSize = 19.sp,
    lineHeight = 22.sp,
    fontFamily = FontFamily(Font(pretendard_semibold))
)

@Composable
fun UserRankCard(
    userRank: UserRank,
    onClickRankCard: () -> Unit
) {
    Card(
        modifier = Modifier.size(
            width = 150.dp,
            height = 178.dp
        ),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        onClick = onClickRankCard
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (userRank.profileImageUrl == null) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(
                                color = Color(0xFFF1F5FF),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "😍",
                            style = title01
                        )
                    }
                } else {
                    AsyncImage(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape),
                        model = BuildConfig.IMAGE_URL + userRank.profileImageUrl,
                        contentDescription = userRank.nickname
                    )
                }
                Spacer(Modifier.height(7.dp))
                when (userRank.rank) {
                    1 -> {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(R.drawable.rank_gold),
                            tint = Color.Unspecified,
                            contentDescription = "Rank Gold"
                        )
                    }

                    2 -> {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(R.drawable.rank_silver),
                            tint = Color.Unspecified,
                            contentDescription = "Rank Silver"
                        )
                    }

                    3 -> {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(R.drawable.rank_bronze),
                            tint = Color.Unspecified,
                            contentDescription = "Rank Bronze"
                        )
                    }

                    else -> {
                        Box(
                            modifier = Modifier.size(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = userRank.rank.toString(),
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily(Font(pretendard_semibold)),
                                    color = gray500
                                )
                            )
                        }
                    }
                }
                Spacer(Modifier.height(7.dp))
                Text(
                    text = userRank.nickname,
                    style = userRankCardNicknameStyle
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = "${userRank.xpSum}xp",
                    style = caption01,
                    color = gray500
                )
            }
        }
    }
}

private val userRankCardNicknameStyle = TextStyle(
    fontSize = 13.sp,
    fontFamily = FontFamily(Font(pretendard_bold)),
    lineHeight = 20.sp
)

@Preview(showBackground = true)
@Composable
fun UserRankContentPreview() {
    UserRankContent((
            listOf(
                UserRank(
                    customerId = "",
                    nickname = "닉네임1",
                    xpSum = 300,
                    profileImageUrl = null,
                    rank = 1
                )
            )), {}, {})
}

@Preview
@Composable
fun UserRankCardPreview() {
    UserRankCard(
        UserRank(
            "", "닉네임1", 300, null, 1
        )
    ) {}
}
