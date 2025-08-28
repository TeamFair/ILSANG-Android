package com.ilsangtech.ilsang.feature.profile.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.core.model.RewardPoint
import com.ilsangtech.ilsang.core.ui.season.model.SeasonUiModel
import com.ilsangtech.ilsang.core.ui.user.UserObtainedPointCard
import com.ilsangtech.ilsang.designsystem.theme.heading01

@Composable
internal fun UserObtainedPointContent(
    modifier: Modifier = Modifier,
    nickname: String,
    completedQuestCount: Int,
    metroAreaPoint: RewardPoint.Metro,
    commercialAreaPoint: RewardPoint.Commercial,
    contributionPoint: RewardPoint.Contribute,
    seasonList: List<SeasonUiModel>,
    selectedSeason: SeasonUiModel,
    onSeasonChange: (SeasonUiModel) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "$nickname 님의 포인트",
            style = heading01,
            color = Color.Black
        )
        UserObtainedPointCard(
            completedQuestCount = completedQuestCount,
            metroAreaPoint = metroAreaPoint,
            commercialAreaPoint = commercialAreaPoint,
            contributionPoint = contributionPoint,
            seasonList = seasonList,
            selectedSeason = selectedSeason,
            onSeasonChange = onSeasonChange
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xF6F6F6)
@Composable
private fun UserObtainedPointContentPreview() {
    val seasonList = listOf(
        SeasonUiModel.Total,
        SeasonUiModel.Specific(id = 1, seasonNumber = 1),
        SeasonUiModel.Specific(id = 2, seasonNumber = 2),
        SeasonUiModel.Specific(id = 3, seasonNumber = 3)
    )
    UserObtainedPointContent(
        nickname = "일상맨",
        completedQuestCount = 10,
        metroAreaPoint = RewardPoint.Metro(1100),
        commercialAreaPoint = RewardPoint.Commercial(2200),
        contributionPoint = RewardPoint.Contribute(2300),
        seasonList = seasonList,
        selectedSeason = seasonList.first(),
        onSeasonChange = {}
    )
}