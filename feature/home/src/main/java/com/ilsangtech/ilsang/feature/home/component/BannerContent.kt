package com.ilsangtech.ilsang.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.model.banner.Banner
import com.ilsangtech.ilsang.designsystem.theme.gray200
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.toSp
import com.ilsangtech.ilsang.feature.home.BuildConfig

@Composable
internal fun BannerContent(
    modifier: Modifier = Modifier,
    banners: List<Banner>,
    onClick: (Banner) -> Unit
) {
    val pagerState = rememberPagerState { banners.size }
    Box {
        HorizontalPager(
            modifier = modifier,
            state = pagerState
        ) { page ->
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(11 / 10f)
                    .clickable(
                        onClick = { onClick(banners[page]) },
                        indication = null,
                        interactionSource = null
                    ),
                contentScale = ContentScale.Crop,
                model = BuildConfig.IMAGE_URL + banners[page].imageId,
                contentDescription = banners[page].description
            )
        }
        BannerIndicator(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 22.dp),
            pageCount = banners.size,
            currentPage = pagerState.currentPage
        )
    }
}

@Composable
private fun BannerIndicator(
    modifier: Modifier = Modifier,
    pageCount: Int,
    currentPage: Int
) {
    Box(
        modifier = modifier
            .height(20.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(Color.Black.copy(alpha = 0.7f))
            .padding(horizontal = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        ProvideTextStyle(
            value = TextStyle(
                fontFamily = pretendardFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 10.dp.toSp(),
                lineHeight = 12.dp.toSp()
            )
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = (currentPage + 1).toString(),
                    color = Color.White
                )
                VerticalDivider(
                    modifier = Modifier.height(8.dp),
                    color = gray200
                )
                Text(
                    text = pageCount.toString(),
                    color = gray200
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BannerContentPreview() {
    val banners = listOf(
        Banner(
            id = 1,
            title = "Banner 1",
            imageId = "imageId1",
            imageUrl = "imageUrl1",
            description = "Description 1",
            activeYn = "Y"
        ),
        Banner(
            id = 2,
            title = "Banner 2",
            imageId = "imageId2",
            imageUrl = "imageUrl2",
            description = "Description 2",
            activeYn = "Y"
        )
    )
    BannerContent(banners = banners, onClick = {})
}