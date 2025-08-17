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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.ilsangtech.ilsang.feature.submit.component.OxQuizGuideCard
import com.ilsangtech.ilsang.feature.submit.component.OxQuizSubmitCard
import com.ilsangtech.ilsang.feature.submit.component.QuizQuestInfoCard
import com.ilsangtech.ilsang.feature.submit.component.QuizScreenHeader
import com.ilsangtech.ilsang.feature.submit.component.WordsQuizGuideCard
import com.ilsangtech.ilsang.feature.submit.component.WordsQuizSubmitCard

@Composable
private fun QuizScreen(
    submitQuestUiState: SubmitQuestUiState,
    quizUiState: QuizUiState,
    onBackButtonClick: () -> Unit,
    onCorrectButtonClick: () -> Unit,
    onIncorrectButtonClick: () -> Unit,
    onSubmitButtonClick: () -> Unit
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
                item {
                    if (quizUiState is QuizUiState.WordsQuizUiState) {
                        WordsQuizGuideCard()
                    } else {
                        OxQuizGuideCard()
                    }
                }
                item { Spacer(Modifier.height(16.dp)) }
                item {
                    if (quizUiState is QuizUiState.OxQuizUiState) {
                        OxQuizSubmitCard(
                            oxQuizUiState = quizUiState,
                            onCorrectButtonClick = onCorrectButtonClick,
                            onIncorrectButtonClick = onIncorrectButtonClick
                        )
                    } else {
                        WordsQuizSubmitCard(
                            wordsQuizUiState = quizUiState as QuizUiState.WordsQuizUiState
                        )
                    }
                }
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
                        enabled = (quizUiState is QuizUiState.OxQuizUiState &&
                                quizUiState.submitState !is OxQuizSubmitUiState.NotSelected) ||
                                (quizUiState is QuizUiState.WordsQuizUiState &&
                                        quizUiState.answer.text.isNotEmpty()),
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
private fun OxQuizScreenPreview() {
    val submitQuestUiState = SubmitQuestUiState(
        questImageId = "",
        title = "정자동 최고의 돈까스 가게 가기",
        locationName = "야미돈까스 정자동점",
        questType = NewQuestType.Event,
        point = 10
    )

    var quizUiState by remember {
        mutableStateOf(
            QuizUiState.OxQuizUiState(
                question = "야미돈까스에서 파는 메뉴 중 ‘치킨까스' 는 케첩소스와 함께 제공된다.",
                submitState = OxQuizSubmitUiState.NotSelected
            )
        )
    }
    QuizScreen(
        submitQuestUiState = submitQuestUiState,
        quizUiState = quizUiState,
        onBackButtonClick = {},
        onCorrectButtonClick = {
            quizUiState = quizUiState.copy(
                submitState = OxQuizSubmitUiState.Correct
            )
        },
        onIncorrectButtonClick = {
            quizUiState = quizUiState.copy(
                submitState = OxQuizSubmitUiState.Incorrect
            )
        },
        onSubmitButtonClick = {}
    )
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
        question = "야미돈까스 정자동점의 베스트셀러 메뉴는?",
        hint = "등심",
        answer = answerTextFieldState
    )
    QuizScreen(
        submitQuestUiState = submitQuestUiState,
        quizUiState = quizUiState,
        onBackButtonClick = {},
        onCorrectButtonClick = {},
        onIncorrectButtonClick = {},
        onSubmitButtonClick = {}
    )
}