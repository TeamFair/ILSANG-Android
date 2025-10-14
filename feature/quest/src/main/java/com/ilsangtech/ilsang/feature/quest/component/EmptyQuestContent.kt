package com.ilsangtech.ilsang.feature.quest.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.feature.quest.model.QuestTabUiModel

@Composable
fun EmptyQuestContent(
    modifier: Modifier = Modifier,
    selectedQuestType: QuestTabUiModel
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            val (headingText, subText) = "완료된 퀘스트가 없어요" to "퀘스트를 수행하러 가볼까요?"
            Text(
                text = headingText,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontFamily = pretendardFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 21.sp,
                    lineHeight = 1.5.em
                ),
                color = gray500
            )
            Text(
                text = subText,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontFamily = pretendardFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 17.sp,
                    lineHeight = 1.5.em
                ),
                color = gray400
            )
        }
    }
}