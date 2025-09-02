package com.ilsangtech.ilsang.designsystem.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.toSp

private val ilsangButtonRadius = 12.dp

@Composable
fun ILSANGButton(
    modifier: Modifier,
    colors: ButtonColors? = null,
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(ilsangButtonRadius),
        colors = colors ?: ButtonDefaults.buttonColors(),
        onClick = onClick
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 14.dp.toSp(),
                lineHeight = 24.dp.toSp(),
                letterSpacing = 0.dp.toSp(),
                fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
            )
        )
    }
}

@Preview
@Composable
fun ILSANGButtonPreview() {
    ILSANGButton(
        modifier = Modifier,
        text = "확인",
        onClick = {}
    )
}