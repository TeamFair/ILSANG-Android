package com.ilsangtech.ilsang.feature.home.my.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.model.Challenge
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_medium
import com.ilsangtech.ilsang.designsystem.theme.caption01
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.title01
import com.ilsangtech.ilsang.feature.home.BuildConfig
import com.ilsangtech.ilsang.feature.home.my.util.formatDate
import kotlinx.coroutines.flow.asFlow

@Composable
fun MyChallengeContent(
    challengePager: LazyPagingItems<Challenge>,
    onMyChallengeClick: (Challenge) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(vertical = 20.dp),
            text = "수행한 챌린지",
            style = myChallengeContentTitleTextStyle
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(9.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            items(challengePager.itemCount) { index ->
                challengePager[index]?.let {
                    MyChallengeItem(
                        challenge = it,
                        onMyChallengeClick = { onMyChallengeClick(it) }
                    )
                }
            }
        }
    }
}

@Composable
fun MyChallengeItem(
    challenge: Challenge,
    onMyChallengeClick: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        onClick = onMyChallengeClick
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
                    text = formatDate(challenge.createdAt),
                    style = caption01,
                    color = Color.White
                )
            }
        }
    }
}

private val myChallengeContentTitleTextStyle = TextStyle(
    fontFamily = FontFamily(Font(pretendard_medium)),
    fontSize = 14.sp,
    lineHeight = 16.sp,
    color = gray400
)

@Preview(showBackground = true, backgroundColor = 0xF6F6F6)
@Composable
fun MyChallengeContentPreview() {
    MyChallengeContent(
        emptyList<PagingData<Challenge>>().asFlow().collectAsLazyPagingItems(),
        {}
    )
}