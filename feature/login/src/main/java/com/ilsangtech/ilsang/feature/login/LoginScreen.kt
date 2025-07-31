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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.secondary
import com.ilsangtech.ilsang.designsystem.theme.title01
import com.ilsangtech.ilsang.designsystem.theme.toSp

@Composable
fun LoginScreen(login: () -> Unit) {
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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoginTitle()
                Spacer(Modifier.height(48.dp))
                AutoSlidePager(
                    pageList = listOf(
                        { NightQuestCard() },
                        { LunchQuestCard() },
                        { CoffeeQuestCard() },
                        { EmojiQuestCard() },
                        { TakeoutQuestCard() }
                    )
                )
            }

            Spacer(Modifier.weight(1f))
            LoginButton(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 106.dp),
                onClick = login
            )
        }
    }
}

@Composable
fun LoginTitle() {
    Text(
        text = buildAnnotatedString {
            withStyle(
                SpanStyle(
                    fontSize = 23.dp.toSp(),
                    fontWeight = title01.fontWeight,
                    fontStyle = title01.fontStyle,
                    fontFamily = title01.fontFamily,
                    color = title01.color
                )
            ) {
                withStyle(SpanStyle(color = primary)) {
                    append("일")
                }
                withStyle(SpanStyle(color = secondary)) {
                    append("상")
                }
                append("의 작은 행동이,\n지역을 바꿉니다")
            }
        },
        textAlign = TextAlign.Center
    )
}

@Composable
fun LoginButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = background),
        contentPadding = PaddingValues(
            horizontal = 12.dp,
            vertical = 10.dp
        ),
        shape = RoundedCornerShape(300.dp),
        border = BorderStroke(
            width = 1.dp,
            color = gray100
        ),
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(id = R.drawable.google_logo),
            tint = Color.Unspecified,
            contentDescription = null
        )
        Spacer(Modifier.width(16.dp))
        Text(
            text = "sign in with google",
            color = Color.Black,
            style = loginButtonTextStyle.copy(
                fontSize = 14.dp.toSp(),
                lineHeight = 20.dp.toSp()
            )
        )
    }
}

private val loginButtonTextStyle = TextStyle(
    color = Color.Black,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    lineHeight = 20.sp
)

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen {}
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