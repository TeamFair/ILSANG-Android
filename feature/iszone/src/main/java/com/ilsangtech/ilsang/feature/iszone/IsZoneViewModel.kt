package com.ilsangtech.ilsang.feature.iszone

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
class IsZoneViewModel @Inject constructor(
    private val userRepository: UserRepository,
    areaRepository: AreaRepository
) : ViewModel() {
    private val _selectedMetroArea = MutableStateFlow<MetroArea?>(null)
    private val _selectedCommercialArea = MutableStateFlow<CommercialArea?>(null)

    private val _isIsZoneUpdateSuccess = MutableStateFlow<Boolean?>(null)
    val isZoneUiState = combine(
        _selectedCommercialArea,
        _selectedMetroArea,
        areaRepository.getAreaList(),
        _isIsZoneUpdateSuccess
    ) { selectedCommercialArea, selectedMetroArea, areaList, isIsZoneUpdateSuccess ->
        IsZoneUiState(
            selectedCommercialArea = selectedCommercialArea,
            selectedMetroArea = selectedMetroArea,
            areaList = areaList,
            isIsZoneUpdateSuccess = isIsZoneUpdateSuccess
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = IsZoneUiState(
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

    fun resetIsZoneUpdateStatus() {
        _isIsZoneUpdateSuccess.update { null }
    }

    fun selectIsZone() {
        viewModelScope.launch {
            isZoneUiState.value.selectedCommercialArea?.let { commercialArea ->
                userRepository.updateUserIsZone(commercialArea.code)
                    .onSuccess {
                        _isIsZoneUpdateSuccess.update { true }
                    }.onFailure {
                        _isIsZoneUpdateSuccess.update { false }
                    }
            }
        }
    }
}