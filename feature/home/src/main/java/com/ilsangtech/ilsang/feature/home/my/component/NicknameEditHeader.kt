package com.ilsangtech.ilsang.feature.home.my.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_bold
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.feature.home.R

@Composable
fun NicknameEditHeader(navigateToMyTabMain: () -> Unit) {
    var showCancelDialog by remember { mutableStateOf(false) }

    if (showCancelDialog) {
        EditNicknameCancelDialog(
            onDismissRequest = { showCancelDialog = false },
            onConfirm = {
                showCancelDialog = false
                navigateToMyTabMain()
            },
            onCancel = { showCancelDialog = false }
        )
    }

    Box(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    color = Color(0xFFDDDDDD),
                    strokeWidth = 0.5.dp.toPx(),
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height)
                )
            }
            .padding(vertical = 12.dp)
    ) {
        Icon(
            modifier = Modifier
                .padding(start = 15.dp)
                .clickable(
                    onClick = { showCancelDialog = true },
                    interactionSource = null,
                    indication = null
                ),
            painter = painterResource(R.drawable.back),
            tint = gray500,
            contentDescription = "뒤로가기"
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "정보 수정",
            style = nicknameEditHeaderTextStyle
        )
    }
}

private val nicknameEditHeaderTextStyle = TextStyle(
    fontFamily = FontFamily(Font(pretendard_bold)),
    fontSize = 17.sp,
    lineHeight = 22.sp,
    color = gray500
)

@Preview(showBackground = true)
@Composable
fun NicknameEditHeaderPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        NicknameEditHeader {}
    }
}