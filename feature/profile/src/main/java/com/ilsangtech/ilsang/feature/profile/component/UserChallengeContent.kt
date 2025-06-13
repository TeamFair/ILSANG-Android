package com.ilsangtech.ilsang.feature.profile.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.model.Challenge
import com.ilsangtech.ilsang.core.util.DateConverter
import com.ilsangtech.ilsang.designsystem.theme.caption01
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.heading02
import com.ilsangtech.ilsang.designsystem.theme.title01
import com.ilsangtech.ilsang.feature.profile.BuildConfig
import kotlinx.coroutines.flow.flowOf

internal fun LazyListScope.userChallengeContent(
    challengePaging: LazyPagingItems<Challenge>,
    onChallengeClick: (Challenge) -> Unit
) {
    item {
        Text(
            modifier = Modifier.padding(
                top = 4.dp,
                start = 4.dp
            ),
            text = "수행한 챌린지",
            style = heading02.copy(gray400)
        )
    }
    item { Spacer(Modifier.height(12.dp)) }
    items(
        count = challengePaging.itemCount,
        key = { index -> challengePaging[index]?.challengeId ?: index }
    ) { index ->
        val challenge = challengePaging[index]
        Column {
            challenge?.let {
                UserChallengeItem(
                    challenge = challenge,
                    onChallengeClick = { onChallengeClick(challenge) }
                )
            }
            if (index < challengePaging.itemCount - 1) {
                Spacer(Modifier.height(9.dp))
            }
        }
    }
    item {
        Spacer(
            Modifier
                .height(68.dp)
                .navigationBarsPadding()
        )
    }

}

@Composable
private fun UserChallengeItem(
    challenge: Challenge,
    onChallengeClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        onClick = onChallengeClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(172.dp)
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = BuildConfig.IMAGE_URL + challenge.receiptImageId,
                contentDescription = "수행한 챌린지 이미지",
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 20.dp, bottom = 20.dp)
            ) {
                Text(
                    text = challenge.missionTitle,
                    style = title01,
                    color = Color.White
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = DateConverter.formatDate(challenge.createdAt),
                    style = caption01,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UserChallengeContentPreview() {
    val challengeList = List(20) {
        Challenge(
            challengeId = it.toString(),
            createdAt = "",
            hateCnt = 0,
            likeCnt = 1,
            missionTitle = "",
            questImage = null,
            receiptImageId = null,
            status = "",
            userNickName = "",
            viewCount = 5
        )
    }
    val challengePaging = flowOf(
        PagingData.from(
            data = challengeList
        )
    ).collectAsLazyPagingItems()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        userChallengeContent(
            challengePaging = challengePaging,
            onChallengeClick = {}
        )
    }
}