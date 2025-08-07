package com.ilsangtech.ilsang.core.ui.quest.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.ui.BuildConfig
import com.ilsangtech.ilsang.designsystem.theme.badge02TextStyle
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray300

@Composable
internal fun QuestApprovalExample(
    modifier: Modifier = Modifier,
    imageId: String?,
    onImageClick: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(start = 16.dp)
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        QuestBottomSheetTextChip(text = "퀘스트 인증 예시")
        Surface(
            modifier = Modifier
                .size(101.dp)
                .clip(RoundedCornerShape(12.dp)),
            onClick = onImageClick
        ) {
            if (imageId == null) {
                Box(
                    modifier = Modifier.background(gray100),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "아직 퀘스트를\n수행하지 않았어요!",
                        style = badge02TextStyle,
                        color = gray300,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                AsyncImage(
                    model = BuildConfig.IMAGE_URL + imageId,
                    contentDescription = "퀘스트 인증 예시 이미지",
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun QuestApprovalExamplePreview() {
    Row {
        QuestApprovalExample(
            modifier = Modifier.weight(1f),
            imageId = null,
            onImageClick = {}
        )
    }
}