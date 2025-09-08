package com.ilsangtech.ilsang.core.ui.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.core.model.reward.RewardPoint
import com.ilsangtech.ilsang.core.ui.quest.RewardPointIcon
import com.ilsangtech.ilsang.core.ui.season.model.SeasonUiModel
import com.ilsangtech.ilsang.designsystem.component.BorderedDropDownMenu
import com.ilsangtech.ilsang.designsystem.theme.caption02
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.tapRegularTextStyle
import com.ilsangtech.ilsang.designsystem.theme.title01
import com.ilsangtech.ilsang.designsystem.theme.title02
import java.util.Locale

@Composable
fun UserObtainedPointCard(
    modifier: Modifier = Modifier,
    completedQuestCount: Int,
    metroAreaPoint: RewardPoint.Metro,
    commercialAreaPoint: RewardPoint.Commercial,
    contributionPoint: RewardPoint.Contribute,
    seasonList: List<SeasonUiModel>,
    selectedSeason: SeasonUiModel,
    onSeasonChange: (SeasonUiModel) -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "수행한 퀘스트",
                        style = tapRegularTextStyle,
                        color = gray500
                    )
                    Text(
                        text = "${completedQuestCount}개",
                        style = title01,
                        color = Color.Black
                    )
                }
                BorderedDropDownMenu(
                    list = seasonList,
                    selectedItem = selectedSeason,
                    onItemSelected = onSeasonChange,
                )
            }
            Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                UserObtainedPointItem(
                    modifier = Modifier.weight(1f),
                    rewardPoint = metroAreaPoint
                )
                VerticalDivider(color = gray100)
                UserObtainedPointItem(
                    modifier = Modifier.weight(1f),
                    rewardPoint = commercialAreaPoint
                )
                VerticalDivider(color = gray100)
                UserObtainedPointItem(
                    modifier = Modifier.weight(1f),
                    rewardPoint = contributionPoint
                )
            }
        }
    }
}

@Composable
private fun UserObtainedPointItem(
    modifier: Modifier = Modifier,
    rewardPoint: RewardPoint
) {
    Column(
        modifier = modifier
            .padding(vertical = 16.dp)
            .padding(start = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RewardPointIcon(
                modifier = Modifier.size(18.dp),
                rewardPoint = rewardPoint
            )
            Text(
                text = when (rewardPoint) {
                    is RewardPoint.Metro -> "일상지역"
                    is RewardPoint.Commercial -> "일상존"
                    is RewardPoint.Contribute -> "기여도"
                },
                style = tapRegularTextStyle,
                color = gray400
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "획득 포인트",
                style = caption02,
                color = gray400
            )
            Text(
                text = String.format(
                    Locale.getDefault(),
                    "%,d",
                    rewardPoint.point
                ) + "P",
                style = title02,
                color = gray500
            )
        }
    }
}

@Preview
@Composable
private fun UserObtainedPointCardPreview() {
    val seasonList = listOf(
        SeasonUiModel.Total,
        SeasonUiModel.Specific(id = 1, seasonNumber = 1),
        SeasonUiModel.Specific(id = 2, seasonNumber = 2),
        SeasonUiModel.Specific(id = 3, seasonNumber = 3)
    )
    UserObtainedPointCard(
        completedQuestCount = 10,
        metroAreaPoint = RewardPoint.Metro(point = 1000),
        commercialAreaPoint = RewardPoint.Commercial(point = 500),
        contributionPoint = RewardPoint.Contribute(point = 200),
        seasonList = seasonList,
        selectedSeason = seasonList.first(),
        onSeasonChange = {}
    )
}
