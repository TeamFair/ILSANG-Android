package com.ilsangtech.ilsang.feature.submit.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.core.ui.quest.EventQuestTypeBadge
import com.ilsangtech.ilsang.core.ui.quest.RepeatQuestTypeBadge
import com.ilsangtech.ilsang.designsystem.theme.bodyTextStyle
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.heading01
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.primary100
import com.ilsangtech.ilsang.designsystem.theme.title02
import com.ilsangtech.ilsang.feature.submit.BuildConfig

@Composable
internal fun QuizQuestInfoCard(
    modifier: Modifier = Modifier,
    questImageId: String?,
    title: String,
    writerName: String,
    questType: QuestType,
    point: Int
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 20.dp,
                    horizontal = 16.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF1F5FF)),
                model = BuildConfig.IMAGE_URL + questImageId,
                contentDescription = null
            )
            Spacer(Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = writerName,
                    style = bodyTextStyle,
                    color = gray500,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = title,
                    style = title02,
                    color = gray500,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                if (questType is QuestType.Event) {
                    EventQuestTypeBadge()
                } else if (questType is QuestType.Repeat) {
                    RepeatQuestTypeBadge(repeatType = questType)
                }
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(primary100)
                    .padding(10.dp)
            ) {
                Text(
                    text = "${point}P",
                    style = heading01,
                    color = primary
                )
            }
        }
    }
}

@Preview
@Composable
private fun QuizQuestInfoCardPreview() {
    QuizQuestInfoCard(
        modifier = Modifier,
        questImageId = "some_image_id",
        title = "정자동 최고의 돈까스 가게 가기",
        writerName = "야미돈까스 정자동점",
        questType = QuestType.Repeat.Daily,
        point = 100
    )
}
