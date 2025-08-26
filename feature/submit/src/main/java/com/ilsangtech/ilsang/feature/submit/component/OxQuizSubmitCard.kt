package com.ilsangtech.ilsang.feature.submit.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.subTitle02
import com.ilsangtech.ilsang.designsystem.theme.title01
import com.ilsangtech.ilsang.designsystem.theme.toSp
import com.ilsangtech.ilsang.feature.submit.OxQuizSubmitUiState
import com.ilsangtech.ilsang.feature.submit.QuizUiState

@Composable
internal fun OxQuizSubmitCard(
    modifier: Modifier = Modifier,
    oxQuizUiState: QuizUiState.OxQuizUiState,
    onCorrectButtonClick: () -> Unit,
    onIncorrectButtonClick: () -> Unit,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(
                vertical = 20.dp,
                horizontal = 16.dp
            )
        ) {
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
                text = oxQuizUiState.question,
                style = subTitle02,
                color = Color.Black
            )
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OxQuizButton(
                    modifier = Modifier.weight(1f),
                    text = "O",
                    isSelected = oxQuizUiState.submitState is OxQuizSubmitUiState.Correct,
                    onClick = onCorrectButtonClick
                )
                OxQuizButton(
                    modifier = Modifier.weight(1f),
                    text = "X",
                    isSelected = oxQuizUiState.submitState is OxQuizSubmitUiState.Incorrect,
                    onClick = onIncorrectButtonClick
                )
            }
        }
    }
}

@Composable
private fun OxQuizButton(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) primary else gray100,
            contentColor = if (isSelected) Color.White else Color.Black
        ),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(vertical = 20.dp),
        onClick = onClick
    ) {
        Text(
            text = text,
            style = title01
        )
    }
}

@Preview
@Composable
private fun OxQuizSubmitCardPreview() {
    var oxQuizUiState by remember {
        mutableStateOf(
            QuizUiState.OxQuizUiState(
                quizId = 0,
                question = "야미돈까스에서 파는 메뉴 중 ‘치킨까스' 는 케첩소스와 함께 제공된다.",
                submitState = OxQuizSubmitUiState.NotSelected
            )
        )
    }
    OxQuizSubmitCard(
        oxQuizUiState = oxQuizUiState,
        onCorrectButtonClick = {
            if (oxQuizUiState.submitState == OxQuizSubmitUiState.Correct) {
                oxQuizUiState = oxQuizUiState.copy(
                    submitState = OxQuizSubmitUiState.NotSelected
                )
            } else {
                oxQuizUiState = oxQuizUiState.copy(
                    submitState = OxQuizSubmitUiState.Correct
                )
            }
        },
        onIncorrectButtonClick = {
            if (oxQuizUiState.submitState == OxQuizSubmitUiState.Incorrect) {
                oxQuizUiState = oxQuizUiState.copy(
                    submitState = OxQuizSubmitUiState.NotSelected
                )
            } else {
                oxQuizUiState = oxQuizUiState.copy(
                    submitState = OxQuizSubmitUiState.Incorrect
                )
            }
        }
    )
}