package com.ilsangtech.ilsang.feature.submit.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_bold
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_regular
import com.ilsangtech.ilsang.designsystem.theme.buttonTextStyle
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.primary

@Composable
fun SubmitErrorDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = modifier,
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        top = 30.dp,
                        bottom = 16.dp
                    )
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(
                            color = Color(0x1AFF7171),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.submit_error_icon),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
                Spacer(Modifier.height(15.dp))
                Text(
                    text = "제출에 실패했어요",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(pretendard_bold)),
                        fontSize = 17.sp,
                        lineHeight = 20.sp,
                        color = gray500
                    )
                )
                Spacer(Modifier.height(9.dp))
                Text(
                    text = "다시 시도해보세요\uD83E\uDD72",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(pretendard_regular)),
                        fontSize = 15.sp,
                        lineHeight = 20.sp,
                        color = gray400
                    )
                )
                Spacer(Modifier.height(20.dp))
                Button(
                    modifier = Modifier.width(228.dp),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primary,
                        contentColor = Color.White
                    ),
                    onClick = onDismissRequest
                ) {
                    Text(
                        text = "확인",
                        style = buttonTextStyle
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SubmitErrorDialogPreview() {
    SubmitErrorDialog {}
}