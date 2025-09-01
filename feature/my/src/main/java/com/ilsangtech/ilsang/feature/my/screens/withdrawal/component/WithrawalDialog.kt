package com.ilsangtech.ilsang.feature.my.screens.withdrawal.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.toSp

@Composable
internal fun WithdrawalDialog(
    onDismissRequest: () -> Unit,
    onWithdrawalButtonClick: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        top = 28.dp,
                        bottom = 16.dp
                    )
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "정말 탈퇴하시겠어요?",
                    style = TextStyle(
                        fontFamily = pretendardFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.dp.toSp()
                    )
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "확인 시 일상 계정이 영구적으로 삭제되며,\n" +
                            "모든 데이터는 복구가 불가능합니다.",
                    style = TextStyle(
                        fontFamily = pretendardFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 13.dp.toSp(),
                        color = gray500
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(20.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        modifier = Modifier.width(125.dp),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(vertical = 9.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = background),
                        onClick = onDismissRequest
                    ) {
                        Text(
                            text = "취소",
                            style = TextStyle(
                                fontFamily = pretendardFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.dp.toSp(),
                                color = gray500
                            )
                        )
                    }
                    Button(
                        modifier = Modifier.width(125.dp),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = primary),
                        onClick = onWithdrawalButtonClick
                    ) {
                        Text(
                            text = "확인",
                            style = TextStyle(
                                fontFamily = pretendardFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.dp.toSp(),
                                color = Color.White
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
private fun WithdrawalDialogPreview() {
    WithdrawalDialog(
        onDismissRequest = {},
        onWithdrawalButtonClick = {}
    )
}