package com.ilsangtech.ilsang.feature.my.screens.favorite_quest.component

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary

@Composable
internal fun MyFavoriteQuestEmptyContent(
    modifier: Modifier = Modifier,
    onQuestNavButtonClick: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Column(
                modifier = Modifier.padding(bottom = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "즐겨찾기한 퀘스트가 없어요",
                    style = TextStyle(
                        fontFamily = pretendardFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 21.sp,
                        lineHeight = 1.5.em,
                        color = gray500,
                        textAlign = TextAlign.Center
                    )
                )
                Text(
                    text = "관심 있는 지역의 퀘스트를\n" +
                            "즐겨찾기 해보세요!",
                    style = TextStyle(
                        fontFamily = pretendardFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 17.sp,
                        lineHeight = 1.5.em,
                        color = gray400,
                        textAlign = TextAlign.Center
                    )
                )
            }
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = primary),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 24.dp),
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
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MyFavoriteQuestEmptyContentPreview() {
    MyFavoriteQuestEmptyContent(
        modifier = Modifier,
        onQuestNavButtonClick = {}
    )
}