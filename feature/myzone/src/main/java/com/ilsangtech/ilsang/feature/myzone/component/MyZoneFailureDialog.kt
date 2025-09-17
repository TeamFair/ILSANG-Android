package com.ilsangtech.ilsang.feature.myzone.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.component.ILSANGDialog
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.title02

@Composable
internal fun MyZoneFailureDialog(
    onDismissRequest: () -> Unit
) {
    ILSANGDialog(
        modifier = Modifier.fillMaxWidth(),
        buttonText = "확인",
        onDismissRequest = onDismissRequest
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_caution),
                tint = Color.Unspecified,
                contentDescription = null
            )
            Text(
                text = "지역 변경에 실패했습니다",
                style = title02,
                color = Color.Black
            )
            Text(
                text = "다시 한번 시도해 주세요.",
                style = TextStyle(
                    fontFamily = pretendardFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                ),
                color = gray500
            )
        }
    }
}

@Preview
@Composable
private fun MyZoneFailureDialogPreview() {
    MyZoneFailureDialog {}
}