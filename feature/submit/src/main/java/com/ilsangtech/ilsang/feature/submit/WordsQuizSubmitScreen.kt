package com.ilsangtech.ilsang.feature.submit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.core.model.NewQuestType
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.feature.submit.component.QuizQuestInfoCard
import com.ilsangtech.ilsang.feature.submit.component.QuizScreenHeader
import com.ilsangtech.ilsang.feature.submit.component.WordsQuizGuideCard
import com.ilsangtech.ilsang.feature.submit.component.WordsQuizSubmitCard

@Composable
private fun WordsQuizSubmitScreen(
    submitQuestUiState: SubmitQuestUiState,
    quizUiState: QuizUiState.WordsQuizUiState,
    onSubmitButtonClick: () -> Unit,
    onBackButtonClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = background
    ) {
        Column {
            QuizScreenHeader(onBackButtonClick = onBackButtonClick)
            LazyColumn(contentPadding = PaddingValues(horizontal = 20.dp)) {
                item { Spacer(Modifier.height(30.dp)) }
                item {
                    QuizQuestInfoCard(
                        questImageId = submitQuestUiState.questImageId,
                        title = submitQuestUiState.title,
                        locationName = submitQuestUiState.locationName,
                        questType = submitQuestUiState.questType,
                        point = submitQuestUiState.point
                    )
                }
                item { Spacer(Modifier.height(16.dp)) }
                item { WordsQuizGuideCard() }
                item { Spacer(Modifier.height(16.dp)) }
                item { WordsQuizSubmitCard(wordsQuizUiState = quizUiState) }
                item { Spacer(Modifier.height(40.dp)) }
                item {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 72.dp),
                        contentPadding = PaddingValues(vertical = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primary,
                            disabledContainerColor = gray300,
                            contentColor = Color.White,
                            disabledContentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        enabled = quizUiState.answer.text.isNotEmpty(),
                        onClick = onSubmitButtonClick
                    ) {
                        Text(
                            text = "퀘스트 인증하기",
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
    }
}

@Preview
@Composable
private fun WordsQuizScreenPreview() {
    val answerTextFieldState = rememberTextFieldState()
    val submitQuestUiState = SubmitQuestUiState(
        questImageId = "",
        title = "정자동 최고의 돈까스 가게 가기",
        locationName = "야미돈까스 정자동점",
        questType = NewQuestType.Event,
        point = 10
    )

    val quizUiState = QuizUiState.WordsQuizUiState(
        quizId = 0,
        question = "야미돈까스 정자동점의 베스트셀러 메뉴는?",
        hint = "등심",
        answer = answerTextFieldState
    )

    WordsQuizSubmitScreen(
        submitQuestUiState = submitQuestUiState,
        quizUiState = quizUiState,
        onSubmitButtonClick = {},
        onBackButtonClick = {}
    )
}