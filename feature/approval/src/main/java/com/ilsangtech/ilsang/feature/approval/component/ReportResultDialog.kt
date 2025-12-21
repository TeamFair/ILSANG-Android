package com.ilsangtech.ilsang.feature.approval.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.buttonTextStyle
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.heading02
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.feature.approval.model.ReportResultUiState

@Composable
internal fun ReportResultDialog(
    modifier: Modifier = Modifier,
    reportResult: ReportResultUiState,
    onDismissRequest: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val dialogWidth = configuration.screenWidthDp.dp - 40.dp
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = modifier.width(dialogWidth),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "신고하기",
                        style = heading02
                    )
                    Icon(
                        modifier = Modifier.clickable(
                            onClick = onDismissRequest,
                            indication = null,
                            interactionSource = null
                        ),
                        painter = painterResource(R.drawable.icon_close),
                        tint = gray500,
                        contentDescription = null
                    )
                }
                Spacer(Modifier.height(54.dp))
                Column(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_syren),
                        tint = Color.Unspecified,
                        contentDescription = null
                    )
                    Text(
                        text = if (reportResult is ReportResultUiState.Success) {
                            "신고가 접수되었습니다\n감사합니다"
                        } else {
                            "이미 신고하신 콘텐츠입니다.\n현재 검토 중에 있습니다."
                        },
                        style = TextStyle(
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 23.sp,
                            letterSpacing = (-0.4).sp
                        ),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(Modifier.height(66.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = primary),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    onClick = onDismissRequest
                ) {
                    Text(
                        text = "확인",
                        style = buttonTextStyle
                    )
                }
            }
        }
    }
}


@Preview(name = "Report Success")
@Composable
private fun ReportResultDialogSuccessPreview() {
    ReportResultDialog(
        reportResult = ReportResultUiState.Success,
        onDismissRequest = {}
    )
}

@Preview(name = "Report Reported")
@Composable
private fun ReportResultDialogReportedPreview() {
    ReportResultDialog(
        reportResult = ReportResultUiState.Reported,
        onDismissRequest = {}
    )
}