package com.ilsangtech.ilsang.feature.approval

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.cachedIn
import androidx.paging.map
import com.ilsangtech.ilsang.core.domain.AreaRepository
import com.ilsangtech.ilsang.core.domain.MissionRepository
import com.ilsangtech.ilsang.feature.approval.model.MissionHistoryUiModel
import com.ilsangtech.ilsang.feature.approval.model.toUiModel
import com.ilsangtech.ilsang.feature.approval.navigation.ApprovalExampleRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApprovalExampleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    areaRepository: AreaRepository,
    private val missionRepository: MissionRepository
) : ViewModel() {
    private val missionId = savedStateHandle.toRoute<ApprovalExampleRoute>().missionId

    private val _missionHistoryRefreshTrigger = MutableSharedFlow<Unit>(replay = 1)
    val missionHistoryRefreshTrigger = _missionHistoryRefreshTrigger.asSharedFlow()

    val exampleMissionHistories = missionRepository.getExampleMissionHistory(missionId)
        .cachedIn(viewModelScope)
        .map { pagingData ->
            pagingData.map { exampleMissionHistory ->
                val commercialArea =
                    areaRepository.getCommercialArea(exampleMissionHistory.commercialAreaCode)
                exampleMissionHistory.toUiModel(commercialArea.areaName)
            }
        }

    fun likeMissionHistory(missionHistory: MissionHistoryUiModel) {
        viewModelScope.launch {
            val missionHistoryId = missionHistory.missionHistoryId
            val emojiTypes = missionHistory.currentUserEmojis

            val result = if (!emojiTypes.contains("LIKE")) {
                missionRepository.likeMissionHistory(missionHistoryId)
            } else {
                missionRepository.unlikeMissionHistory(missionHistoryId)
            }

            result.onSuccess {
                _missionHistoryRefreshTrigger.emit(Unit)
            }
        }
    }

    fun hateMissionHistory(missionHistory: MissionHistoryUiModel) {
        viewModelScope.launch {
            val missionHistoryId = missionHistory.missionHistoryId
            val emojiTypes = missionHistory.currentUserEmojis

            val result = if (!emojiTypes.contains("HATE")) {
                missionRepository.hateMissionHistory(missionHistoryId)
            } else {
                missionRepository.unhateMissionHistory(missionHistoryId)
            }

            result.onSuccess {
                _missionHistoryRefreshTrigger.emit(Unit)
            }
        }
    }

    fun reportMissionHistory(missionHistory: MissionHistoryUiModel) {
        viewModelScope.launch {
            runCatching {
                missionRepository.reportMissionHistory(missionHistory.missionHistoryId)
            }.onSuccess {
                _missionHistoryRefreshTrigger.emit(Unit)
            }
        }
    }
}