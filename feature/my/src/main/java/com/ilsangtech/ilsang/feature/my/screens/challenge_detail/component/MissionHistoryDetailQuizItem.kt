package com.ilsangtech.ilsang.feature.my.screens.challenge_detail.component

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.core.model.mission.MissionType
import com.ilsangtech.ilsang.core.model.quiz.CompletedQuiz
import com.ilsangtech.ilsang.designsystem.theme.caption01
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.heading02
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.subTitle02
import com.ilsangtech.ilsang.designsystem.theme.title01
import com.ilsangtech.ilsang.designsystem.theme.toSp

@Composable
internal fun MissionHistoryDetailQuizItem(
    modifier: Modifier = Modifier,
    missionType: MissionType,
    completedQuiz: CompletedQuiz
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
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
                                lineHeight = 24.dp.toSp(),
                                color = Color.White
                            )
                        )
                    }
                    Text(
                        text = "QUIZ",
                        style = title01.copy(color = primary)
                    )
                }
                Text(
                    text = completedQuiz.question.orEmpty(),
                    style = subTitle02
                )
            }
            if (missionType == MissionType.Ox) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Surface(
                        modifier = Modifier
                            .weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        color = if (completedQuiz.userAnswer == "O") primary else gray100
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp),
                            text = "O",
                            style = title01.copy(
                                fontSize = 23.dp.toSp(),
                                lineHeight = 24.dp.toSp()
                            ),
                            color = if (completedQuiz.userAnswer == "O") Color.White else Color.Black,
                            textAlign = TextAlign.Center
                        )
                    }
                    Surface(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        color = if (completedQuiz.userAnswer == "X") primary else gray100
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp),
                            text = "X",
                            style = title01.copy(
                                fontSize = 23.dp.toSp(),
                                lineHeight = 24.dp.toSp()
                            ),
                            color = if (completedQuiz.userAnswer == "X") Color.White else Color.Black,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "내 정답",
                        style = heading02.copy(color = primary)
                    )
                    Text(
                        text = completedQuiz.userAnswer ?: "없음",
                        style = subTitle02
                    )
                }
            }

            Text(
                text = "정답: ${completedQuiz.answer ?: "없음"}",
                style = caption01.copy(color = primary)
            )
        }
    }
}

@Composable
@Preview(name = "OX Quiz", showBackground = true)
private fun MissionHistoryDetailQuizItemOxPreview() {
    MissionHistoryDetailQuizItem(
        missionType = MissionType.Ox,
        completedQuiz = CompletedQuiz(
            id = 1,
            question = "일상은 2024년 2월에 런칭되었다.",
            userAnswer = "O",
            answer = "O"
        )
    )
}

@Composable
@Preview(name = "Words Quiz", showBackground = true)
private fun MissionHistoryDetailQuizItemWordsPreview() {
    MissionHistoryDetailQuizItem(
        missionType = MissionType.Words,
        completedQuiz = CompletedQuiz(
            id = 1,
            question = "일상의 영어 이름은 무엇일까요?",
            userAnswer = "ilsang",
            answer = "ilsang"
        )
    )
}