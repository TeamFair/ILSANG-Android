package com.ilsangtech.ilsang.feature.approval

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ilsangtech.ilsang.core.domain.MissionRepository
import com.ilsangtech.ilsang.feature.approval.model.ReportResultUiState
import com.ilsangtech.ilsang.feature.approval.model.ReportTypeUiModel
import com.ilsangtech.ilsang.feature.approval.navigation.ReportRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val missionRepository: MissionRepository
) : ViewModel() {
    private val missionHistoryId = savedStateHandle.toRoute<ReportRoute>().missionHistoryId
    private val commentId = savedStateHandle.toRoute<ReportRoute>().commentId

    private val _selectedReportTypes = MutableStateFlow<List<ReportTypeUiModel>>(emptyList())
    val selectedReportTypes = _selectedReportTypes.asStateFlow()

    private val _reportResultUiState =
        MutableStateFlow<ReportResultUiState>(ReportResultUiState.UnReported)
    val reportResultUiState = _reportResultUiState.asStateFlow()

    fun updateSelectedReportTypes(reportType: ReportTypeUiModel) {
        _selectedReportTypes.update {
            if (it.contains(reportType)) {
                it.filter { type -> type != reportType }
            } else {
                it + reportType
            }
        }
    }

    fun report() {
        check(missionHistoryId != null || commentId != null)
        viewModelScope.launch {
            val reason = selectedReportTypes.value.joinToString(",") { it.toString() }
            missionHistoryId?.let { missionHistoryId ->
                missionRepository.reportMissionHistory(
                    missionHistoryId = missionHistoryId,
                    reason = reason
                ).onSuccess { isSuccess ->
                    if (isSuccess) {
                        _reportResultUiState.update { ReportResultUiState.Success }
                    } else {
                        _reportResultUiState.update { ReportResultUiState.Reported }
                    }
                }.onFailure {
                    _reportResultUiState.update { ReportResultUiState.Failure }
                }
            }
        }
    }
}