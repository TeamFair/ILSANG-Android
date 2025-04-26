package com.ilsangtech.ilsang.feature.home.my

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_semibold
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.feature.home.HomeViewModel
import com.ilsangtech.ilsang.feature.home.my.component.NicknameEditContent
import com.ilsangtech.ilsang.feature.home.my.component.NicknameEditHeader

@Composable
fun NicknameEditScreen(
    homeViewModel: HomeViewModel,
    navigateToMyTabMain: () -> Unit
) {
    val originUserInfo by homeViewModel.userInfo.collectAsStateWithLifecycle()
    val nickname by homeViewModel.editNickname.collectAsStateWithLifecycle()
    val nicknameEditErrorMessage by homeViewModel.nicknameEditErrorMessage.collectAsStateWithLifecycle()
    val isNicknameEditSuccess by homeViewModel.isNicknameEditSuccess.collectAsStateWithLifecycle()

    LaunchedEffect(isNicknameEditSuccess) {
        if (isNicknameEditSuccess == true) {
            navigateToMyTabMain()
            homeViewModel.clearNicknameEditResult()
        }
    }
    NicknameEditScreen(
        originNickname = originUserInfo?.nickname ?: "",
        nickname = nickname,
        nicknameEditErrorMessage = nicknameEditErrorMessage,
        onNicknameChange = homeViewModel::changeNickname,
        onEditButtonClick = homeViewModel::updateNickname,
        navigateToMyTabMain = {
            navigateToMyTabMain()
            homeViewModel.clearNicknameEditResult()
        }
    )
}

@Composable
fun NicknameEditScreen(
    originNickname: String,
    nickname: String,
    nicknameEditErrorMessage: String?,
    onNicknameChange: (String) -> Unit,
    onEditButtonClick: () -> Unit,
    navigateToMyTabMain: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            NicknameEditHeader(navigateToMyTabMain)
            NicknameEditContent(
                nickname = nickname,
                nicknameEditErrorMessage = nicknameEditErrorMessage,
                onNicknameChange = onNicknameChange
            )
            Spacer(Modifier.weight(1f))
            EditButton(
                modifier = Modifier
                    .imePadding()
                    .padding(horizontal = 20.dp),
                enabled = originNickname != nickname && nicknameEditErrorMessage == null,
                onClick = onEditButtonClick
            )
        }
    }
}

@Composable
fun EditButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = gray300,
            containerColor = primary,
            disabledContentColor = Color.White
        ),
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        Text(
            text = "변경 완료",
            style = editButtonTextStyle
        )
    }
}

private val editButtonTextStyle = TextStyle(
    fontSize = 16.sp,
    lineHeight = 18.sp,
    fontFamily = FontFamily(Font(pretendard_semibold)),
)

@Preview(showBackground = true)
@Composable
fun NicknameEditScreenPreview() {
    NicknameEditScreen(
        originNickname = "닉네임",
        nickname = "닉네임",
        nicknameEditErrorMessage = null,
        onNicknameChange = {},
        onEditButtonClick = {},
        navigateToMyTabMain = {}
    )
}