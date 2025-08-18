package com.ilsangtech.ilsang.feature.ranking.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.subTitle01
import com.ilsangtech.ilsang.designsystem.theme.title01
import kotlinx.coroutines.delay
import java.util.Date
import java.util.Locale

@Composable
internal fun TimeRemainingCard(
    modifier: Modifier = Modifier,
    seasonNumber: Int,
    endDate: Date,
    onSeasonFinished: () -> Unit
) {
    var timeRemaining by remember {
        mutableLongStateOf(
            endDate.time - System.currentTimeMillis()
        )
    }

    LaunchedEffect(Unit) {
        while (timeRemaining > 0) {
            delay(1000L)
            timeRemaining -= 1000L
        }
        onSeasonFinished()
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = primary),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "시즌$seasonNumber 종료까지",
                style = subTitle01.copy(fontWeight = FontWeight.Normal),
                color = Color.White
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ProvideTextStyle(title01.copy(color = Color.White)) {
                    Text(text = "${timeRemaining / (1000 * 60 * 60 * 24)}일")
                    Text(
                        text =
                            String.format(
                                Locale.getDefault(),
                                "%02d",
                                timeRemaining / (1000 * 60 * 60) % 24
                            ) + ":" + String.format(
                                Locale.getDefault(),
                                "%02d",
                                timeRemaining / (1000 * 60) % 60
                            ) + ":" + String.format(
                                Locale.getDefault(),
                                "%02d",
                                timeRemaining / 1000 % 60
                            )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun TimeRemainingCardPreview() {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Text(
                text = "시즌 종료",
                color = Color.White
            )
        }
    }

    TimeRemainingCard(
        seasonNumber = 1,
        endDate = Date(System.currentTimeMillis() + 1000 * 5),
        onSeasonFinished = {
            showDialog = true
        }
    )
}
