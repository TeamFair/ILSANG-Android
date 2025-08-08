package com.ilsangtech.ilsang.feature.myzone.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ilsangtech.ilsang.designsystem.component.ILSANGDialog

@Composable
internal fun MyZoneSuccessDialog(
    onDismissRequest: () -> Unit
) {
    ILSANGDialog(
        modifier = Modifier.fillMaxWidth(),
        title = "일상존이 설정되었습니다.",
        content = "퀘스트를 수행하러 가 볼까요?",
        buttonText = "확인",
        onDismissRequest = onDismissRequest,
        onClickButton = onDismissRequest
    )

}

@Preview
@Composable
private fun MyZoneSuccessDialogPreview() {
    MyZoneSuccessDialog {}
}