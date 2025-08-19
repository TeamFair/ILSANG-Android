package com.ilsangtech.ilsang.feature.iszone

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
import com.ilsangtech.ilsang.feature.iszone.component.IsZoneHeader

@Composable
internal fun IsZoneScreen(
    isZoneViewModel: IsZoneViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit
) {
    val isZoneUiState by isZoneViewModel.myZoneUiState.collectAsStateWithLifecycle()
    IsZoneScreen(
        selectedMetroArea = isZoneUiState.selectedMetroArea,
        selectedCommercialArea = isZoneUiState.selectedCommercialArea,
        areaList = isZoneUiState.areaList,
        onMetroAreaClick = isZoneViewModel::updateSelectedMetroArea,
        onCommercialAreaClick = isZoneViewModel::updateSelectedCommercialArea,
        onSelectButtonClick = isZoneViewModel::selectIsZone,
        onBackButtonClick = onBackButtonClick
    )
}

@Composable
private fun IsZoneScreen(
    selectedMetroArea: MetroArea?,
    selectedCommercialArea: CommercialArea?,
    areaList: List<MetroArea>,
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
            IsZoneHeader(onBackButtonClick = onBackButtonClick)
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
                            text = "내 일상존 선택하기",
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
private fun IsZoneScreenPreview() {
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

    IsZoneScreen(
        areaList = listOf(sampleMetroArea1, sampleMetroArea2),
        selectedMetroArea = sampleMetroArea1,
        selectedCommercialArea = sampleCommercialArea2,
        onMetroAreaClick = {},
        onCommercialAreaClick = {},
        onSelectButtonClick = {},
        onBackButtonClick = {}
    )
}
