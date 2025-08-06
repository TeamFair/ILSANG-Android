package com.ilsangtech.ilsang.core.ui.quest

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.model.Quest
import com.ilsangtech.ilsang.core.model.QuestType
import com.ilsangtech.ilsang.core.model.Reward
import com.ilsangtech.ilsang.core.model.RewardType
import com.ilsangtech.ilsang.core.ui.BuildConfig
import com.ilsangtech.ilsang.core.ui.R
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_bold
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_regular
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_semibold
import com.ilsangtech.ilsang.designsystem.component.IlsangBottomSheet
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.badge01TextStyle
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.heading01
import com.ilsangtech.ilsang.designsystem.theme.heading02
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.primary100
import com.ilsangtech.ilsang.designsystem.theme.primary300

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestBottomSheet(
    quest: Quest,
    bottomSheetState: SheetState,
    onFavoriteClick: () -> Unit,
    onApproveButtonClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    IlsangBottomSheet(
        bottomSheetState = bottomSheetState,
        onDismissRequest = onDismiss
    ) {
        QuestBottomSheetHeader(
            isFavorite = quest.favoriteYn,
            onFavoriteClick = onFavoriteClick
        )
        QuestBottomSheetContent(
            modifier = Modifier.padding(horizontal = 20.dp),
            quest = quest,
            onApproveButtonClick = onApproveButtonClick
        )
    }
}

@Composable
private fun QuestBottomSheetHeader(
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 14.dp,
                bottom = 15.dp
            )
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "퀘스트 정보",
            style = questBottomSheetTitleStyle
        )
        Icon(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 20.dp)
                .clickable(
                    onClick = onFavoriteClick,
                    indication = null,
                    interactionSource = null
                ),
            painter = painterResource(com.ilsangtech.ilsang.designsystem.R.drawable.icon_favorite),
            contentDescription = "즐겨찾기",
            tint = if (isFavorite) primary300 else gray100
        )
    }
}

@Composable
private fun QuestBottomSheetContent(
    modifier: Modifier = Modifier,
    quest: Quest,
    onApproveButtonClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF1F5FF)),
                model = BuildConfig.IMAGE_URL + quest.imageId,
                contentDescription = quest.missionTitle
            )
            Spacer(Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text =
                        (QuestType.entries.find {
                            it.name == quest.type
                        }?.title ?: "기본") + " 퀘스트",
                    style = questBottomSheetQuestTypeTextStyle
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = quest.missionTitle,
                    style = questBottomSheetQuestTitleStyle,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
            Box(
                modifier = Modifier
                    .background(
                        color = primary100,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(10.dp)
            ) {
                Text(
                    text = "${quest.rewardList.sumOf { it.quantity }}XP",
                    style = heading01,
                    color = primary,
                )
            }
        }

        Box(
            modifier = Modifier
                .background(
                    color = primary,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(
                    horizontal = 8.dp,
                    vertical = 4.dp
                )
        ) {
            Text(
                text = "획득 가능 스텟",
                style = badge01TextStyle,
                color = Color.White
            )
        }
        ObtainableStatItemContent(quest.rewardList)
        Text(
            text = "퀘스트를 수행하셨나요?\n" +
                    "인증 후 포인트를 적립받으세요",
            textAlign = TextAlign.Center,
            style = questBottomSheetDescriptionTextStyle
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onApproveButtonClick,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primary
            ),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            Text(
                text = "퀘스트 인증하기",
                style = questBottomSheetApproveButtonTextStyle
            )
        }
    }
}

@Composable
private fun ObtainableStatItemContent(rewardList: List<Reward>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            val strengthReward = rewardList.find { it.content == RewardType.STRENGTH.name }
            val intellectReward = rewardList.find { it.content == RewardType.INTELLECT.name }
            val charmReward = rewardList.find { it.content == RewardType.CHARM.name }

            ObtainableStatItem(
                rewardType = RewardType.STRENGTH,
                xp = strengthReward?.quantity ?: 0
            )
            ObtainableStatItem(
                rewardType = RewardType.INTELLECT,
                xp = intellectReward?.quantity ?: 0
            )
            ObtainableStatItem(
                rewardType = RewardType.CHARM,
                xp = charmReward?.quantity ?: 0
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            val funReward = rewardList.find { it.content == RewardType.FUN.name }
            val sociabilityReward =
                rewardList.find { it.content == RewardType.SOCIABILITY.name }

            ObtainableStatItem(
                rewardType = RewardType.FUN,
                xp = funReward?.quantity ?: 0
            )
            ObtainableStatItem(
                rewardType = RewardType.SOCIABILITY,
                xp = sociabilityReward?.quantity ?: 0
            )
        }
    }
}

@Composable
private fun ObtainableStatItem(rewardType: RewardType, xp: Int) {
    val painterResource = painterResource(
        when (rewardType) {
            RewardType.STRENGTH -> {
                R.drawable.strength
            }

            RewardType.INTELLECT -> {
                R.drawable.bulb
            }

            RewardType.CHARM -> {
                R.drawable.heart
            }

            RewardType.FUN -> {
                R.drawable.`fun`
            }

            RewardType.SOCIABILITY -> {
                R.drawable.social
            }
        }
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .width(60.dp)
                .background(
                    color = background,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = rewardType.title,
                style = heading02,
                color = gray500
            )
        }
        Icon(
            modifier = Modifier.size(48.dp),
            painter = painterResource,
            tint = Color.Unspecified,
            contentDescription = "보상 타입 아이콘"
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "${xp}P",
                style = questBottomSheetRewardPointTextStyle
            )
            Icon(
                modifier = Modifier.padding(
                    vertical = 3.dp,
                    horizontal = 4.dp
                ),
                painter = painterResource(R.drawable.quest_bottom_sheet_reward_xp_up),
                tint = Color.Unspecified,
                contentDescription = null
            )
        }
    }
}

private val questBottomSheetTitleStyle = TextStyle(
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 17.sp,
    lineHeight = 22.sp,
    color = gray500
)

private val questBottomSheetQuestTypeTextStyle = TextStyle(
    fontFamily = FontFamily(Font(pretendard_regular)),
    fontSize = 15.sp,
    lineHeight = 30.sp,
    letterSpacing = (-0.02).em,
    color = gray500
)

private val questBottomSheetQuestTitleStyle = TextStyle(
    fontFamily = FontFamily(Font(pretendard_bold)),
    fontSize = 18.sp,
    color = gray500,
    lineHeight = 30.sp,
    letterSpacing = (-0.02).sp,
)

private val questBottomSheetRewardPointTextStyle = TextStyle(
    fontFamily = FontFamily(Font(pretendard_bold)),
    fontSize = 13.sp,
    lineHeight = 20.sp,
    color = primary
)

private val questBottomSheetDescriptionTextStyle = TextStyle(
    fontFamily = FontFamily(Font(pretendard_regular)),
    fontSize = 14.sp,
    lineHeight = 22.sp,
    color = gray500
)

private val questBottomSheetApproveButtonTextStyle = TextStyle(
    fontFamily = FontFamily(Font(pretendard_semibold)),
    fontSize = 16.sp,
    lineHeight = 18.sp,
    color = Color.White
)

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun QuestBottomSheetPreview() {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    QuestBottomSheet(
        quest = previewQuest,
        bottomSheetState = bottomSheetState,
        onFavoriteClick = {},
        onApproveButtonClick = {},
        onDismiss = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun QuestBottomSheetContentPreview() {
    QuestBottomSheetContent(quest = previewQuest) {}
}

private val previewReward = Reward(
    content = "",
    quantity = 100,
    rewardId = "",
    type = "INTELLECT",
    discountRate = 0,
    questId = "",
    target = "",
    title = ""
)

private val previewQuest = Quest(
    createDate = "",
    creatorRole = "",
    expireDate = "",
    favoriteYn = false,
    imageId = "",
    mainImageId = "",
    marketId = "",
    missionId = "",
    missionTitle = "아메리카노 마시기",
    missionType = "",
    popularYn = true,
    questId = "",
    rewardList = listOf(previewReward),
    score = 40,
    status = "",
    target = "",
    type = "",
    writer = ""
)