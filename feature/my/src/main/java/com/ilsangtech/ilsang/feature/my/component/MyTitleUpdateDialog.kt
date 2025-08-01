package com.ilsangtech.ilsang.feature.my.component

import androidx.compose.runtime.Composable
import com.ilsangtech.ilsang.designsystem.component.ILSANGDialog

@Composable
internal fun MyTitleUpdateDialog(
    onUpdateButtonClick: () -> Unit,
    onDismissRequest: () -> Unit
) {
    ILSANGDialog(
        title = "변경 사항을 저장하시겠습니까?",
        content = "취소하면 내용이 저장되지 않습니다.",
        onClickPositiveButton = {
            onUpdateButtonClick()
            onDismissRequest()
        },
        onClickNegativeButton = onDismissRequest,
        onDismissRequest = onDismissRequest,
        positiveButtonText = "저장",
        negativeButtonText = "취소"
    )
}