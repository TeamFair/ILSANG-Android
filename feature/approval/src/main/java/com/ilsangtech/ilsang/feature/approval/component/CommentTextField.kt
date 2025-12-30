package com.ilsangtech.ilsang.feature.approval.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.designsystem.theme.buttonTextStyle
import com.ilsangtech.ilsang.designsystem.theme.caption01
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.tapBoldTextStyle
import com.ilsangtech.ilsang.designsystem.theme.toSp

@Composable
internal fun CommentTextField(
    modifier: Modifier = Modifier,
    textFieldState: TextFieldState,
    onButtonClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .drawBehind {
                drawRect(color = Color.White)
                drawLine(
                    color = gray100,
                    strokeWidth = 1.dp.toPx(),
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f)
                )
            }
            .padding(vertical = 11.dp)
            .padding(horizontal = 20.dp)
            .navigationBarsPadding(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            modifier = Modifier.weight(1f),
            state = textFieldState,
            textStyle = caption01.copy(
                fontSize = 13.dp.toSp(),
                lineHeight = 20.dp.toSp()
            ),
            lineLimits = TextFieldLineLimits.MultiLine(maxHeightInLines = 3),
            decorator = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(gray100)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(vertical = 15.dp, horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            innerTextField()
                        }
                        Text(
                            text = "${textFieldState.text.length}/300",
                            style = tapBoldTextStyle.copy(
                                fontSize = 14.dp.toSp(),
                                lineHeight = 24.dp.toSp()
                            ),
                            color = gray300
                        )
                    }
                }
            }
        )
        Button(
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primary,
                disabledContainerColor = gray300
            ),
            contentPadding = PaddingValues(
                horizontal = 21.dp,
                vertical = 16.dp
            ),
            onClick = onButtonClick
        ) {
            Text(
                text = "등록",
                style = buttonTextStyle.copy(
                    fontSize = 16.dp.toSp(),
                    lineHeight = 18.dp.toSp()
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CommentTextFieldPreview() {
    val textFieldState = rememberTextFieldState()
    CommentTextField(textFieldState = textFieldState) { }
}