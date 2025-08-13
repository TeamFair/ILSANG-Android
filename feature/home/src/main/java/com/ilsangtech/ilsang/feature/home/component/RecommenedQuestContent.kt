package com.ilsangtech.ilsang.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.ilsangtech.ilsang.core.model.Quest
import com.ilsangtech.ilsang.core.model.quest.RecommendedQuest
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_regular
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_semibold
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.feature.home.BuildConfig

@Composable
fun RecommendedQuestsContent(
    userNickname: String?,
    recommendedQuests: List<Quest>,
    onRecommendedQuestClick: (Quest) -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Text(
            text = if (userNickname == null) "추천 퀘스트" else "$userNickname 님을 위한 추천 퀘스트",
            style = recommendedQuestsContentTitleStyle
        )
        Spacer(Modifier.height(12.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(recommendedQuests) { quest ->
                RecommendedQuestView(
                    quest = quest,
                    onCardClick = { onRecommendedQuestClick(quest) }
                )
            }
        }
    }
}

@Composable
internal fun RecommendQuestCard(
    quest: RecommendedQuest,
    onCardClick: () -> Unit
) {
    Card(
        modifier = Modifier.size(
            width = 152.dp,
            height = 172.dp
        ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        onClick = onCardClick
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = quest.title,
                style = recommendedQuestTitleStyle,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = quest.writerName,
                style = recommendedQuestWriterStyle,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Spacer(Modifier.weight(1f))
            AsyncImage(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(recommendedQuestImageColor),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(BuildConfig.IMAGE_URL + quest.imageId)
                    .build(),
                contentDescription = quest.title,
            )
        }
    }
}

@Preview
@Composable
private fun RecommendQuestCardPreview() {
    val quest = RecommendedQuest(
        questId = 1,
        imageId = "sample_image_id",
        mainImageId = "sample_main_image_id",
        title = "Sample Quest Title",
        writerName = "Sample Writer"
    )
    RecommendQuestCard(quest = quest, onCardClick = {})
}

@Composable
fun RecommendedQuestView(
    quest: Quest,
    onCardClick: () -> Unit
) {
    Card(
        modifier = Modifier.size(
            width = 152.dp,
            height = 172.dp
        ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        onClick = onCardClick
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = quest.missionTitle,
                style = recommendedQuestTitleStyle,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = quest.writer,
                style = recommendedQuestWriterStyle,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Spacer(Modifier.weight(1f))
            AsyncImage(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(recommendedQuestImageColor),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(BuildConfig.IMAGE_URL + quest.imageId)
                    .build(),
                contentDescription = quest.missionTitle,
            )

        }
    }
}

private val recommendedQuestsContentTitleStyle = TextStyle(
    fontSize = 19.sp,
    lineHeight = 22.sp,
    fontFamily = FontFamily(Font(pretendard_semibold))
)

private val recommendedQuestImageColor = Color(0xFFF1F5FF)

private val recommendedQuestTitleStyle = TextStyle(
    fontSize = 15.sp,
    lineHeight = 20.sp,
    letterSpacing = (-0.4).sp,
    fontFamily = FontFamily(Font(pretendard_semibold)),
    lineBreak = LineBreak.Heading
)

private val recommendedQuestWriterStyle = TextStyle(
    fontSize = 12.sp,
    lineHeight = 16.sp,
    letterSpacing = (-0.3).sp,
    color = gray400,
    fontFamily = FontFamily(Font(pretendard_regular))
)