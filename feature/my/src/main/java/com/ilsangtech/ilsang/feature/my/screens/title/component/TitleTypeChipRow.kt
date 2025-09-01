package com.ilsangtech.ilsang.feature.my.screens.title.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.toSp
import com.ilsangtech.ilsang.feature.my.R

@Composable
internal fun TitleTypeChipRow(
    selectedType: String,
    onChipClick: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        listOf("STANDARD", "RARE", "LEGEND").forEach { type ->
            TitleTypeChip(
                modifier = Modifier.weight(1f),
                selected = selectedType == type,
                type = type,
                onClick = { onChipClick(type) }
            )
        }
    }
}

@Composable
private fun TitleTypeChip(
    modifier: Modifier = Modifier,
    selected: Boolean,
    type: String,
    onClick: () -> Unit
) {
    val typeName = when (type) {
        "STANDARD" -> "일반"
        "RARE" -> "희귀"
        else -> "전설"
    }
    val painter = painterResource(
        when (type) {
            "STANDARD" -> R.drawable.icon_normal_title
            "RARE" -> R.drawable.icon_rare_title
            else -> R.drawable.icon_legend_title
        }
    )

    Surface(
        modifier = modifier,
        onClick = onClick,
        color = if (selected) primary else Color.White,
        shape = RoundedCornerShape(12.dp),
        contentColor = if (selected) Color.White else gray500
    ) {
        Row(
            modifier = Modifier.padding(vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(18.dp),
                painter = painter,
                contentDescription = "칭호 아이콘",
                tint = Color.Unspecified
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = typeName,
                fontFamily = pretendardFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.dp.toSp(),
                lineHeight = 24.dp.toSp()
            )
        }
    }
}

@Composable
@Preview
private fun TitleTypeChipRowPreview() {
    var selectedType by remember { mutableStateOf("STANDARD") }
    TitleTypeChipRow(selectedType) { selectedType = it }
}