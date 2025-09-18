package com.ilsangtech.ilsang.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.caption01
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.primary

private val ilsangDialogRadius = 24.dp

@Composable
fun ILSANGDialog(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    buttonText: String,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Card(
            modifier = modifier,
            colors = CardDefaults.cardColors(
                containerColor = background
            ),
            shape = RoundedCornerShape(ilsangDialogRadius)
        ) {
            Column(
                modifier = modifier
                    .padding(
                        top = 28.dp,
                        bottom = 16.dp
                    )
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        lineHeight = 18.sp,
                        letterSpacing = 0.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                    )
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = content,
                    textAlign = TextAlign.Center,
                    style = caption01,
                    color = gray500
                )
                Spacer(Modifier.height(20.dp))

                ILSANGButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = buttonText,
                    onClick = onDismissRequest,
                    colors = ButtonDefaults.buttonColors(containerColor = primary)
                )
            }
        }
    }
}

@Composable
fun ILSANGDialog(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    positiveButtonText: String,
    negativeButtonText: String,
    onClickPositiveButton: () -> Unit,
    onClickNegativeButton: () -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Card(
            modifier = modifier,
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(ilsangDialogRadius)
        ) {
            Column(
                modifier = modifier
                    .padding(
                        top = 28.dp,
                        bottom = 16.dp
                    )
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        lineHeight = 18.sp,
                        letterSpacing = 0.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                    )
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = content,
                    textAlign = TextAlign.Center,
                    style = caption01,
                    color = gray500
                )
                Spacer(Modifier.height(20.dp))

                Row {
                    ILSANGButton(
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = background,
                            contentColor = gray500
                        ),
                        text = negativeButtonText,
                        onClick = onClickNegativeButton
                    )
                    Spacer(Modifier.width(10.dp))
                    ILSANGButton(
                        modifier = Modifier.weight(1f),
                        text = positiveButtonText,
                        onClick = onClickPositiveButton,
                        colors = ButtonDefaults.buttonColors(containerColor = primary)
                    )
                }
            }
        }
    }
}

@Composable
fun ILSANGDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    buttonText: String,
    content: @Composable () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = modifier,
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(ilsangDialogRadius)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 28.dp, bottom = 16.dp)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content()
                Spacer(Modifier.height(20.dp))
                ILSANGButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = buttonText,
                    onClick = onDismissRequest,
                    colors = ButtonDefaults.buttonColors(containerColor = primary)
                )
            }
        }
    }
}

@Composable
fun ILSANGDialog(
    modifier: Modifier = Modifier,
    positiveButtonText: String,
    negativeButtonText: String,
    onDismissRequest: () -> Unit,
    onPositiveButtonClick: (() -> Unit)? = null,
    onNegativeButtonClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = modifier,
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(ilsangDialogRadius)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 28.dp, bottom = 16.dp)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content()
                Spacer(Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ILSANGButton(
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = background,
                            contentColor = gray500
                        ),
                        text = negativeButtonText,
                        onClick = {
                            onNegativeButtonClick?.invoke()
                            onDismissRequest()
                        }
                    )
                    ILSANGButton(
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primary,
                            contentColor = Color.White
                        ),
                        text = positiveButtonText,
                        onClick = {
                            onPositiveButtonClick?.invoke()
                            onDismissRequest()
                        }
                    )
                }
            }
        }
    }
}

@Preview("ILSANGDialog1")
@Composable
fun ILSANGDialogPreview() {
    ILSANGDialog(
        modifier = Modifier,
        title = "알럿 타이틀을 입력하세요",
        content = "내용을 입력하세요내용을 입력하세요내용을 입력하세요내용을 입력하세요",
        buttonText = "확인",
        onDismissRequest = {}
    )
}

@Preview("ILSANGDialog2")
@Composable
fun ILSANGDialogPreview2() {
    ILSANGDialog(
        modifier = Modifier,
        title = "알럿 타이틀을 입력하세요",
        content = "내용을 입력하세요내용을 입력하세요내용을 입력하세요내용을 입력하세요",
        positiveButtonText = "확인",
        negativeButtonText = "취소",
        onClickPositiveButton = {},
        onClickNegativeButton = {},
        onDismissRequest = {}
    )
}

@Preview("ILSANGDialog3")
@Composable
fun ILSANGDialogPreview3() {
    ILSANGDialog(
        onDismissRequest = {},
        buttonText = "확인"
    ) {
        Text(
            text = "커스텀 컨텐츠를 입력하세요",
            style = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                lineHeight = 18.sp,
                letterSpacing = 0.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
            )
        )
    }
}

@Preview("ILSANGDialog4")
@Composable
fun ILSANGDialogPreview4() {
    ILSANGDialog(
        positiveButtonText = "확인",
        negativeButtonText = "취소",
        onDismissRequest = {},
        onPositiveButtonClick = {},
        onNegativeButtonClick = {}
    ) {
        Text(
            text = "커스텀 컨텐츠를 입력하세요",
            style = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                lineHeight = 18.sp,
                letterSpacing = 0.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
            )
        )
    }
}