package com.ilsangtech.ilsang.feature.my.screens.title.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.core.model.title.TitleGrade
import com.ilsangtech.ilsang.core.ui.title.TitleGradeIcon
import com.ilsangtech.ilsang.designsystem.component.CircleShapeCheckBox
import com.ilsangtech.ilsang.designsystem.component.IlsangCheckBox
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.tapBoldTextStyle
import com.ilsangtech.ilsang.designsystem.theme.tapRegularTextStyle
import com.ilsangtech.ilsang.designsystem.theme.toSp
import com.ilsangtech.ilsang.feature.my.screens.title.model.MyTitleUiModel


@Composable
internal fun TypeTitleListHeader(
    modifier: Modifier = Modifier,
    selectedGrade: TitleGrade
) {
    val typeName = when (selectedGrade) {
        TitleGrade.Standard -> "일반"
        TitleGrade.Rare -> "희귀"
        TitleGrade.Legend -> "전설"
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = typeName,
            style = TextStyle(
                fontFamily = pretendardFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 19.dp.toSp(),
                lineHeight = 23.dp.toSp()
            )
        )
        TitleGradeIcon(
            modifier = Modifier.size(24.dp),
            titleGrade = selectedGrade
        )
    }
}

@Composable
private fun TypeTitleListItem(
    modifier: Modifier = Modifier,
    title: MyTitleUiModel,
    checked: Boolean,
    onLegendTitleItemClick: () -> Unit,
    onCheckBoxClick: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .drawBehind {
                drawLine(
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    color = gray100,
                    strokeWidth = 1.dp.toPx()
                )
            }
            .padding(vertical = 16.dp)
            .then(
                if (title.title.grade == TitleGrade.Legend) {
                    Modifier.clickable(
                        onClick = onLegendTitleItemClick,
                        indication = null,
                        interactionSource = null
                    )
                } else {
                    Modifier
                }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.width(50.dp),
            contentAlignment = Alignment.Center
        ) {
            IlsangCheckBox(
                modifier = Modifier.size(20.dp),
                checked = checked,
                onClick = { onCheckBoxClick(!checked) }
            )
        }
        Box(modifier = Modifier.weight(1f)) {
            Text(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = title.title.name,
                style = tapRegularTextStyle.copy(
                    lineBreak = LineBreak.Heading
                ),
                color = gray400,
            )
        }
        Box(modifier = Modifier.weight(1f)) {
            Text(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = title.condition,
                style = tapRegularTextStyle.copy(
                    lineBreak = LineBreak.Paragraph
                ),
                color = gray400,
            )
        }
        Box(
            modifier = Modifier.width(50.dp),
            contentAlignment = Alignment.Center
        ) {
            CircleShapeCheckBox(
                modifier = Modifier.size(20.dp),
                checked = title.titleHistoryId != null,
                enabled = false
            )
        }
    }
}

internal fun LazyListScope.typeTitleList(
    titleList: List<MyTitleUiModel>,
    selectedTitle: MyTitleUiModel?,
    onLegendTitleItemClick: (MyTitleUiModel) -> Unit,
    onTitleSelect: (MyTitleUiModel) -> Unit
) {
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .drawBehind {
                    drawLine(
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        color = gray500,
                        strokeWidth = 1.dp.toPx()
                    )
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.width(50.dp),
                text = "선택",
                style = tapBoldTextStyle,
                color = gray500,
                fontSize = 14.dp.toSp(),
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "칭호명",
                style = tapBoldTextStyle,
                color = gray500,
                fontSize = 14.dp.toSp(),
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "획득조건",
                style = tapBoldTextStyle,
                color = gray500,
                fontSize = 14.dp.toSp(),
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.width(50.dp),
                text = "획득",
                style = tapBoldTextStyle,
                color = gray500,
                fontSize = 14.dp.toSp(),
                textAlign = TextAlign.Center
            )
        }
    }
    items(
        items = titleList,
        key = { title -> title.title.name }
    ) { title ->
        TypeTitleListItem(
            title = title,
            checked = selectedTitle == title,
            onLegendTitleItemClick = { onLegendTitleItemClick(title) },
            onCheckBoxClick = {
                if (title.titleHistoryId != null) {
                    onTitleSelect(title)
                }
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun MyTitleContentPreview() {
    LazyColumn {
        item {
            TypeTitleListHeader(
                modifier = Modifier.padding(vertical = 16.dp),
                selectedGrade = TitleGrade.Standard
            )
        }
        typeTitleList(
            titleList = emptyList(),
            selectedTitle = null,
            onLegendTitleItemClick = {},
            onTitleSelect = {}
        )
    }
}