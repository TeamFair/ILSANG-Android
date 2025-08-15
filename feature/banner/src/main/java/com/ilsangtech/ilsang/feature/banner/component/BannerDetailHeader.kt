package com.ilsangtech.ilsang.feature.banner.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.toSp

@Composable
internal fun BannerDetailHeader(
    modifier: Modifier = Modifier,
    bannerTitle: String,
    onBackButtonClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(top = 12.dp, bottom = 11.dp)
            .padding(start = 15.dp)
    ) {
        Icon(
            modifier = Modifier
                .clickable(
                    onClick = onBackButtonClick,
                    indication = null,
                    interactionSource = null
                ),
            tint = Color.Black,
            painter = painterResource(R.drawable.icon_chevron_back),
            contentDescription = "뒤로 가기"
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = bannerTitle,
            style = TextStyle(
                fontFamily = pretendardFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 17.dp.toSp(),
                lineHeight = 22.dp.toSp()
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BannerDetailHeaderPreview() {
    BannerDetailHeader(bannerTitle = "배너 타이틀") {}
}