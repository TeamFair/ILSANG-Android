package com.ilsangtech.ilsang.feature.tutorial

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_semibold
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.primary300
import com.ilsangtech.ilsang.designsystem.theme.tapBoldTextStyle
import com.ilsangtech.ilsang.designsystem.theme.title01
import kotlinx.coroutines.launch

@Composable
internal fun TutorialPager(
    modifier: Modifier = Modifier,
    pages: List<TutorialPage>,
    pagerState: PagerState,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState
        ) { pageIndex ->
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TutorialBadge(pageIndex)
                Spacer(Modifier.height(16.dp))
                Text(
                    text = pages[pageIndex].title,
                    style = title01
                )
                Spacer(Modifier.height(35.dp))
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = pages[pageIndex].imageRes),
                    contentScale = ContentScale.Fit,
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
private fun TutorialBadge(index: Int) {
    Box(
        modifier = Modifier
            .background(
                color = primary300,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = "Step 0${index + 1}",
            style = tapBoldTextStyle.copy(
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Center,
                    trim = LineHeightStyle.Trim.None
                )
            ),
            color = Color.White
        )
    }
}

@Composable
internal fun TutorialPageIndicator(pagerState: PagerState) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        repeat(pagerState.pageCount) {
            val color = if (pagerState.currentPage == it) {
                gray500
            } else {
                gray100
            }
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(
                        color = color,
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
internal fun TutorialButton(
    pagerState: PagerState,
    onClick: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val prevButtonColor = ButtonDefaults.buttonColors(containerColor = gray300)
    val nextButtonColor = ButtonDefaults.buttonColors(containerColor = primary)
    val buttonShape = remember { RoundedCornerShape(12.dp) }
    val buttonContentPadding = remember { PaddingValues(vertical = 16.dp) }
    val buttonTextStyle = remember {
        TextStyle(
            fontSize = 16.sp,
            lineHeight = 18.sp,
            fontFamily = FontFamily(Font(pretendard_semibold))
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 20.dp)
    ) {
        when (pagerState.currentPage) {
            0 -> {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = nextButtonColor,
                    shape = buttonShape,
                    contentPadding = buttonContentPadding,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                ) {
                    Text(
                        text = "다음",
                        style = buttonTextStyle
                    )
                }
            }

            pagerState.pageCount - 1 -> {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = nextButtonColor,
                    shape = buttonShape,
                    contentPadding = buttonContentPadding,
                    onClick = onClick
                ) {
                    Text(
                        text = "완료",
                        style = buttonTextStyle
                    )
                }
            }

            else -> {
                Button(
                    modifier = Modifier.weight(1f),
                    colors = prevButtonColor,
                    shape = buttonShape,
                    contentPadding = buttonContentPadding,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
                ) {
                    Text(
                        text = "이전",
                        style = buttonTextStyle
                    )
                }
                Spacer(Modifier.width(8.dp))
                Button(
                    modifier = Modifier.weight(1f),
                    colors = nextButtonColor,
                    shape = buttonShape,
                    contentPadding = buttonContentPadding,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                ) {
                    Text(
                        text = "다음",
                        style = buttonTextStyle
                    )
                }
            }
        }
    }
}

data class TutorialPage(
    val title: String,
    val imageRes: Int,
    val imageWidthDp: Dp
)

