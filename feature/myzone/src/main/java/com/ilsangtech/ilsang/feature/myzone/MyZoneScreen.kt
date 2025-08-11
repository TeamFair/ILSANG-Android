package com.ilsangtech.ilsang.feature.myzone

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ilsangtech.ilsang.core.model.area.CommercialArea
import com.ilsangtech.ilsang.core.model.area.MetroArea
import com.ilsangtech.ilsang.core.ui.zone.ZoneListContent
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.feature.myzone.component.MyZoneHeader

@Composable
fun MyZoneScreen(
    myZoneViewModel: MyZoneViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit
) {
    val myZoneUiState by myZoneViewModel.myZoneUiState.collectAsStateWithLifecycle()

    MyZoneScreen(
        areaList = myZoneUiState.areaList,
        selectedMetroArea = myZoneUiState.selectedMetroArea,
        selectedCommercialArea = myZoneUiState.selectedCommercialArea,
        onMetroAreaClick = myZoneViewModel::updateSelectedMetroArea,
        onCommercialAreaClick = myZoneViewModel::updateSelectedCommercialArea,
        onSelectButtonClick = myZoneViewModel::selectMyZone,
        onBackButtonClick = onBackButtonClick
    )
}

@Composable
private fun MyZoneScreen(
    areaList: List<MetroArea>,
    selectedMetroArea: MetroArea?,
    selectedCommercialArea: CommercialArea?,
    onMetroAreaClick: (MetroArea) -> Unit,
    onCommercialAreaClick: (CommercialArea) -> Unit,
    onSelectButtonClick: () -> Unit,
    onBackButtonClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            MyZoneHeader(onBackButtonClick = onBackButtonClick)
            Box(modifier = Modifier.weight(1f)) {
                ZoneListContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    areaList = areaList,
                    selectedMetroArea = selectedMetroArea,
                    selectedCommercialArea = selectedCommercialArea,
                    onMetroAreaClick = onMetroAreaClick,
                    onCommercialAreaClick = onCommercialAreaClick
                )
                if (selectedMetroArea != null && selectedCommercialArea != null) {
                    Button(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .navigationBarsPadding()
                            .padding(horizontal = 20.dp)
                            .padding(bottom = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primary,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(vertical = 16.dp),
                        onClick = onSelectButtonClick
                    ) {
                        Text(
                            text = "내 지역 선택하기",
                            style = TextStyle(
                                fontFamily = pretendardFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                lineHeight = 18.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun MyZoneScreenPreview() {
    val sampleMetroArea = MetroArea(
        code = "01",
        areaName = "서울",
        description = "서울특별시",
        commericalAreaList = emptyList()
    )
    val sampleCommercialArea = CommercialArea(
        code = "1001491",
        areaName = "명동",
        description = "명동 거리",
        metroAreaCode = "01"
    )
    MyZoneScreen(
        areaList = listOf(
            MetroArea(
                code = "01",
                areaName = "서울",
                description = "서울특별시",
                commericalAreaList = listOf(
                    CommercialArea(
                        code = "1001491",
                        areaName = "명동",
                        description = "명동 거리",
                        metroAreaCode = "01"
                    ),
                    CommercialArea(
                        code = "1001492",
                        areaName = "강남",
                        description = "강남역",
                        metroAreaCode = "01"
                    )
                )
            ),
            MetroArea(
                code = "02",
                areaName = "부산",
                description = "부산광역시",
                commericalAreaList = emptyList()
            )
        ),
        selectedMetroArea = sampleMetroArea,
        selectedCommercialArea = sampleCommercialArea,
        onMetroAreaClick = {},
        onCommercialAreaClick = {},
        onSelectButtonClick = {},
        onBackButtonClick = {}
    )
}
