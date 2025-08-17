package com.ilsangtech.ilsang.feature.submit.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.designsystem.theme.caption01
import com.ilsangtech.ilsang.designsystem.theme.heading01
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.subTitle02

@Composable
internal fun OxQuizGuideCard(modifier: Modifier = Modifier) {
    QuizGuideCard(
        modifier = modifier,
        title = "OX 퀘스트 인증 참여 방법",
        steps =
            listOf(
                "내용을 읽고, O/X 중 정답을 선택해 주세요.",
                "‘퀘스트 인증하기' 버튼을 눌러 인증을 해 주세요.",
                "정답일 경우 보상을 받을 수 있어요."
            )
    )
}

@Composable
internal fun WordsQuizGuideCard(modifier: Modifier = Modifier) {
    QuizGuideCard(
        modifier = modifier,
        title = "서술형 퀘스트 인증 참여 방법",
        steps = listOf(
            "내용을 읽고, 정답을 입력해 주세요.",
            "‘퀘스트 인증하기' 버튼을 눌러 인증을 해 주세요.",
            "정답일 경우 보상을 받을 수 있어요."
        )
    )
}

@Composable
private fun QuizGuideCard(
    modifier: Modifier = Modifier,
    title: String,
    steps: List<String>
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 20.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = title,
                style = heading01,
                color = Color.Black
            )
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                steps.forEachIndexed { index, step ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .background(primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${index + 1}",
                                style = caption01,
                                color = Color.White
                            )
                        }
                        Text(
                            text = step,
                            style = subTitle02,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun OxQuizGuideCardPreview() {
    OxQuizGuideCard()
}

@Preview
@Composable
private fun WordsQuizGuideCardPreview() {
    WordsQuizGuideCard()
}
