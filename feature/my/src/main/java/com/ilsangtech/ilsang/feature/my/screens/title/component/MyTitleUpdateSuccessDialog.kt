package com.ilsangtech.ilsang.feature.my.screens.title.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ilsangtech.ilsang.designsystem.component.ILSANGDialog

@Composable
internal fun MyTitleUpdateSuccessDialog(
    onDismissRequest: () -> Unit
) {
    ILSANGDialog(
        title = "칭호가 설정되었습니다",
        content = "퀘스트를 수행하고 다른 칭호도 획득해 보세요!",
        buttonText = "확인",
        onDismissRequest = onDismissRequest
    )
}

@Preview
@Composable
private fun MyTitleUpdateSuccessDialogPreview() {
    MyTitleUpdateSuccessDialog(onDismissRequest = {})
}

