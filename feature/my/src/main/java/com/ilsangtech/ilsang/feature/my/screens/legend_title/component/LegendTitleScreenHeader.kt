package com.ilsangtech.ilsang.feature.my.screens.legend_title.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.core.model.title.TitleGrade
import com.ilsangtech.ilsang.core.ui.title.TitleGradeIcon
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.toSp

@Composable
internal fun LegendTitleScreenHeader(
    modifier: Modifier = Modifier,
    title: String,
    onBackButtonClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(top = 12.dp, bottom = 11.dp)
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 15.dp)
                .clickable(
                    onClick = onBackButtonClick,
                    interactionSource = null,
                    indication = null
                ),
            painter = painterResource(R.drawable.icon_chevron_back),
            tint = gray500,
            contentDescription = null
        )

        Row(
            modifier = Modifier.align(Alignment.Center),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TitleGradeIcon(
                modifier = Modifier.size(18.dp),
                titleGrade = TitleGrade.Legend
            )
            Text(
                text = title,
                style = TextStyle(
                    fontFamily = pretendardFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.dp.toSp(),
                    lineHeight = 22.dp.toSp()
                ),
                color = gray500
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LegendTitleScreenHeaderPreview() {
    LegendTitleScreenHeader(
        title = "체력왕",
        onBackButtonClick = {}
    )
}