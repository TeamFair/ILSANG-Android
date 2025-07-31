package com.ilsangtech.ilsang.feature.tutorial

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.tapBoldTextStyle

@Composable
fun TutorialScreen(navigateToHome: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val pagerState = rememberPagerState(
            initialPage = 0,
            pageCount = { pages.size }
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding(),
        ) {
            SkipButton(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(
                        top = 32.dp,
                        end = 16.dp
                    ),
                onClick = navigateToHome
            )
            Spacer(Modifier.height(48.dp))
            TutorialPager(
                modifier = Modifier
                    .weight(1f)
                    .widthIn(max = LocalConfiguration.current.screenWidthDp.dp - 80.dp),
                pages = pages,
                pagerState = pagerState
            )
            Spacer(Modifier.height(45.dp))
            TutorialPageIndicator(pagerState)
            Spacer(Modifier.height(45.dp))
            TutorialButton(
                pagerState = pagerState,
                onClick = navigateToHome
            )
        }
    }
}

private val pages = listOf(
    TutorialPage(
        title = "원하는 퀘스트를 선택하세요",
        imageRes = R.drawable.tutorial_step_1,
        imageWidthDp = 225.92.dp
    ),
    TutorialPage(
        title = "사진을 촬영해 주세요",
        imageRes = R.drawable.tutorial_step_2,
        imageWidthDp = 192.dp
    ),
    TutorialPage(
        title = "사진을 찍으면 퀘스트 완료!",
        imageRes = R.drawable.tutorial_step_3,
        imageWidthDp = 192.dp
    ),
)


@Composable
private fun SkipButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier.clickable(
            onClick = onClick,
            interactionSource = null,
            indication = null
        ),
    ) {
        Text(
            text = "SKIP",
            style = tapBoldTextStyle,
            color = gray300
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun TutorialScreenPreview() {
    TutorialScreen {}
}