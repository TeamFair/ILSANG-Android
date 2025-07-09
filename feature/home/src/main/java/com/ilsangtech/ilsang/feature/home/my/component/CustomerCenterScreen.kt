package com.ilsangtech.ilsang.feature.home.my.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.toSp
import com.ilsangtech.ilsang.feature.home.R

private val settingItemTitleStyle = TextStyle(
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 17.sp,
    color = gray500
)

private val settingItemContentStyle = TextStyle(
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 17.sp,
    color = Color(0xFFA0A0A0)
)

@Composable
internal fun CustomerCenterScreen(
    onBackButtonClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = background
    ) {
        val uriHandler = LocalUriHandler.current
        Column {
            CustomerCenterScreenHeader(onBackButtonClick)
            SettingItem(
                title = {
                    Text(
                        text = "인스타그램",
                        style = settingItemTitleStyle,
                        fontSize = 17.dp.toSp()
                    )
                },
                endContent = {
                    Text(
                        text = "illsang.official",
                        style = settingItemContentStyle,
                        fontSize = 17.dp.toSp(),
                        textDecoration = TextDecoration.Underline
                    )
                },
                onItemClick = {
                    uriHandler.openUri("https://www.instagram.com/illsang.official/")
                }
            )
            SettingItem(
                title = {
                    Text(
                        text = "디스코드",
                        style = settingItemTitleStyle,
                        fontSize = 17.dp.toSp()
                    )
                },
                endContent = {
                    Text(
                        text = "인스타그램 바이오링크 확인",
                        style = settingItemContentStyle,
                        fontSize = 17.dp.toSp(),
                    )
                }
            )
            SettingItem(
                title = {
                    Text(
                        text = "이메일",
                        style = settingItemTitleStyle,
                        fontSize = 17.dp.toSp()
                    )
                },
                endContent = {
                    Text(
                        text = "illsangtech@gmail.com",
                        style = settingItemContentStyle,
                        fontSize = 17.dp.toSp()
                    )
                }
            )
        }
    }
}

@Composable
private fun CustomerCenterScreenHeader(
    onBackButtonClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 12.dp)
            .statusBarsPadding()
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp)
                .clickable(
                    onClick = onBackButtonClick,
                    indication = null,
                    interactionSource = null
                ),
            painter = painterResource(id = R.drawable.back),
            contentDescription = null
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "고객센터",
            style = TextStyle(
                fontFamily = pretendardFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 17.dp.toSp()
            )
        )
    }
}

@Preview
@Composable
private fun CustomerCenterScreenPreview() {
    CustomerCenterScreen {}
}