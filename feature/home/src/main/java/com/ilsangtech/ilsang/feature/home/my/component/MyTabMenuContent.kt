package com.ilsangtech.ilsang.feature.home.my.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_semibold
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.feature.home.my.MyTabMenu

@Composable
fun MyTabMenuContent(
    selectedMenu: MyTabMenu,
    onSelectMenu: (MyTabMenu) -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        MyTabMenu.entries.forEach { menu ->
            MyTabMenu(
                modifier = Modifier.weight(1f),
                emojiText = menu.emoji,
                title = menu.title,
                isSelected = menu == selectedMenu
            ) {
                if (menu != MyTabMenu.ACTIVITY) { // 활동 메뉴의 경우 스킵
                    onSelectMenu(menu)
                }
            }
        }
    }
}

@Composable
fun MyTabMenu(
    modifier: Modifier = Modifier,
    emojiText: String,
    title: String,
    isSelected: Boolean,
    onSelectMenu: () -> Unit
) {
    val cardColor = if (isSelected) primary else Color.White
    val textColor = if (isSelected) Color.White else gray500
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = cardColor),
        onClick = onSelectMenu
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 14.dp,
                    vertical = 10.dp
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Text(
                text = emojiText,
                style = myTabMenuTextStyle
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = title,
                color = textColor,
                style = myTabMenuTextStyle
            )
        }
    }
}

private val myTabMenuTextStyle = TextStyle(
    fontFamily = FontFamily(Font(pretendard_semibold)),
    fontSize = 14.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.4.sp,
)

@Preview(showBackground = true, backgroundColor = 0xF6F6F6)
@Composable
fun MyTabMenuContentPreview() {
    MyTabMenuContent(selectedMenu = MyTabMenu.CHALLENGE) { }
}