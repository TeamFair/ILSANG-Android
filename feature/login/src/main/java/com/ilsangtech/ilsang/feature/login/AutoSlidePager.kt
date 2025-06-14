package com.ilsangtech.ilsang.feature.login

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun AutoSlidePager(
    modifier: Modifier = Modifier,
    pageList: List<@Composable () -> Unit>
) {
    val coroutineScope = rememberCoroutineScope()
    val actualPageCount = pageList.size
    val fakePageCount = actualPageCount + 2
    val pagerState = rememberPagerState(initialPage = 1) { fakePageCount }

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val contentPadding = (screenWidth - 180.dp) / 2

    LaunchedEffect(Unit) {
        while (true) {
            delay(2500)
            coroutineScope.launch {
                pagerState.animateScrollToPage(
                    page = pagerState.currentPage + 1,
                    animationSpec = tween(
                        durationMillis = 400,
                        easing = LinearOutSlowInEasing
                    )
                )
            }
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        coroutineScope.launch {
            when (pagerState.currentPage) {
                0 -> { // 첫 번째 앞의 가짜 페이지
                    delay(200)
                    pagerState.scrollToPage(page = actualPageCount) // 마지막 페이지로 순간 이동
                }

                fakePageCount - 1 -> { // 마지막 뒤의 가짜 페이지
                    delay(200)
                    pagerState.scrollToPage(1) // 첫 번째 페이지로 순간 이동
                }
            }
        }
    }

    HorizontalPager(
        modifier = modifier.fillMaxWidth(),
        pageSize = PageSize.Fixed(180.dp),
        contentPadding = PaddingValues(horizontal = contentPadding),
        state = pagerState,
        userScrollEnabled = false
    ) { pageIndex ->
        val pageOffset = (pagerState.currentPage - pageIndex) + pagerState.currentPageOffsetFraction
        val scale = lerp(0.83f, 1f, 1f - pageOffset.absoluteValue.coerceIn(0f, 1f))
        val pageAlpha = lerp(0.2f, 1f, 1f - pageOffset.absoluteValue.coerceIn(0f, 1f))

        val actualPageIndex =
            when (pageIndex) {
                0 -> actualPageCount - 1
                fakePageCount - 1 -> 0
                else -> pageIndex - 1
            }

        Box(
            modifier = Modifier.graphicsLayer {
                scaleX = scale
                scaleY = scale
                alpha = pageAlpha
            }
        ) {
            pageList[actualPageIndex].invoke()
        }
    }
}