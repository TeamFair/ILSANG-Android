package com.ilsangtech.ilsang.feature.approval

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ilsangtech.ilsang.designsystem.theme.buttonTextStyle
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.subTitle02
import com.ilsangtech.ilsang.designsystem.theme.title01
import com.ilsangtech.ilsang.feature.approval.component.ApprovalReportHeader
import com.ilsangtech.ilsang.feature.approval.component.ReportCheckBoxItem
import com.ilsangtech.ilsang.feature.approval.component.ReportResultDialog
import com.ilsangtech.ilsang.feature.approval.model.ReportResultUiState
import com.ilsangtech.ilsang.feature.approval.model.ReportTypeUiModel

@Composable
internal fun ReportScreen(
    viewModel: ReportViewModel = hiltViewModel(),
    popBackStack: () -> Unit
) {
    val selectedReportTypes by viewModel.selectedReportTypes.collectAsStateWithLifecycle()
    val reportResultUiState by viewModel.reportResultUiState.collectAsStateWithLifecycle()

    ReportScreen(
        selectedReportTypes = selectedReportTypes,
        reportResultUiState = reportResultUiState,
        onReportTypeSelected = viewModel::updateSelectedReportTypes,
        onBackButtonClick = popBackStack,
        onReportButtonClick = viewModel::report
    )
}

@Composable
private fun ReportScreen(
    selectedReportTypes: List<ReportTypeUiModel>,
    reportResultUiState: ReportResultUiState,
    onReportTypeSelected: (ReportTypeUiModel) -> Unit,
    onBackButtonClick: () -> Unit,
    onReportButtonClick: () -> Unit
) {
    if (reportResultUiState !is ReportResultUiState.UnReported) {
        ReportResultDialog(
            reportResult = reportResultUiState,
            onDismissRequest = onBackButtonClick
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column {
            ApprovalReportHeader(onBackButtonClick = onBackButtonClick)
            Column(
                modifier = Modifier
                    .padding(top = 36.dp, bottom = 8.dp)
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    text = "신고 사유를 선택해 주세요",
                    style = title01
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "신고하신 내용은 운영자가 24~48시간 내 검토 후 조치합니다. " +
                            "허위 신고 시 서비스 이용이 제한될 수 있습니다.",
                    style = subTitle02,
                    color = gray500,
                )
                Spacer(Modifier.height(36.dp))
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ReportTypeUiModel.entries.forEach { reportType ->
                        ReportCheckBoxItem(
                            reportType = reportType,
                            isSelected = selectedReportTypes.contains(reportType),
                            onClick = { onReportTypeSelected(reportType) }
                        )
                    }
                }
                Spacer(Modifier.weight(1f))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primary,
                        disabledContainerColor = gray300,
                        contentColor = Color.White,
                        disabledContentColor = Color.White
                    ),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    onClick = onReportButtonClick
                ) {
                    Text(
                        text = "신고하기",
                        style = buttonTextStyle
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ReportScreenPreview() {
    ReportScreen(
        selectedReportTypes = listOf(ReportTypeUiModel.ABUSE),
        reportResultUiState = ReportResultUiState.UnReported,
        onReportTypeSelected = {},
        onBackButtonClick = {},
        onReportButtonClick = {}
    )
}