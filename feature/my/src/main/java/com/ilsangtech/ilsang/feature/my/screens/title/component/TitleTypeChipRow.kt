package com.ilsangtech.ilsang.feature.my.screens.title.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.core.model.title.TitleGrade
import com.ilsangtech.ilsang.core.ui.title.TitleGradeIcon
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.toSp

@Composable
internal fun TitleTypeChipRow(
    selectedType: TitleGrade,
    onChipClick: (TitleGrade) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        listOf(TitleGrade.Standard, TitleGrade.Rare, TitleGrade.Legend)
            .forEach { type ->
                TitleTypeChip(
                    modifier = Modifier.weight(1f),
                    selected = selectedType == type,
                    grade = type,
                    onClick = { onChipClick(type) }
                )
            }
    }
}

@Composable
private fun TitleTypeChip(
    modifier: Modifier = Modifier,
    grade: TitleGrade,
    selected: Boolean,
    onClick: () -> Unit
) {
    val typeName = when (grade) {
        TitleGrade.Standard -> "일반"
        TitleGrade.Rare -> "희귀"
        TitleGrade.Legend -> "전설"
    }

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
            TitleGradeIcon(
                modifier = Modifier.size(18.dp),
                titleGrade = grade
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
    var selectedType by remember {
        mutableStateOf<TitleGrade>(TitleGrade.Standard)
    }
    TitleTypeChipRow(selectedType) { selectedType = it }
}