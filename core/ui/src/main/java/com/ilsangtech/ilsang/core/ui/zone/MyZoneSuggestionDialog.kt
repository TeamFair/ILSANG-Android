package com.ilsangtech.ilsang.core.ui.zone

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.designsystem.component.ILSANGDialog
import com.ilsangtech.ilsang.designsystem.component.IlsangCheckBox
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.tapRegularTextStyle

@Composable
fun MyZoneSuggestionDialog(
    onConfirm: () -> Unit,
    onCancel: (checked: Boolean) -> Unit,
    onDismissRequest: () -> Unit
) {
    var checked by remember { mutableStateOf(false) }

    ILSANGDialog(
        positiveButtonText = "일상존 선택하기",
        negativeButtonText = "취소",
        onDismissRequest = onDismissRequest,
        onPositiveButtonClick = onConfirm,
        onNegativeButtonClick = { onCancel(checked) }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "일상존이 선택되지 않았어요",
                style = TextStyle(
                    fontFamily = pretendardFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    lineHeight = 18.sp,
                    color = Color.Black
                )
            )
            Text(
                text = "일상존을 선택하고 퀘스트를 수행하면\n" +
                        "기여도 포인트를 2배나 받을 수 있어요!",
                style = TextStyle(
                    fontFamily = pretendardFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp,
                    lineHeight = 20.sp,
                    color = gray500
                )
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IlsangCheckBox(
                    modifier = Modifier
                        .size(24.dp)
                        .padding(4.dp),
                    checked = checked,
                    onClick = { checked = !checked }
                )
                Text(
                    text = "다시보지 않기",
                    style = tapRegularTextStyle,
                    color = gray500
                )
            }
        }
    }
}

@Preview
@Composable
private fun MyZoneSuggestionDialogPreview() {
    MyZoneSuggestionDialog(
        onConfirm = {},
        onCancel = {},
        onDismissRequest = {}
    )
}