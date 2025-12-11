package com.ilsangtech.ilsang.feature.approval.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.designsystem.component.IlsangCheckBox
import com.ilsangtech.ilsang.designsystem.theme.subTitle02
import com.ilsangtech.ilsang.feature.approval.model.ReportTypeUiModel

@Composable
internal fun ReportCheckBoxItem(
    modifier: Modifier = Modifier,
    reportType: ReportTypeUiModel,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        IlsangCheckBox(
            modifier = Modifier
                .padding(5.dp)
                .size(20.dp),
            checked = isSelected,
            onClick = onClick
        )
        Text(
            text = reportType.toString(),
            style = subTitle02
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ReportCheckBoxItemPreview() {
    ReportCheckBoxItem(
        reportType = ReportTypeUiModel.ABUSE,
        isSelected = true,
        onClick = {}
    )
}
