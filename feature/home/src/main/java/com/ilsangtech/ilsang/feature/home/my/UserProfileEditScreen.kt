package com.ilsangtech.ilsang.feature.home.my

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.ilsangtech.ilsang.feature.home.BuildConfig
import com.ilsangtech.ilsang.feature.home.HomeViewModel
import com.ilsangtech.ilsang.feature.home.my.component.NicknameEditContent
import com.ilsangtech.ilsang.feature.home.my.component.NicknameEditHeader
import com.ilsangtech.ilsang.feature.home.my.component.UserImageEditContent

@Composable
fun UserProfileEditScreen(
    homeViewModel: HomeViewModel,
    navigateToMyTabMain: () -> Unit
) {
    val originUserInfo by homeViewModel.userInfo.collectAsStateWithLifecycle()
    val nickname by homeViewModel.editNickname.collectAsStateWithLifecycle()
    val nicknameEditErrorMessage by homeViewModel.nicknameEditErrorMessage.collectAsStateWithLifecycle()
    val isUserProfileEditSuccess by homeViewModel.isUserProfileEditSuccess.collectAsStateWithLifecycle()

    LaunchedEffect(isUserProfileEditSuccess) {
        if (isUserProfileEditSuccess != null) {
            navigateToMyTabMain()
            homeViewModel.resetUserProfileEditSuccess()
        }
    }

    UserProfileEditScreen(
        originNickname = originUserInfo?.nickname ?: "",
        nickname = nickname,
        imageId = originUserInfo?.profileImage,
        nicknameEditErrorMessage = nicknameEditErrorMessage,
        onNicknameChange = homeViewModel::changeNickname,
        onEditButtonClick = homeViewModel::updateUserProfile,
        onDeleteUserImage = homeViewModel::deleteUserProfileImage,
        navigateToMyTabMain = {
            navigateToMyTabMain()
            homeViewModel.resetUserProfileEditSuccess()
        }
    )
}

@Composable
fun UserProfileEditScreen(
    originNickname: String,
    nickname: String,
    imageId: String?,
    nicknameEditErrorMessage: String?,
    onNicknameChange: (String) -> Unit,
    onEditButtonClick: (Uri?) -> Unit,
    onDeleteUserImage: () -> Unit,
    navigateToMyTabMain: () -> Unit
) {
    var updatedImage by remember { mutableStateOf<Uri?>(null) }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NicknameEditHeader(navigateToMyTabMain)
            Spacer(Modifier.height(35.dp))
            UserImageEditContent(
                model = updatedImage ?: (BuildConfig.IMAGE_URL + imageId),
                onSelectImage = { updatedImage = it },
                onDeleteImage = onDeleteUserImage
            )
            Spacer(Modifier.height(36.dp))
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
                enabled = (originNickname != nickname && nicknameEditErrorMessage == null) || updatedImage != null,
                onClick = { onEditButtonClick(updatedImage) }
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
fun UserProfileEditScreenPreview() {
    UserProfileEditScreen(
        originNickname = "닉네임",
        nickname = "닉네임",
        imageId = null,
        nicknameEditErrorMessage = null,
        onNicknameChange = {},
        onEditButtonClick = {},
        onDeleteUserImage = {},
        navigateToMyTabMain = {}
    )
}