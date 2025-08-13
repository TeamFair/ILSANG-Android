package com.ilsangtech.ilsang.core.ui.quest.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.core.model.Reward
import com.ilsangtech.ilsang.core.model.RewardPoint
import com.ilsangtech.ilsang.core.model.RewardType
import com.ilsangtech.ilsang.core.ui.R
import com.ilsangtech.ilsang.core.ui.quest.RewardPointIcon
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_bold
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.toSp

@Composable
internal fun ObtainablePointContent(
    modifier: Modifier = Modifier,
    rewardPoints: List<RewardPoint>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        QuestBottomSheetTextChip(text = "획득 가능 포인트")
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            rewardPoints.forEach { rewardPoint ->
                ObtainablePointItem(rewardPoint)
            }
        }
    }
}

@Composable
private fun ObtainablePointItem(rewardPoint: RewardPoint) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .width(48.dp)
                .background(
                    color = background,
                    shape = RoundedCornerShape(9.dp)
                )
                .padding(vertical = 3.2.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = when (rewardPoint) {
                    is RewardPoint.Metro -> "일상지역"
                    is RewardPoint.Commerical -> "일상존"
                    is RewardPoint.Contribute -> "기여도"
                },
                style = TextStyle(
                    fontFamily = pretendardFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.dp.toSp(),
                    lineHeight = 14.4.dp.toSp(),
                    letterSpacing = (-0.32).dp.toSp()
                ),
                color = gray500
            )
        }
        RewardPointIcon(
            modifier = Modifier.size(38.dp),
            rewardPoint = rewardPoint
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "${rewardPoint.point}P",
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

@Composable
internal fun ObtainablePointsContent(rewardList: List<Reward>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        QuestBottomSheetTextChip(text = "획득 가능 스텟")
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            val strengthReward = rewardList.find { it.content == RewardType.STRENGTH.name }
            val intellectReward = rewardList.find { it.content == RewardType.INTELLECT.name }
            val charmReward = rewardList.find { it.content == RewardType.CHARM.name }
            val funReward = rewardList.find { it.content == RewardType.FUN.name }
            val sociabilityReward = rewardList.find { it.content == RewardType.SOCIABILITY.name }

            ObtainablePointsItem(
                rewardType = RewardType.STRENGTH,
                xp = strengthReward?.quantity ?: 0
            )
            ObtainablePointsItem(
                rewardType = RewardType.INTELLECT,
                xp = intellectReward?.quantity ?: 0
            )
            ObtainablePointsItem(
                rewardType = RewardType.CHARM,
                xp = charmReward?.quantity ?: 0
            )
            ObtainablePointsItem(
                rewardType = RewardType.FUN,
                xp = funReward?.quantity ?: 0
            )
            ObtainablePointsItem(
                rewardType = RewardType.SOCIABILITY,
                xp = sociabilityReward?.quantity ?: 0
            )
        }
    }
}

@Composable
private fun ObtainablePointsItem(rewardType: RewardType, xp: Int) {
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
                .width(48.dp)
                .background(
                    color = background,
                    shape = RoundedCornerShape(9.dp)
                )
                .padding(vertical = 3.2.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = rewardType.title,
                style = TextStyle(
                    fontFamily = pretendardFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.dp.toSp(),
                    lineHeight = 14.4.dp.toSp(),
                    letterSpacing = (-0.32).dp.toSp()
                ),
                color = gray500
            )
        }
        Box(
            modifier = Modifier.size(38.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource,
                tint = Color.Unspecified,
                contentDescription = "보상 타입 아이콘"
            )
        }
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

private val questBottomSheetRewardPointTextStyle = TextStyle(
    fontFamily = FontFamily(Font(pretendard_bold)),
    fontSize = 13.sp,
    lineHeight = 20.sp,
    color = primary
)

@Preview(showBackground = true)
@Composable
private fun ObtainablePointContentPreview() {
    val rewardPoints = listOf(
        RewardPoint.Metro(point = 10),
        RewardPoint.Commerical(point = 20),
        RewardPoint.Contribute(point = 30)
    )
    ObtainablePointContent(rewardPoints = rewardPoints)
}

@Preview(showBackground = true)
@Composable
private fun ObtainablePointsContentPreview() {
    val rewardList = listOf(
        Reward(
            content = RewardType.STRENGTH.name,
            discountRate = null,
            quantity = 10,
            questId = "1",
            rewardId = "1",
            target = null,
            title = null,
            type = "XP"
        ),
        Reward(
            content = RewardType.INTELLECT.name,
            discountRate = null,
            quantity = 20,
            questId = "1",
            rewardId = "2",
            target = null,
            title = null,
            type = "XP"
        ),
        Reward(
            content = RewardType.CHARM.name,
            discountRate = null,
            quantity = 5,
            questId = "1",
            rewardId = "3",
            target = null,
            title = null,
            type = "XP"
        ),
        Reward(
            content = RewardType.FUN.name,
            discountRate = null,
            quantity = 15,
            questId = "1",
            rewardId = "4",
            target = null,
            title = null,
            type = "XP"
        ),
        Reward(
            content = RewardType.SOCIABILITY.name,
            discountRate = null,
            quantity = 25,
            questId = "1",
            rewardId = "5",
            target = null,
            title = null,
            type = "XP"
        )
    )
    ObtainablePointsContent(rewardList = rewardList)
}





