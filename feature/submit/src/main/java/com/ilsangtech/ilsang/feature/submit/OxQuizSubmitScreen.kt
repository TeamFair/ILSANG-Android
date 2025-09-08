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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.core.model.reward.RewardPoint
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.feature.submit.component.InCorrectAnswerDialog
import com.ilsangtech.ilsang.feature.submit.component.OxQuizGuideCard
import com.ilsangtech.ilsang.feature.submit.component.OxQuizSubmitCard
import com.ilsangtech.ilsang.feature.submit.component.QuizQuestInfoCard
import com.ilsangtech.ilsang.feature.submit.component.QuizScreenHeader
import com.ilsangtech.ilsang.feature.submit.component.SubmitErrorDialog
import com.ilsangtech.ilsang.feature.submit.component.SubmitLoadingDialog
import com.ilsangtech.ilsang.feature.submit.component.SubmitSuccessDialog
import com.ilsangtech.ilsang.feature.submit.model.OxQuizSubmitUiState
import com.ilsangtech.ilsang.feature.submit.model.OxQuizUiState
import com.ilsangtech.ilsang.feature.submit.model.SubmitQuestUiState
import com.ilsangtech.ilsang.feature.submit.model.SubmitResultUiState

@Composable
internal fun OxQuizSubmitScreen(
    viewModel: OxQuizSubmitViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit
) {
    val quizUiState by viewModel.oxQuizUiState.collectAsStateWithLifecycle()
    val quizSubmitUiState by viewModel.quizSubmitUiState.collectAsStateWithLifecycle()
    val submitResultUiState by viewModel.submitResultUiState.collectAsStateWithLifecycle()

    when (val result = submitResultUiState) {
        is SubmitResultUiState.Loading -> {
            SubmitLoadingDialog()
        }

        is SubmitResultUiState.Success -> {
            SubmitSuccessDialog(
                rewardPoints = result.rewardPoints,
                onDismissRequest = {
                    viewModel.resetResultUiState()
                    onBackButtonClick()
                }
            )
        }

        is SubmitResultUiState.WrongAnswer -> {
            InCorrectAnswerDialog {
                viewModel.resetResultUiState()
            }
        }

        is SubmitResultUiState.Error -> {
            SubmitErrorDialog {
                viewModel.resetResultUiState()
            }
        }

        else -> {}
    }

    if (quizUiState is OxQuizUiState.Success) {
        val quizUiState = quizUiState as OxQuizUiState.Success
        OxQuizSubmitScreen(
            question = quizUiState.question,
            submitQuestUiState = quizUiState.submitQuestUiState,
            quizSubmitUiState = quizSubmitUiState,
            onCorrectButtonClick = {
                viewModel.updateSubmitState(OxQuizSubmitUiState.Correct)
            },
            onIncorrectButtonClick = {
                viewModel.updateSubmitState(OxQuizSubmitUiState.Incorrect)
            },
            onSubmitButtonClick = viewModel::submitMission,
            onBackButtonClick = onBackButtonClick
        )
    }
}

@Composable
private fun OxQuizSubmitScreen(
    question: String,
    submitQuestUiState: SubmitQuestUiState,
    quizSubmitUiState: OxQuizSubmitUiState,
    onCorrectButtonClick: () -> Unit,
    onIncorrectButtonClick: () -> Unit,
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
                        writerName = submitQuestUiState.writerName,
                        questType = submitQuestUiState.questType,
                        point = submitQuestUiState.rewards.sumOf { it.point }
                    )
                }
                item { Spacer(Modifier.height(16.dp)) }
                item { OxQuizGuideCard() }
                item { Spacer(Modifier.height(16.dp)) }
                item {
                    OxQuizSubmitCard(
                        question = question,
                        quizSubmitUiState = quizSubmitUiState,
                        onCorrectButtonClick = onCorrectButtonClick,
                        onIncorrectButtonClick = onIncorrectButtonClick
                    )

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
                        enabled = quizSubmitUiState != OxQuizSubmitUiState.NotSelected,
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
private fun OxQuizSubmitScreenPreview() {
    val question = "야미돈까스에서 파는 메뉴 중 ‘치킨까스' 는 케첩소스와 함께 제공된다."
    val submitQuestUiState = SubmitQuestUiState(
        questImageId = "sampleImageId",
        title = "정자동 최고의 돈까스 \n" +
                "가게 가기",
        writerName = "야미돈까스 정자동점",
        questType = QuestType.Normal,
        rewards = listOf(
            RewardPoint.Metro(5),
            RewardPoint.Commercial(10),
            RewardPoint.Contribute(15)
        )
    )
    val quizSubmitUiState = OxQuizSubmitUiState.NotSelected

    OxQuizSubmitScreen(
        question = question,
        submitQuestUiState = submitQuestUiState,
        quizSubmitUiState = quizSubmitUiState,
        onCorrectButtonClick = {},
        onIncorrectButtonClick = {},
        onSubmitButtonClick = {},
        onBackButtonClick = {}
    )
}