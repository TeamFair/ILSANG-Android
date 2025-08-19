package com.ilsangtech.ilsang.core.ui.zone

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.core.model.area.CommercialArea
import com.ilsangtech.ilsang.core.model.area.MetroArea
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary300
import com.ilsangtech.ilsang.designsystem.theme.primary500
import com.ilsangtech.ilsang.designsystem.theme.subTitle01
import com.ilsangtech.ilsang.designsystem.theme.subTitle02
import com.ilsangtech.ilsang.designsystem.theme.toSp

@Composable
fun ZoneListContent(
    modifier: Modifier = Modifier,
    areaList: List<MetroArea>,
    selectedMetroArea: MetroArea?,
    selectedCommercialArea: CommercialArea?,
    onMetroAreaClick: (MetroArea) -> Unit,
    onCommercialAreaClick: (CommercialArea) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MetroAreaList(
            selectedMetroArea = selectedMetroArea,
            areaList = areaList,
            onMetroAreaClick = onMetroAreaClick
        )
        CommercialAreaList(
            selectedMetroArea = selectedMetroArea,
            selectedCommercialArea = selectedCommercialArea,
            onCommercialAreaClick = onCommercialAreaClick
        )
    }
}

@Composable
private fun MetroAreaList(
    modifier: Modifier = Modifier,
    areaList: List<MetroArea>,
    selectedMetroArea: MetroArea?,
    onMetroAreaClick: (MetroArea) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .width(103.dp)
            .fillMaxHeight()
            .background(background)
    ) {
        items(
            items = areaList,
            key = { metroArea -> metroArea.code }
        ) {
            MetroAreaItem(
                metroArea = it,
                isSelected = it == selectedMetroArea,
                onClick = { onMetroAreaClick(it) }
            )
        }
    }
}

@Composable
private fun CommercialAreaList(
    modifier: Modifier = Modifier,
    selectedMetroArea: MetroArea?,
    selectedCommercialArea: CommercialArea?,
    onCommercialAreaClick: (CommercialArea) -> Unit
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    drawLine(
                        strokeWidth = 1.dp.toPx(),
                        color = gray100,
                        start = Offset(
                            x = 0f,
                            y = size.height - 1.dp.toPx()
                        ),
                        end = Offset(
                            x = size.width,
                            y = size.height - 1.dp.toPx()
                        )
                    )
                }
                .padding(start = 16.dp)
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
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
                contentDescription = "광역 지역 아이콘"
            )
            Text(
                text = selectedMetroArea?.areaName ?: "지역을 선택해주세요",
                style = TextStyle(
                    fontFamily = pretendardFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.dp.toSp(),
                    lineHeight = 18.dp.toSp()
                )
            )
        }
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(
                items = selectedMetroArea?.commercialAreaList.orEmpty(),
                key = { commercialArea -> commercialArea.code }
            ) {
                CommercialAreaItem(
                    commercialArea = it,
                    isSelected = it == selectedCommercialArea,
                    onClick = { onCommercialAreaClick(it) }
                )
            }
            item {
                Spacer(
                    modifier = Modifier
                        .height(120.dp)
                        .navigationBarsPadding()
                )
            }
        }
    }
}

@Composable
private fun MetroAreaItem(
    modifier: Modifier = Modifier,
    metroArea: MetroArea,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                if (isSelected) {
                    primary300
                } else {
                    Color.Transparent
                }
            )
            .padding(vertical = 15.dp)
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = null
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = metroArea.areaName,
            style = subTitle01.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 16.dp.toSp(),
                lineHeight = 24.dp.toSp()
            ),
            color = if (isSelected) Color.White else gray500
        )
    }
}

@Composable
private fun CommercialAreaItem(
    modifier: Modifier = Modifier,
    commercialArea: CommercialArea,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 1.dp,
                        color = primary500,
                        shape = RoundedCornerShape(12.dp)
                    )
                } else {
                    Modifier.clickable(
                        onClick = onClick,
                        indication = null,
                        interactionSource = null
                    )
                }
            )
            .padding(start = 20.dp)
            .padding(vertical = 15.dp)
    ) {
        Text(
            text = commercialArea.areaName,
            style = subTitle02.copy(
                fontSize = 16.dp.toSp(),
                lineHeight = 24.dp.toSp()
            ),
            color = if (isSelected) primary500 else Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MyZoneListContentPreview() {
    val sampleCommercialArea1 = CommercialArea(
        code = "CA001",
        areaName = "강남",
        description = "Commercial hub in Seoul",
        metroAreaCode = "MA001"
    )
    val sampleCommercialArea2 = CommercialArea(
        code = "CA002",
        areaName = "명동",
        description = "Shopping district in Seoul",
        metroAreaCode = "MA001"
    )
    val sampleMetroArea1 = MetroArea(
        code = "MA001",
        areaName = "서울",
        commercialAreaList = listOf(sampleCommercialArea1, sampleCommercialArea2)
    )
    val sampleMetroArea2 = MetroArea(
        code = "MA002",
        areaName = "부산",
        commercialAreaList = emptyList()
    )

    ZoneListContent(
        modifier = Modifier.padding(horizontal = 20.dp),
        areaList = listOf(sampleMetroArea1, sampleMetroArea2),
        selectedMetroArea = sampleMetroArea1,
        selectedCommercialArea = sampleCommercialArea2,
        onMetroAreaClick = {},
        onCommercialAreaClick = {}
    )
}
