package com.ilsangtech.ilsang.core.ui.quest

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.core.model.reward.RewardPoint
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily

@Composable
fun DefaultQuestContent(
    modifier: Modifier = Modifier,
    title: String,
    writer: String,
    rewardPoints: List<RewardPoint>,
    questImage: @Composable () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        questImage()
        Spacer(Modifier.width(20.dp))
        Column {
            Text(
                text = title,
                maxLines = 1,
                style = defaultQuestContentTitleStyle,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = writer,
                maxLines = 1,
                style = defaultQuestContentWriterStyle,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(4.dp))
            RewardPointChips(rewardPoints)
        }
    }
}

private val defaultQuestContentTitleStyle = TextStyle(
    fontSize = 15.sp,
    lineHeight = 20.sp,
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.SemiBold
)

private val defaultQuestContentWriterStyle = TextStyle(
    fontSize = 11.sp,
    lineHeight = 16.sp,
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.Normal,
    color = gray400
)

@Preview(showBackground = true)
@Composable
fun DefaultQuestContentPreview2() {
    val title = "퀘스트 타이틀 타이틀"
    val writer = "퀘스트 작성자 작성자"
    val rewardPoints = listOf(
        RewardPoint.Metro(2),
        RewardPoint.Commercial(5),
        RewardPoint.Contribute(10)
    )

    DefaultQuestContent(
        title = title,
        writer = writer,
        rewardPoints = rewardPoints,
        questImage = {
            DefaultQuestImage(
                imageId = "sample_image_id",
                contentDescription = title
            )
        }
    )
}
