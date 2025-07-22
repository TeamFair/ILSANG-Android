package com.ilsangtech.ilsang.feature.my.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_bold
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_medium
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.errorColor
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.primary

@Composable
fun NicknameEditContent(
    modifier: Modifier = Modifier,
    nickname: String,
    nicknameEditErrorMessage: String?,
    onNicknameChange: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp)
    ) {
        Text(
            text = "새로운 닉네임을 입력하세요",
            style = nicknameEditContentTitleTextStyle
        )
        Spacer(Modifier.height(10.dp))
        NicknameEditTextField(
            text = nickname,
            onTextChange = onNicknameChange,
            errorMessage = nicknameEditErrorMessage
        )
    }
}

@Composable
fun NicknameEditTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    errorMessage: String? = null
) {
    Column {
        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = text,
            onValueChange = onTextChange,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = background,
                unfocusedContainerColor = background,
                errorContainerColor = background,
                disabledBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                errorBorderColor = errorColor,
                cursorColor = primary,
                errorCursorColor = primary,
            ),
            textStyle = nicknameEditTextFieldTextStyle.copy(color = gray500),
            shape = RoundedCornerShape(12.dp),
            placeholder = {
                Text(
                    text = "새로운 닉네임을 입력하세요",
                    style = nicknameEditTextFieldTextStyle.copy(color = gray300)
                )
            },
            singleLine = true,
            isError = errorMessage != null
        )

        if (errorMessage != null) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 4.dp,
                        end = 10.dp,
                        top = 10.dp
                    ),
                text = errorMessage,
                style = nicknameEditErrorTextStyle
            )
        }
    }
}

private val nicknameEditContentTitleTextStyle = TextStyle(
    fontSize = 17.sp,
    lineHeight = 22.sp,
    fontFamily = FontFamily(Font(pretendard_bold)),
    color = gray500
)

private val nicknameEditTextFieldTextStyle = TextStyle(
    fontSize = 16.sp,
    lineHeight = 22.sp,
    fontFamily = FontFamily(Font(pretendard_bold))
)

private val nicknameEditErrorTextStyle = TextStyle(
    fontSize = 14.sp,
    lineHeight = 20.sp,
    fontFamily = FontFamily(Font(pretendard_medium)),
    color = errorColor
)

@Preview(showBackground = true)
@Composable
fun NicknameEditContentPreview() {
    var nickname by remember { mutableStateOf("") }
    NicknameEditContent(
        nickname = nickname,
        nicknameEditErrorMessage = null,
        onNicknameChange = { nickname = it }
    )
}