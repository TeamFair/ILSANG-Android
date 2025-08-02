package com.ilsangtech.ilsang.feature.my.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ilsangtech.ilsang.designsystem.component.ILSANGDialog

@Composable
internal fun UserImageDeleteDialog(
    onDeleteButtonClick: () -> Unit,
    onDismissRequest: () -> Unit
) {
    ILSANGDialog(
        title = "프로필 사진 삭제을 삭제하시겠습니까?",
        content = "기본 사진으로 변경됩니다.",
        positiveButtonText = "확인",
        negativeButtonText = "취소",
        onClickPositiveButton = {
            onDismissRequest()
            onDeleteButtonClick()
        },
        onClickNegativeButton = onDismissRequest,
        onDismissRequest = onDismissRequest
    )
}

@Preview
@Composable
private fun UserImageDeleteDialogPreview() {
    UserImageDeleteDialog(
        onDeleteButtonClick = {},
        onDismissRequest = {}
    )
}