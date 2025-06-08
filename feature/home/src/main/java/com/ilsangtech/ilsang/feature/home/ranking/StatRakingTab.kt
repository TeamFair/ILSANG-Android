package com.ilsangtech.ilsang.feature.home.ranking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.model.RewardType
import com.ilsangtech.ilsang.core.model.UserXpTypeRank
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_bold
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_regular
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.heading02
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.feature.home.BuildConfig
import com.ilsangtech.ilsang.feature.home.R
import com.ilsangtech.ilsang.core.util.XpLevelCalculator

@Composable
fun StatRankingTabRow(
    selectedRewardType: RewardType,
    onRewardTypeSelect: (RewardType) -> Unit
) {
    val rewardTypes = RewardType.entries

    TabRow(
        containerColor = Color.Transparent,
        contentColor = gray500,
        selectedTabIndex = selectedRewardType.ordinal,
        indicator = { tabPositions ->
            if (selectedRewardType.ordinal < rewardTypes.size) {
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedRewardType.ordinal]),
                    width = 40.dp,
                    height = 3.dp,
                    color = primary,
                    shape = RectangleShape
                )
            }
        },
        divider = { HorizontalDivider(color = gray100) }
    ) {
        rewardTypes.forEachIndexed { index, rewardType ->
            Tab(
                modifier = Modifier
                    .height(44.sp.value.dp),
                selected = selectedRewardType.ordinal == index,
                onClick = { onRewardTypeSelect(rewardType) },
                content = {
                    Text(
                        text = rewardType.title,
                        style = TextStyle(
                            fontFamily = FontFamily(Font(pretendard_bold)),
                            fontSize = 14.sp,
                            lineHeight = 24.sp,
                        )
                    )
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = gray300
            )
        }
    }
}

@Composable
fun StatRankingTabContent(
    selectedRewardType: RewardType,
    userXpTypeRanks: List<UserXpTypeRank>
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(
            start = 20.dp,
            end = 20.dp,
            top = 20.dp,
            bottom = 56.dp
        )
    ) {
        itemsIndexed(userXpTypeRanks) { index, userStatRank ->
            StatRankingTabContentItem(
                rank = index,
                rewardType = selectedRewardType,
                userXpTypeRank = userStatRank
            )
        }
    }
}

@Composable
fun StatRankingTabContentItem(
    rank: Int,
    rewardType: RewardType,
    userXpTypeRank: UserXpTypeRank
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 19.dp,
                vertical = 21.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            UserRankIcon(rank)
            if (userXpTypeRank.profileImage == null) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF1F5FF)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("\uD83D\uDE0D")
                }
            } else {
                AsyncImage(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF1F5FF)),
                    contentScale = ContentScale.Crop,
                    model = BuildConfig.IMAGE_URL + userXpTypeRank.profileImage,
                    contentDescription = null
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = userXpTypeRank.nickname,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(pretendard_bold)),
                        fontSize = 15.sp,
                        lineHeight = 20.sp,
                        lineHeightStyle = LineHeightStyle(
                            alignment = LineHeightStyle.Alignment.Center,
                            trim = LineHeightStyle.Trim.None
                        ),
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = "${rewardType.title}: ${userXpTypeRank.xpPoint}p",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(pretendard_regular)),
                        fontSize = 13.sp,
                        lineHeight = 16.sp,
                        lineHeightStyle = LineHeightStyle(
                            alignment = LineHeightStyle.Alignment.Center,
                            trim = LineHeightStyle.Trim.None
                        ),
                        color = gray400
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(primary)
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = "LV.${XpLevelCalculator.getCurrentLevel(userXpTypeRank.xpPoint)}",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(pretendard_bold)),
                        fontSize = 13.sp,
                        lineHeight = 20.sp,
                        lineHeightStyle = LineHeightStyle(
                            alignment = LineHeightStyle.Alignment.Center,
                            trim = LineHeightStyle.Trim.None
                        ),
                        color = Color.White
                    )
                )
            }
        }
    }
}

@Composable
internal fun UserRankIcon(rank: Int) {
    when (rank) {
        0 -> {
            Box(
                modifier = Modifier.size(30.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.rank_gold),
                    tint = Color.Unspecified,
                    contentDescription = null
                )
            }
        }

        1 -> {
            Box(
                modifier = Modifier.size(30.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.rank_silver),
                    tint = Color.Unspecified,
                    contentDescription = null
                )
            }
        }

        2 -> {
            Box(
                modifier = Modifier.size(30.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.rank_bronze),
                    tint = Color.Unspecified,
                    contentDescription = null
                )
            }
        }

        else -> {
            Box(
                modifier = Modifier.size(30.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = (rank + 1).toString(),
                    style = heading02.copy(color = gray500)
                )
            }
        }
    }
}

@Preview
@Composable
private fun StatRankingTabContentsPreview() {
    val userXpTypeRanks = List(20) {
        UserXpTypeRank(
            customerId = "",
            nickname = "닉네임",
            profileImage = null,
            xpPoint = 1340,
            xpType = ""
        )
    }
    StatRankingTabContent(
        selectedRewardType = RewardType.CHARM,
        userXpTypeRanks
    )
}
