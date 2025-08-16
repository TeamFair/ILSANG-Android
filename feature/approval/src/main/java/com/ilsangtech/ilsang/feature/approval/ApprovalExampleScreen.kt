package com.ilsangtech.ilsang.feature.approval

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.feature.approval.component.ApprovalExampleHeader

@Composable
internal fun ApprovalExampleScreen(
    approvalExampleViewModel: ApprovalExampleViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit
) {
    ApprovalExampleScreen(
        onBackButtonClick = onBackButtonClick
    )
}

@Composable
private fun ApprovalExampleScreen(
    onBackButtonClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            ApprovalExampleHeader(onBackButtonClick = onBackButtonClick)
        }
    }
}

@Preview
@Composable
private fun ApprovalExampleScreenPreview() {
    ApprovalExampleScreen(onBackButtonClick = {})
}