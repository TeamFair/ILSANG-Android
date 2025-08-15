package com.ilsangtech.ilsang.feature.approval.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.primary100
import com.ilsangtech.ilsang.designsystem.theme.primary300

@Composable
internal fun ApprovalFeedbackButtonRow(
    modifier: Modifier = Modifier,
    isLiked: Boolean,
    isHated: Boolean,
    onLikeButtonClick: () -> Unit,
    onHateButtonClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ApprovalFeedbackButton(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(top = 11.dp, bottom = 9.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isHated) primary100 else gray100,
                contentColor = if (isHated) primary300 else gray300
            ),
            onClick = onHateButtonClick
        ) {
            Icon(
                modifier = Modifier
                    .padding(vertical = 3.dp)
                    .padding(start = 2.dp, end = 1.dp),
                painter = painterResource(R.drawable.thumbs_down),
                contentDescription = null
            )
        }

        ApprovalFeedbackButton(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(top = 9.dp, bottom = 11.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isLiked) primary else gray100,
                contentColor = if (isLiked) Color.White else gray300
            ),
            onClick = onLikeButtonClick
        ) {
            Icon(
                modifier = Modifier
                    .padding(vertical = 3.dp)
                    .padding(start = 1.dp, end = 2.dp),
                painter = painterResource(R.drawable.thumbs_up),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun ApprovalFeedbackButton(
    modifier: Modifier,
    onClick: () -> Unit,
    contentPadding: PaddingValues,
    colors: ButtonColors,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        content = content,
        contentPadding = contentPadding,
        colors = colors,
        shape = RoundedCornerShape(12.dp),
        onClick = onClick
    )
}

@Preview(showBackground = true)
@Composable
private fun ApprovalFeedbackButtonRowPreview() {
    ApprovalFeedbackButtonRow(
        isLiked = false,
        isHated = false,
        onLikeButtonClick = {},
        onHateButtonClick = {}
    )
}