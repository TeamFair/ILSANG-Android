package com.ilsangtech.ilsang.feature.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_bold
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.gray100
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
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(Modifier.weight(1f))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                LoginTitle()
                Spacer(Modifier.height(28.dp))
                AutoSlidePager(
                    pageList = listOf(
                        { WaterQuestCard() },
                        { MusicQuestCard() },
                        { WeatherQuestCard() },
                        { CoffeeQuestCard() },
                        { EggQuestCard() },
                        { WaterQuestCard() },
                        { MusicQuestCard() }
                    )
                )
            }

            Spacer(Modifier.weight(1f))
            LoginButton { }
            Spacer(Modifier.height(38.dp))
        }
    }
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
                append("특별한 하루를 위한\n작은 도전, ")
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

@Composable
fun LoginButton(login: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = background,
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 18.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = gray100
        ),
        onClick = login
    ) {
        Icon(
            painter = painterResource(id = R.drawable.google_logo),
            tint = Color.Unspecified,
            contentDescription = null
        )
        Spacer(Modifier.width(16.dp))
        Text(
            text = "Google로 로그인하기",
            color = Color.Black,
            style = loginButtonTextStyle
        )
    }
}

private val loginButtonTextStyle = TextStyle(
    color = Color.Black,
    fontSize = 15.sp,
    fontFamily = FontFamily(Font(pretendard_bold)),
    lineHeight = 24.sp,
)

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

@Preview(showBackground = true)
@Composable
fun LoginButtonPreview() {
    LoginButton {}
}