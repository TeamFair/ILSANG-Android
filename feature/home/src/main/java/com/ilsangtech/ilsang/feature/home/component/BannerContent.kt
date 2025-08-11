package com.ilsangtech.ilsang.feature.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.model.Banner
import com.ilsangtech.ilsang.feature.home.BuildConfig

@Composable
internal fun BannerContent(
    modifier: Modifier = Modifier,
    banners: List<Banner>,
    onClick: () -> Unit
) {
    val pagerState = rememberPagerState { banners.size }
    HorizontalPager(
        modifier = modifier,
        state = pagerState
    ) { page ->
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(11 / 10f)
                .clip(RoundedCornerShape(12.dp))
                .clickable(
                    onClick = {
                        if (banners[page].description.contains("quest")) {
                            onClick()
                        }
                    },
                    indication = null,
                    interactionSource = null
                ),
            contentScale = ContentScale.Crop,
            model = BuildConfig.IMAGE_URL + banners[page].imageId,
            contentDescription = banners[page].description
        )
    }
}
