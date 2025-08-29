package com.ilsangtech.ilsang.feature.my.screens.mytab.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.core.ui.user.TopCommercialAreaContent
import com.ilsangtech.ilsang.core.ui.user.TotalOwnerContributionContent
import com.ilsangtech.ilsang.core.ui.user.model.TopCommercialAreaUiModel
import com.ilsangtech.ilsang.core.ui.user.model.TotalOwnerContributionUiModel
import com.ilsangtech.ilsang.core.ui.user.model.UserCommercialPointUiModel
import com.ilsangtech.ilsang.designsystem.theme.heading01
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary

@Composable
internal fun MyCommercialPointContent(
    modifier: Modifier = Modifier,
    myCommercialPoint: UserCommercialPointUiModel,
    onQuestNavButtonClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "내 일상존",
            style = heading01,
            color = Color.Black
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                TopCommercialAreaContent(
                    topCommercialArea = myCommercialPoint.topCommercialArea!!
                )
                Spacer(Modifier.height(28.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primary,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    onClick = onQuestNavButtonClick
                ) {
                    Text(
                        text = "퀘스트 바로가기",
                        style = TextStyle(
                            fontFamily = pretendardFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            lineHeight = 18.sp
                        )
                    )
                }
                Spacer(Modifier.height(48.dp))
                TotalOwnerContributionContent(
                    totalOwnerContributions = myCommercialPoint.totalOwnerContributions
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MyCommercialPointContentPreview() {
    val myCommercialPoint = UserCommercialPointUiModel(
        nickname = "User",
        topCommercialArea = TopCommercialAreaUiModel(
            commercialAreaName = "강남역",
            contributionPercent = 70,
            point = 1000
        ),
        totalOwnerContributions = listOf(
            TotalOwnerContributionUiModel(
                commercialAreaName = "역삼역",
                point = 500
            ),
            TotalOwnerContributionUiModel(
                commercialAreaName = "선릉역",
                point = 300
            ),
            TotalOwnerContributionUiModel(
                commercialAreaName = "삼성역",
                point = 300
            )
        )
    )
    MyCommercialPointContent(
        myCommercialPoint = myCommercialPoint,
        onQuestNavButtonClick = {}
    )
}