package com.ilsangtech.ilsang.core.ui.quest.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.ui.BuildConfig
import com.ilsangtech.ilsang.designsystem.theme.badge01TextStyle
import com.ilsangtech.ilsang.designsystem.theme.badge02TextStyle
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.toSp

@Composable
internal fun QuestApprovalExample(
    modifier: Modifier = Modifier,
    imageIds: List<String?>,
    onImageClick: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            QuestBottomSheetTextChip(text = "퀘스트 인증 예시")
            if (imageIds.size == 3) {
                Text(
                    text = "예시 사진은 실제 유저들의 사진입니다",
                    style = badge01TextStyle.copy(
                        fontSize = 11.dp.toSp(),
                        lineHeight = 12.dp.toSp()
                    ),
                    color = gray300
                )
            }
        }
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            imageIds.forEach { imageId ->
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
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
                            contentScale = ContentScale.Crop,
                            contentDescription = "퀘스트 인증 예시 이미지",
                        )
                    }
                }
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
            imageIds = List(3) { null },
            onImageClick = {}
        )
    }
}