package com.ilsangtech.ilsang.feature.approval

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.cachedIn
import androidx.paging.map
import com.ilsangtech.ilsang.core.domain.AreaRepository
import com.ilsangtech.ilsang.core.domain.MissionRepository
import com.ilsangtech.ilsang.feature.approval.model.ExampleMissionHistoryUiModel
import com.ilsangtech.ilsang.feature.approval.model.toUiModel
import com.ilsangtech.ilsang.feature.approval.navigation.ApprovalExampleRoute
import com.ilsangtech.ilsang.feature.approval.navigation.questTypeMap
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
    private val missionInfo = savedStateHandle.toRoute<ApprovalExampleRoute>(questTypeMap)
    val missionId = missionInfo.missionId
    val questId = missionInfo.questId
    val isIsZoneQuest = missionInfo.isIsZoneQuest

    val questTitle = missionInfo.title
    val questWriterName = missionInfo.writerName
    val questType = missionInfo.questType

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

    fun likeMissionHistory(missionHistory: ExampleMissionHistoryUiModel) {
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
}