package com.ilsangtech.ilsang.core.ui.zone

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.tapBoldTextStyle

@Composable
fun MyZoneSelector(
    modifier: Modifier = Modifier,
    myCommercialAreaName: String,
    onMyZoneClick: () -> Unit
) {
    Row(
        modifier = modifier.clickable(
            onClick = onMyZoneClick,
            indication = null,
            interactionSource = null
        ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            modifier = Modifier
                .size(30.dp)
                .padding(
                    top = 4.dp,
                    bottom = 4.53.dp,
                    start = 7.dp,
                    end = 6.81.dp
                ),
            painter = painterResource(R.drawable.icon_metro),
            tint = Color.Unspecified,
            contentDescription = "광역 지역"
        )
        Text(
            text = myCommercialAreaName,
            style = tapBoldTextStyle,
            color = gray500
        )
        Icon(
            painter = painterResource(R.drawable.icon_under),
            tint = Color.Unspecified,
            contentDescription = null
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MyZoneSelectorPreview() {
    MyZoneSelector(
        myCommercialAreaName = "강남구",
        onMyZoneClick = {}
    )
}