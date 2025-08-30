package com.ilsangtech.ilsang.feature.my.screens.mytab.component

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
import com.ilsangtech.ilsang.core.ui.user.model.UserObtainedPointUiModel
import com.ilsangtech.ilsang.designsystem.theme.heading01

@Composable
internal fun MyObtainedPointContent(
    modifier: Modifier = Modifier,
    myPoint: UserObtainedPointUiModel,
    selectedSeason: SeasonUiModel,
    onSeasonChanged: (SeasonUiModel) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "내 포인트",
            style = heading01,
            color = Color.Black
        )
        UserObtainedPointCard(
            completedQuestCount = myPoint.completedQuestCount,
            metroAreaPoint = myPoint.metroAreaPoint,
            commercialAreaPoint = myPoint.commercialAreaPoint,
            contributionPoint = myPoint.contributionPoint,
            seasonList = myPoint.seasonList,
            selectedSeason = selectedSeason,
            onSeasonChange = onSeasonChanged
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MyObtainedPointContentPreview() {
    val myPoint = UserObtainedPointUiModel(
        completedQuestCount = 10,
        metroAreaPoint = RewardPoint.Metro(100),
        commercialAreaPoint = RewardPoint.Commercial(200),
        contributionPoint = RewardPoint.Contribute(300),
        seasonList = listOf(
            SeasonUiModel.Total,
            SeasonUiModel.Specific(id = 1, seasonNumber = 1),
            SeasonUiModel.Specific(id = 2, seasonNumber = 2)
        )
    )
    val selectedSeason = SeasonUiModel.Total
    MyObtainedPointContent(
        myPoint = myPoint,
        selectedSeason = selectedSeason,
        onSeasonChanged = {}
    )
}