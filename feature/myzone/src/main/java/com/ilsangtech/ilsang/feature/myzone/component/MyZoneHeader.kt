package com.ilsangtech.ilsang.feature.myzone.component

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily

@Composable
internal fun MyZoneHeader(
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(start = 15.dp)
            .padding(top = 12.dp, bottom = 11.dp)
    ) {
        Icon(
            modifier = Modifier.clickable(
                onClick = onBackButtonClick,
                interactionSource = null,
                indication = null
            ),
            painter = painterResource(R.drawable.icon_back),
            tint = gray500,
            contentDescription = "뒤로 가기"
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "지역 선택",
            style = TextStyle(
                fontFamily = pretendardFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                lineHeight = 22.sp
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MyZoneHeaderPreview() {
    MyZoneHeader {}
}