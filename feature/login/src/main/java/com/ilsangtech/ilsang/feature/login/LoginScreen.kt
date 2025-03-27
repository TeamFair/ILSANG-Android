package com.ilsangtech.ilsang.feature.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.secondary
import com.ilsangtech.ilsang.designsystem.theme.title01

@Composable
fun LoginScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        color = Color.White
    ) {}
}

@Composable
fun LoginTitle() {
    Text(
        text = buildAnnotatedString {
            withStyle(
                SpanStyle(
                    fontSize = title01.fontSize,
                    fontWeight = title01.fontWeight,
                    fontStyle = title01.fontStyle,
                    fontFamily = title01.fontFamily,
                    color = title01.color
                )
            ) {
                append("특별한 하루를 위한\n작은 도전,")
                withStyle(SpanStyle(color = primary)) {
                    append("일")
                }
                withStyle(SpanStyle(color = secondary)) {
                    append("상")
                }
                append("!")
            }
        },
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}

@Preview(showBackground = true)
@Composable
fun LoginTitlePreview() {
    LoginTitle()
}