package com.ilsangtech.ilsang.core.ui.season

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.component.IlsangCheckBox
import com.ilsangtech.ilsang.designsystem.theme.gmarketSansFontFamily
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.primary300
import com.ilsangtech.ilsang.designsystem.theme.primary500
import com.ilsangtech.ilsang.designsystem.theme.subTitle01
import com.ilsangtech.ilsang.designsystem.theme.tapRegularTextStyle
import com.ilsangtech.ilsang.designsystem.theme.toSp

@Composable
fun SeasonOpenDialog(
    seasonNumber: Int,
    startDate: String,
    endDate: String,
    onRankingButtonClick: () -> Unit,
    onDismissRequest: (Boolean) -> Unit
) {
    var checked by remember { mutableStateOf(false) }

    Dialog(
        properties = DialogProperties(dismissOnClickOutside = false),
        onDismissRequest = { onDismissRequest(checked) }
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = primary),
            shape = RoundedCornerShape(12.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 20.dp, start = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IlsangCheckBox(
                        modifier = Modifier
                            .size(25.dp)
                            .padding(4.17.dp),
                        checked = checked,
                        onClick = { checked = !checked }
                    )
                    Text(
                        modifier = Modifier.clickable(
                            indication = null,
                            interactionSource = null,
                            onClick = { checked = !checked }
                        ),
                        text = "다시 보지 않기",
                        style = tapRegularTextStyle.copy(
                            fontSize = 14.dp.toSp(),
                            lineHeight = 24.dp.toSp()
                        ),
                        color = gray100
                    )
                }
                Icon(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 24.dp, end = 24.dp)
                        .clickable(
                            indication = null,
                            interactionSource = null,
                            onClick = { onDismissRequest(checked) }
                        ),
                    painter = painterResource(R.drawable.icon_close),
                    tint = Color.White,
                    contentDescription = "닫기 버튼"
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 67.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "일상 시즌${seasonNumber} 오픈!",
                        style = TextStyle(
                            fontFamily = gmarketSansFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 36.dp.toSp(),
                            lineHeight = 1.3.em
                        ),
                        color = Color.White
                    )
                    Spacer(Modifier.height(7.dp))
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = primary500
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(vertical = 4.dp, horizontal = 10.dp),
                            text = "$startDate ~ $endDate",
                            style = subTitle01.copy(
                                fontSize = 16.dp.toSp(),
                                lineHeight = 24.dp.toSp()
                            ),
                            color = gray100
                        )
                    }
                    Spacer(Modifier.height(38.dp))
                    Icon(
                        modifier = Modifier
                            .size(130.dp)
                            .drawBehind {
                                drawOval(
                                    topLeft = Offset(
                                        x = (130 - 180).dp.toPx() / 2,
                                        y = -21.dp.toPx()
                                    ),
                                    size = Size(
                                        width = 180.dp.toPx(),
                                        height = 191.dp.toPx()
                                    ),
                                    color = primary300
                                )
                            },
                        painter = painterResource(R.drawable.icon_reward_box),
                        tint = Color.Unspecified,
                        contentDescription = null
                    )

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .padding(bottom = 24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = gray500
                        ),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(vertical = 16.dp),
                        onClick = {
                            onDismissRequest(checked)
                            onRankingButtonClick()
                        }
                    ) {
                        Text(
                            text = "시즌 랭킹 보러가기",
                            style = TextStyle(
                                fontFamily = pretendardFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.dp.toSp(),
                                lineHeight = 18.dp.toSp()
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
fun SeasonOpenDialogPreview() {
    SeasonOpenDialog(
        seasonNumber = 1,
        startDate = "2025.10.01",
        endDate = "2025.12.31",
        onRankingButtonClick = {},
        onDismissRequest = {}
    )
}

