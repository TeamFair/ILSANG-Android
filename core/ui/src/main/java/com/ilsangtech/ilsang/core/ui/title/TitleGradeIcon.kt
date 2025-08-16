package com.ilsangtech.ilsang.core.ui.title

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.ilsangtech.ilsang.core.model.title.TitleGrade
import com.ilsangtech.ilsang.designsystem.R

@Composable
fun TitleGradeIcon(
    modifier: Modifier = Modifier,
    titleGrade: TitleGrade
) {
    Icon(
        modifier = modifier,
        painter = painterResource(
            when (titleGrade) {
                TitleGrade.Standard -> R.drawable.icon_standard_title

                TitleGrade.Rare -> R.drawable.icon_rare_title

                TitleGrade.Legend -> R.drawable.icon_legend_title
            }
        ),
        tint = Color.Unspecified,
        contentDescription = "칭호 아이콘"
    )
}

@Preview
@Composable
private fun TitleGradeIconPreviewStandard() {
    TitleGradeIcon(titleGrade = TitleGrade.Standard)
}

@Preview
@Composable
private fun TitleGradeIconPreviewRare() {
    TitleGradeIcon(titleGrade = TitleGrade.Rare)
}

@Preview
@Composable
private fun TitleGradeIconPreviewLegend() {
    TitleGradeIcon(titleGrade = TitleGrade.Legend)
}


