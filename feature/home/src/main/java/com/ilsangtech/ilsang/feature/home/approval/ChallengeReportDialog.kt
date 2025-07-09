package com.ilsangtech.ilsang.feature.home.approval

import androidx.compose.runtime.Composable
import com.ilsangtech.ilsang.designsystem.component.ILSANGDialog

@Composable
internal fun ChallengeReportDialog(
    onReportButtonClick: () -> Unit,
    onDismissRequest: () -> Unit
) {
    ILSANGDialog(
        title = "신고하시겠습니까?",
        content = "확인 후 빠른 시일 내 조치하도록 하겠습니다",
        onDismissRequest = onDismissRequest,
        negativeButtonText = "취소",
        positiveButtonText = "확인",
        onClickNegativeButton = onDismissRequest,
        onClickPositiveButton = onReportButtonClick
    )
}