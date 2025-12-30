package com.ilsangtech.ilsang.feature.approval.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.designsystem.theme.caption02
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.primary100
import com.ilsangtech.ilsang.designsystem.theme.toSp

@Composable
internal fun RecommentProgressCard(
    modifier: Modifier = Modifier,
    commentWriterName: String,
    onCancelButtonClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(30.dp)
            .background(primary100)
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${commentWriterName}님께 답글 남기는 중",
            style = caption02.copy(
                fontSize = 12.dp.toSp(),
                lineHeight = 16.dp.toSp()
            ),
            color = primary
        )
        Text(
            modifier = Modifier.clickable(onClick = onCancelButtonClick),
            text = "취소",
            style = caption02.copy(
                fontSize = 12.dp.toSp(),
                lineHeight = 16.dp.toSp()
            ),
            color = gray400
        )
    }
}

@Preview
@Composable
private fun RecommentProgressCardPreview() {
    RecommentProgressCard(commentWriterName = "일상 123") { }
}