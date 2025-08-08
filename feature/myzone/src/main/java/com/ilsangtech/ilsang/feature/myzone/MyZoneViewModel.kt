package com.ilsangtech.ilsang.feature.myzone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilsangtech.ilsang.core.model.area.CommercialArea
import com.ilsangtech.ilsang.core.model.area.MetroArea
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyZoneViewModel @Inject constructor(
) : ViewModel() {
    private val _selectedMetroArea = MutableStateFlow<MetroArea?>(null)
    private val _selectedCommercialArea = MutableStateFlow<CommercialArea?>(null)

    val myZoneUiState = combine(
        _selectedCommercialArea,
        _selectedMetroArea
    ) { selectedCommercialArea, selectedMetroArea ->
        MyZoneUiState(
            selectedCommercialArea = selectedCommercialArea,
            selectedMetroArea = selectedMetroArea,
            areaList = emptyList()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MyZoneUiState(
            selectedCommercialArea = null,
            selectedMetroArea = null,
            areaList = emptyList()
        )
    )

    fun updateSelectedMetroArea(metroArea: MetroArea) {
        _selectedMetroArea.update { metroArea }
    }

    fun updateSelectedCommercialArea(commercialArea: CommercialArea) {
        _selectedCommercialArea.update { commercialArea }
    }

    fun selectMyZone() {
        viewModelScope.launch {
            if (myZoneUiState.value.selectedMetroArea != null
                && myZoneUiState.value.selectedCommercialArea != null
            ) {
                //TODO 내 일상존 선택 확정
            }
        }
    }
}