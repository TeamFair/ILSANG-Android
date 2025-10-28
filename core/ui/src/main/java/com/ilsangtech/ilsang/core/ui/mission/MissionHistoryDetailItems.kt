package com.ilsangtech.ilsang.core.ui.mission

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.model.mission.MissionType
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.core.ui.BuildConfig
import com.ilsangtech.ilsang.core.ui.quest.EventQuestTypeBadge
import com.ilsangtech.ilsang.core.ui.quest.MissionTypeBadge
import com.ilsangtech.ilsang.core.ui.quest.RepeatQuestTypeBadge
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.heading02
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.subTitle02
import com.ilsangtech.ilsang.designsystem.theme.tapBoldTextStyle
import com.ilsangtech.ilsang.designsystem.theme.title01
import com.ilsangtech.ilsang.designsystem.theme.title02

@Composable
fun MissionHistoryDetailPhotoItem(
    modifier: Modifier = Modifier,
    imageId: String?
) {
    AsyncImage(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        model = BuildConfig.IMAGE_URL + imageId,
        contentScale = ContentScale.Crop,
        contentDescription = "퀘스트 수행 이미지"
    )
}


@Composable
fun MissionHistoryDetailInfoItem(
    modifier: Modifier = Modifier,
    title: String,
    questType: QuestType,
    missionType: MissionType,
    likeCount: Int,
    createdAt: String,
) {
    val (year, month, day) = createdAt.split('.')
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Color.White
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "${year}년 ${month}월 ${day}일에 포인트 획득 완료!",
                    style = tapBoldTextStyle.copy(color = primary)
                )
                Text(
                    text = title,
                    style = title01
                )
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    if (questType is QuestType.Repeat) {
                        RepeatQuestTypeBadge(repeatType = questType)
                    } else if (questType is QuestType.Event) {
                        EventQuestTypeBadge()
                    }
                    MissionTypeBadge(missionType = missionType)
                }
            }
            if (missionType == MissionType.Photo) {
                Spacer(Modifier.height(20.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.thumbs_up),
                        tint = Color.Unspecified,
                        contentDescription = "좋아요 아이콘"
                    )
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = likeCount.toString(),
                        style = heading02.copy(gray300)
                    )
                }
            }
        }
    }
}

@Composable
fun MissionHistoryDetailPointItem(
    modifier: Modifier = Modifier,
    metroGainPoint: Int,
    commercialGainPoint: Int,
    contributionGainPoint: Int
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "획득한 포인트",
                style = tapBoldTextStyle.copy(color = gray500)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "일상지역",
                    style = subTitle02.copy(color = gray500)
                )
                Text(
                    text = "+${metroGainPoint}P",
                    style = title02.copy(primary)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "일상존",
                    style = subTitle02.copy(color = gray500)
                )
                Text(
                    text = "+${commercialGainPoint}P",
                    style = title02.copy(primary)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "기여도",
                    style = subTitle02.copy(color = gray500)
                )
                Text(
                    text = "+${contributionGainPoint}P",
                    style = title02.copy(primary)
                )
            }
        }
    }
}

@Composable
fun MissionHistoryDetailWriterItem(
    modifier: Modifier = Modifier,
    writerName: String
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "작성자 정보",
                style = tapBoldTextStyle.copy(gray500)
            )
            Text(
                text = writerName,
                style = subTitle02
            )
        }
    }
}

@Composable
fun MissionHistoryDetailDateItem(
    modifier: Modifier = Modifier,
    createdAt: String
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "퀘스트 수행 날짜",
                style = tapBoldTextStyle.copy(gray500)
            )
            Text(
                text = createdAt,
                style = subTitle02
            )
        }
    }
}

@Preview
@Composable
private fun MissionHistoryDetailInfoItemPreview() {
    MissionHistoryDetailInfoItem(
        modifier = Modifier,
        title = "Mission Title",
        questType = QuestType.Repeat.Daily,
        missionType = MissionType.Photo,
        likeCount = 100,
        createdAt = "2024.01.01"
    )
}

@Preview
@Composable
private fun MissionHistoryDetailPointItemPreview() {
    MissionHistoryDetailPointItem(
        modifier = Modifier,
        metroGainPoint = 100,
        commercialGainPoint = 200,
        contributionGainPoint = 300
    )
}

@Preview
@Composable
private fun MissionHistoryDetailWriterItemPreview() {
    MissionHistoryDetailWriterItem(
        modifier = Modifier,
        writerName = "ilsang"
    )
}

@Preview
@Composable
private fun MissionHistoryDetailDateItemPreview() {
    MissionHistoryDetailDateItem(
        modifier = Modifier,
        createdAt = "2024.01.01"
    )
}