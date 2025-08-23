package com.ilsangtech.ilsang.feature.quest.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.feature.quest.model.QuestTabUiModel

@Composable
internal fun QuestTabHeader(
    selectedQuestTab: QuestTabUiModel,
    onQuestTabSelected: (QuestTabUiModel) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 20.dp,
                vertical = 10.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        QuestTabUiModel.entries.forEach { questType ->
            Box(
                modifier = Modifier
                    .clickable(
                        interactionSource = null,
                        indication = null,
                        onClick = { onQuestTabSelected(questType) }
                    )
            ) {
                Text(
                    text = questType.toString(),
                    style = questTypeTabTitleStyle,
                    color = if (selectedQuestTab == questType) gray500 else gray300
                )
            }
        }
    }
}

private val questTypeTabTitleStyle = TextStyle(
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 21.sp,
    lineHeight = 1.4.em
)

@Preview(showBackground = true)
@Composable
private fun QuestTypeTabRowPreview() {
    QuestTabHeader(
        selectedQuestTab = QuestTabUiModel.REPEAT,
        onQuestTabSelected = {}
    )
}