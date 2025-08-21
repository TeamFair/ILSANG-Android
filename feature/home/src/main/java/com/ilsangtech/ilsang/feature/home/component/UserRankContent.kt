package com.ilsangtech.ilsang.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.model.UserRank
import com.ilsangtech.ilsang.core.model.title.Title
import com.ilsangtech.ilsang.core.model.title.TitleGrade
import com.ilsangtech.ilsang.core.model.title.TitleType
import com.ilsangtech.ilsang.core.ui.title.TitleGradeIcon
import com.ilsangtech.ilsang.designsystem.theme.badge01TextStyle
import com.ilsangtech.ilsang.designsystem.theme.caption01
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.title01
import com.ilsangtech.ilsang.designsystem.theme.toSp
import com.ilsangtech.ilsang.feature.home.BuildConfig
import com.ilsangtech.ilsang.feature.home.R

@Composable
internal fun UserRankContent(
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
                    onClickRankCard = { navigateToProfile(userRank.userId) }
                )
            }
        }
    }
}

@Composable
private fun UserRankCard(
    userRank: UserRank,
    onClickRankCard: () -> Unit
) {
    Card(
        modifier = Modifier.width(150.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        onClick = onClickRankCard
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 11.dp, vertical = 23.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (userRank.profileImageId == null) {
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
                    model = BuildConfig.IMAGE_URL + userRank.profileImageId,
                    contentDescription = userRank.nickName
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
                                fontFamily = pretendardFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                color = gray500
                            )
                        )
                    }
                }
            }
            Spacer(Modifier.height(7.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = userRank.nickName,
                    style = userRankCardNicknameStyle,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    TitleGradeIcon(
                        modifier = Modifier.size(12.dp),
                        titleGrade = userRank.title.grade
                    )
                    Text(
                        text = userRank.title.name,
                        style = badge01TextStyle.copy(
                            fontSize = 11.dp.toSp(),
                            lineHeight = 12.dp.toSp()
                        ),
                        color = gray400,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
                Text(
                    text = "${userRank.point}p",
                    style = caption01,
                    color = gray500,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    }
}

private val userRankContentTitleStyle = TextStyle(
    fontSize = 19.sp,
    lineHeight = 22.sp,
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.SemiBold
)

private val userRankCardNicknameStyle = TextStyle(
    fontSize = 13.sp,
    lineHeight = 20.sp,
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.Bold
)

@Preview(showBackground = true)
@Composable
private fun UserRankContentPreview() {
    val rankList = listOf(
        UserRank(
            userId = "1",
            nickName = "User 1",
            point = 100,
            profileImageId = "image1",
            rank = 1,
            title = Title(name = "모두의 시선을 받는 자", grade = TitleGrade.Legend, type = TitleType.Metro)
        ),
        UserRank(
            userId = "2",
            nickName = "User 2",
            point = 90,
            profileImageId = "image2",
            rank = 2,
            title = Title(
                name = "모두의 시선을 받는 자자자자 ",
                grade = TitleGrade.Rare,
                type = TitleType.Commercial
            )
        ),
        UserRank(
            userId = "3",
            nickName = "User 3",
            point = 80,
            profileImageId = "image3",
            rank = 3,
            title = Title(
                name = "Title 3",
                grade = TitleGrade.Standard,
                type = TitleType.Contribution
            )
        )
    )
    UserRankContent(
        rankList = rankList,
        navigateToRankingTab = {},
        navigateToProfile = {}
    )
}