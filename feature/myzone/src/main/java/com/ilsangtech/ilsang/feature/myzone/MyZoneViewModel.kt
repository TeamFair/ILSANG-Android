package com.ilsangtech.ilsang.feature.myzone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilsangtech.ilsang.core.domain.AreaRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
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
    private val userRepository: UserRepository,
    areaRepository: AreaRepository
) : ViewModel() {
    private val _selectedMetroArea = MutableStateFlow<MetroArea?>(null)
    private val _selectedCommercialArea = MutableStateFlow<CommercialArea?>(null)

    private val _isMyZoneUpdateSuccess = MutableStateFlow<Boolean?>(null)
    val myZoneUiState = combine(
        _selectedCommercialArea,
        _selectedMetroArea,
        areaRepository.getMetroAreaList(),
        _isMyZoneUpdateSuccess
    ) { selectedCommercialArea, selectedMetroArea, areaList, isMyZoneUpdateSuccess ->
        MyZoneUiState(
            selectedCommercialArea = selectedCommercialArea,
            selectedMetroArea = selectedMetroArea,
            areaList = areaList,
            isMyZoneUpdateSuccess = isMyZoneUpdateSuccess
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

    fun resetMyZoneUpdateStatus() {
        _isMyZoneUpdateSuccess.update { null }
    }

    fun selectMyZone() {
        viewModelScope.launch {
            myZoneUiState.value.selectedCommercialArea?.let { commercialArea ->
                userRepository.updateUserMyZone(commercialArea.code)
                    .onSuccess {
                        _isMyZoneUpdateSuccess.update { true }
                    }.onFailure {
                        _isMyZoneUpdateSuccess.update { false }
                    }
            }
        }
    }
}