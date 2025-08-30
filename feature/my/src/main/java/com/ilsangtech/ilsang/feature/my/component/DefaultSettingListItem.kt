package com.ilsangtech.ilsang.feature.my.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily

@Composable
internal fun DefaultSettingListItem(
    title: @Composable () -> Unit,
    endContent: @Composable (() -> Unit)? = null,
    onItemClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        onClick = onItemClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 18.dp,
                    horizontal = 25.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            title.invoke()
            endContent?.invoke()
        }
    }
}

@Composable
internal fun DefaultSettingListItemIcon() {
    Icon(
        painter = painterResource(R.drawable.icon_next),
        contentDescription = null,
        tint = Color.Unspecified
    )
}

internal val defaultSettingListItemTextStyle = TextStyle(
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 17.sp,
    color = gray500
)
