package com.ilsangtech.ilsang.feature.my.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ilsangtech.ilsang.designsystem.component.ILSANGDialog


@Composable
fun EditNicknameCancelDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    ILSANGDialog(
        modifier = modifier.fillMaxWidth(),
        title = "닉네임 변경을 취소할까요?",
        content = "변경을 완료하지 않으면\n프로필이 저장되지 않습니다.",
        positiveButtonText = "확인",
        negativeButtonText = "취소",
        onClickPositiveButton = onConfirm,
        onClickNegativeButton = onCancel,
        onDismissRequest = onDismissRequest,
    )
}

@Preview
@Composable
fun EditNicknameCancelDialogPreview() {
    EditNicknameCancelDialog(
        onDismissRequest = {},
        onConfirm = {},
        onCancel = {}
    )
}