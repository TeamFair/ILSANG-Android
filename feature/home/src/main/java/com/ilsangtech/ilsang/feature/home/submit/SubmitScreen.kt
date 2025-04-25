package com.ilsangtech.ilsang.feature.home.submit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.designsystem.theme.buttonTextStyle
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.primary100
import com.ilsangtech.ilsang.designsystem.theme.title02
import com.ilsangtech.ilsang.feature.home.R

@Composable
fun SubmitScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        SubmitScreenHeader { }
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            model = null,
            contentDescription = null
        )
        SubmitScreenFooter()
    }
}


@Composable
fun SubmitScreenHeader(
    onBackButtonClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 12.dp)
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 15.dp)
                .clickable(
                    indication = null,
                    interactionSource = null,
                    onClick = onBackButtonClick
                ),
            painter = painterResource(R.drawable.back),
            contentDescription = "뒤로가기"
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "퀘스트 인증하기",
            style = title02,
            color = gray500
        )
    }
}

@Composable
fun SubmitScreenFooter() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = 24.dp,
                    topEnd = 24.dp
                )
            )
            .background(Color.White)
            .padding(
                top = 18.dp,
                bottom = 8.dp
            )
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(13.dp)
    ) {
        Button(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primary100,
                contentColor = primary
            ),
            shape = RoundedCornerShape(12.dp),
            onClick = {}
        ) {
            Text(
                text = "다시 찍기",
                style = buttonTextStyle
            )
        }
        Button(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primary,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            onClick = {}
        ) {
            Text(
                text = "제출하기",
                style = buttonTextStyle
            )
        }
    }
}

@Preview
@Composable
fun SubmitScreenPreview() {
    SubmitScreen()
}