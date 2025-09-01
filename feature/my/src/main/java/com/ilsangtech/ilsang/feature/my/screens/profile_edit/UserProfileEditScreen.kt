package com.ilsangtech.ilsang.feature.my.screens.profile_edit

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ilsangtech.ilsang.feature.my.BuildConfig
import com.ilsangtech.ilsang.feature.my.screens.profile_edit.component.EditButton
import com.ilsangtech.ilsang.feature.my.screens.profile_edit.component.EditNicknameCancelDialog
import com.ilsangtech.ilsang.feature.my.screens.profile_edit.component.NicknameEditContent
import com.ilsangtech.ilsang.feature.my.screens.profile_edit.component.ProfileEditHeader
import com.ilsangtech.ilsang.feature.my.screens.profile_edit.component.UserImageEditContent

@Composable
fun UserProfileEditScreen(
    viewModel: UserProfileEditViewModel = hiltViewModel(),
    navigateToMyTabMain: () -> Unit
) {
    val originNickname = viewModel.originNickname
    val profileImageId = viewModel.profileImageId

    val nickname by viewModel.editNickname.collectAsStateWithLifecycle()
    val nicknameEditErrorMessage by viewModel.nicknameEditErrorMessage.collectAsStateWithLifecycle()
    val isUserProfileEditSuccess by viewModel.isUserProfileEditSuccess.collectAsStateWithLifecycle()
    var showCancelDialog by remember { mutableStateOf(false) }

    LaunchedEffect(isUserProfileEditSuccess) {
        if (isUserProfileEditSuccess != null) {
            navigateToMyTabMain()
            viewModel.resetUserProfileEditSuccess()
        }
    }

    BackHandler { showCancelDialog = true }
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

    UserProfileEditScreen(
        originNickname = originNickname,
        nickname = nickname,
        imageId = profileImageId,
        nicknameEditErrorMessage = nicknameEditErrorMessage,
        onNicknameChange = viewModel::changeNickname,
        onEditButtonClick = viewModel::updateUserProfile,
        onDeleteUserImage = viewModel::deleteUserProfileImage,
        onBackButtonClick = { showCancelDialog = true }
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
    onBackButtonClick: () -> Unit
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
            ProfileEditHeader(onBackButtonClick = onBackButtonClick)
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
        onBackButtonClick = {}
    )
}