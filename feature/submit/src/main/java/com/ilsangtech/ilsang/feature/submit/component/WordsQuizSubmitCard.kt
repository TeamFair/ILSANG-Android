package com.ilsangtech.ilsang.feature.submit.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldState
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
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.heading02
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.subTitle02
import com.ilsangtech.ilsang.designsystem.theme.title01
import com.ilsangtech.ilsang.designsystem.theme.toSp
import com.ilsangtech.ilsang.feature.submit.QuizUiState

@Composable
internal fun WordsQuizSubmitCard(
    modifier: Modifier = Modifier,
    wordsQuizUiState: QuizUiState.WordsQuizUiState
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 24.dp,
                    horizontal = 16.dp
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(45.dp)
                            .clip(CircleShape)
                            .background(primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Q",
                            style = title01.copy(
                                fontSize = 23.dp.toSp(),
                                lineHeight = 24.dp.toSp()
                            ),
                            color = Color.White
                        )
                    }
                    Text(
                        text = "QUIZ",
                        style = title01,
                        color = primary
                    )
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    text = wordsQuizUiState.question,
                    style = subTitle02,
                    color = Color.Black
                )
            }
            Column {
                Text(
                    text = "힌트",
                    style = heading02,
                    color = primary
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = wordsQuizUiState.hint,
                    style = subTitle02,
                    color = gray300
                )
            }
            BasicTextField(
                modifier = Modifier.fillMaxWidth(),
                state = wordsQuizUiState.answer,
                textStyle = caption01.copy(color = Color.Black),
                decorator = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(gray100)
                            .padding(
                                vertical = 15.dp,
                                horizontal = 16.dp
                            )
                    ) {
                        if (wordsQuizUiState.answer.text.isEmpty()) {
                            Text(
                                text = "정답을 입력해 주세요.",
                                style = caption01,
                                color = gray300
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }
    }
}

@Preview
@Composable
private fun WordsQuizSubmitCardPreview() {
    WordsQuizSubmitCard(
        wordsQuizUiState = QuizUiState.WordsQuizUiState(
            question = "야미돈까스 정자동점의 베스트셀러 메뉴는?",
            hint = "등심",
            answer = TextFieldState(initialText = "등심 돈까스")
        )
    )
}