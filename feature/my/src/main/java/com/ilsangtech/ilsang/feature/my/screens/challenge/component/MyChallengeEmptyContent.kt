package com.ilsangtech.ilsang.feature.my.screens.challenge.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary

@Composable
internal fun MyChallengeEmptyContent(
    modifier: Modifier = Modifier,
    onQuestNavButtonClick: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = "아직 수행한 퀘스트가 없어요",
                style = TextStyle(
                    fontFamily = pretendardFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 21.sp,
                    lineHeight = 1.5.em
                )
            )
            Text(
                modifier = Modifier.padding(top = 6.dp),
                text = "내 지역의 퀘스트를\n" +
                        "수행 해보세요!",
                style = TextStyle(
                    fontFamily = pretendardFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 17.sp,
                    lineHeight = 1.5.em
                )
            )
            Button(
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary,
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(
                    vertical = 16.dp,
                    horizontal = 24.dp
                ),
                onClick = onQuestNavButtonClick,
            ) {
                Text(
                    text = "퀘스트 바로가",
                    style = TextStyle(
                        fontFamily = pretendardFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        lineHeight = 18.sp
                    )
                )
            }
        }
    }
}