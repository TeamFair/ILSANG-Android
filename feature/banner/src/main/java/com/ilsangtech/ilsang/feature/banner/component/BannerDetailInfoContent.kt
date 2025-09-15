package com.ilsangtech.ilsang.feature.banner.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.subTitle01
import com.ilsangtech.ilsang.designsystem.theme.title02
import com.ilsangtech.ilsang.feature.banner.BuildConfig

internal fun LazyListScope.bannerDetailInfoContent(
    modifier: Modifier = Modifier,
    imageId: String,
    title: String,
    description: String
) {
    item {
        Column(modifier = modifier.padding(top = 2.dp)) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3 / 2f),
                model = BuildConfig.IMAGE_URL + imageId,
                contentDescription = title
            )
            Column(
                modifier = Modifier
                    .padding(top = 26.dp, bottom = 16.dp)
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    modifier = Modifier.padding(top = 2.dp),
                    text = title,
                    style = title02,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = description,
                    style = subTitle01,
                    color = gray500,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            HorizontalDivider(
                thickness = 8.dp,
                color = gray100
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BannerDetailInfoContentPreview() {
    LazyColumn {
        bannerDetailInfoContent(
            imageId = "your_image_id",
            title = "*일상 X 미트스팟 한정 퀘스트 이벤트*",
            description = "이벤트에 대한 설명이 들어갑니다 이벤트에 대한 설명이 들어갑니다"
        )
    }
}