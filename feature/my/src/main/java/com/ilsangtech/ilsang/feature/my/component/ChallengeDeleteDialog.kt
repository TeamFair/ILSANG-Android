package com.ilsangtech.ilsang.feature.my.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ilsangtech.ilsang.designsystem.component.ILSANGDialog

@Composable
internal fun ChallengeDeleteDialog(
    onDeleteButtonClick: () -> Unit = {},
    onDismissRequest: () -> Unit = {}
) {
    ILSANGDialog(
        title = "챌린지를 삭제 할까요?",
        content = "삭제하면 복구가 불가합니다",
        positiveButtonText = "확인",
        negativeButtonText = "취소",
        onClickPositiveButton = onDeleteButtonClick,
        onClickNegativeButton = onDismissRequest,
        onDismissRequest = onDismissRequest
    )
}

@Preview
@Composable
private fun ChallengeDeleteDialogPreview() {
    ChallengeDeleteDialog()
}