package com.ilsangtech.ilsang.feature.approval.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ilsangtech.ilsang.designsystem.component.ILSANGDialog
import com.ilsangtech.ilsang.feature.approval.model.CommentAlertUiState

@Composable
internal fun CommentAlertDialog(
    modifier: Modifier = Modifier,
    uiState: CommentAlertUiState,
    onDismissRequest: () -> Unit
) {
    ILSANGDialog(
        modifier = modifier,
        title = "댓글 입력",
        content = uiState.toString(),
        buttonText = "확인",
        onDismissRequest = onDismissRequest
    )
}

@Preview
@Composable
private fun CommentAlertDialogPreview() {
    CommentAlertDialog(uiState = CommentAlertUiState.TooLong) { }
}