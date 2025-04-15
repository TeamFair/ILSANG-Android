package com.ilsangtech.ilsang.feature.home.my.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_medium
import com.ilsangtech.ilsang.designsystem.theme.caption01
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.title01

@Composable
fun MyChallengeContent() {
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
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            items(emptyList<String>()) {
                MyChallengeItem()
            }
        }
    }
}

@Composable
fun MyChallengeItem() {
    Card(
        shape = RoundedCornerShape(12.dp),
        onClick = {}
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(172.dp)
        ) {
            AsyncImage(
                model = "",
                contentDescription = "수행한 챌린지 이미지",
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 20.dp, bottom = 20.dp)
            ) {
                Text(
                    text = "겨울 간식 먹기",
                    style = title01,
                    color = Color.White
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "2024.12.27",
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
    MyChallengeContent()
}