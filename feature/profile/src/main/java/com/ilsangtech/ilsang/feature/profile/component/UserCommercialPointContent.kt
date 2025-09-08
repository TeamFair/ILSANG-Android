package com.ilsangtech.ilsang.feature.profile.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.core.ui.user.TopCommercialAreaContent
import com.ilsangtech.ilsang.core.ui.user.TotalOwnerContributionContent
import com.ilsangtech.ilsang.core.ui.user.model.TopCommercialAreaUiModel
import com.ilsangtech.ilsang.core.ui.user.model.TotalOwnerContributionUiModel
import com.ilsangtech.ilsang.core.ui.user.model.UserCommercialPointUiModel
import com.ilsangtech.ilsang.designsystem.theme.heading01

@Composable
internal fun UserCommercialPointContent(
    modifier: Modifier = Modifier,
    userCommercialPointUiModel: UserCommercialPointUiModel
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = userCommercialPointUiModel.nickname + " 님의 일상존",
            style = heading01
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(48.dp)
            ) {
                userCommercialPointUiModel.topCommercialArea?.let { topCommercialArea ->
                    TopCommercialAreaContent(
                        modifier = Modifier.fillMaxWidth(),
                        topCommercialArea = topCommercialArea
                    )
                }
                TotalOwnerContributionContent(
                    totalOwnerContributions = userCommercialPointUiModel.totalOwnerContributions
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UserCommercialPointContentPreview() {
    val userCommercialPointUiModel = UserCommercialPointUiModel(
        nickname = "박일상 Daily Park",
        topCommercialArea = TopCommercialAreaUiModel(
            commercialAreaName = "정자역",
            contributionPercent = 32,
            point = 4200
        ),
        totalOwnerContributions = listOf(
            TotalOwnerContributionUiModel(
                commercialAreaName = "정자역",
                point = 4200
            ),
            TotalOwnerContributionUiModel(
                commercialAreaName = "야탑역",
                point = 1200
            ),
            TotalOwnerContributionUiModel(
                commercialAreaName = "이매역",
                point = 600
            )
        )
    )
    UserCommercialPointContent(
        userCommercialPointUiModel = userCommercialPointUiModel
    )
}