package com.ilsangtech.ilsang.core.ui.zone

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.designsystem.component.ILSANGDialog
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily

@Composable
fun ZoneSelectDisabledDialog(
    onDismissRequest: () -> Unit
) {
    ILSANGDialog(
        modifier = Modifier.fillMaxWidth(),
        buttonText = "확인",
        onDismissRequest = onDismissRequest
    ) {
        Text(
            text = "내 일상존은 시즌중에는\n" +
                    "변경이 불가합니다.",
            style = TextStyle(
                fontFamily = pretendardFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun ZoneSelectDisabledDialogPreview() {
    ZoneSelectDisabledDialog(onDismissRequest = {})
}