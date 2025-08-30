package com.ilsangtech.ilsang.feature.iszone.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ilsangtech.ilsang.core.model.area.CommercialArea
import com.ilsangtech.ilsang.designsystem.component.ILSANGButton
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.primary500
import com.ilsangtech.ilsang.designsystem.theme.title02

@Composable
internal fun IsZoneConfirmDialog(
    selectedCommercialArea: CommercialArea,
    onConfirm: () -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 28.dp, bottom = 16.dp)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "한 번 선택한 일상존은 시즌중에는\n" +
                            "변경이 불가합니다.",
                    textAlign = TextAlign.Center,
                    style = title02,
                    color = Color.Red
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                fontFamily = pretendardFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 13.sp,
                                color = gray500
                            )
                        ) {
                            append("현재 선택된 일상존: ")
                            withStyle(SpanStyle(color = primary500)) {
                                append(selectedCommercialArea.areaName)
                            }
                        }
                    },
                    lineHeight = 16.sp
                )
                Spacer(Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ILSANGButton(
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = background,
                            contentColor = gray500
                        ),
                        text = "취소",
                        onClick = onDismissRequest
                    )
                    ILSANGButton(
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primary,
                            contentColor = Color.White
                        ),
                        text = "확인",
                        onClick = {
                            onConfirm()
                            onDismissRequest()
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun IsZoneConfirmDialogPreview() {
    IsZoneConfirmDialog(
        selectedCommercialArea = CommercialArea(
            code = "1001491",
            areaName = "명동",
            description = "명동 관광특구 주요 상권으로 의류/잡화 및 음식 업종이 고루 분포하며, 관광객 수요 풍부",
            metroAreaCode = "1000001",
            images = emptyList()
        ),
        onConfirm = {},
        onDismissRequest = {}
    )
}