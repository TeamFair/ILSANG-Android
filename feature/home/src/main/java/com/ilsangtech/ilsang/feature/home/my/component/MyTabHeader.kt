package com.ilsangtech.ilsang.feature.home.my.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_bold
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.feature.home.R

@Composable
fun MyTabHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "내 프로필",
            style = myTabHeaderTitleTextStyle
        )
        Spacer(Modifier.weight(1f))
        Icon(
            painter = painterResource(R.drawable.setting),
            contentDescription = "설정",
            tint = gray500
        )
    }
}

private val myTabHeaderTitleTextStyle = TextStyle(
    fontFamily = FontFamily(Font(pretendard_bold)),
    fontSize = 21.sp,
    lineHeight = 1.4.em,
    color = gray500
)

@Preview(showBackground = true, backgroundColor = 0xF6F6F6)
@Composable
fun MyTabHeaderPreview() {
    MyTabHeader()
}