package com.ilsangtech.ilsang.feature.approval

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.ilsangtech.ilsang.core.domain.AreaRepository
import com.ilsangtech.ilsang.core.domain.MissionRepository
import com.ilsangtech.ilsang.feature.approval.model.MissionHistoryUiModel
import com.ilsangtech.ilsang.feature.approval.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApprovalViewModel @Inject constructor(
    areaRepository: AreaRepository,
    private val missionRepository: MissionRepository
) : ViewModel() {
    private val _missionHistoryRefreshTrigger = MutableSharedFlow<Unit>(replay = 1)
    val missionHistoryRefreshTrigger = _missionHistoryRefreshTrigger.asSharedFlow()

    val randomMissionHistories = missionRepository.getRandomMissionHistory().map { pagingData ->
        pagingData.map { randomMissionHistory ->
            val commercialArea =
                areaRepository.getCommercialArea(randomMissionHistory.commercialAreaCode)
            randomMissionHistory.toUiModel(commercialArea.areaName)
        }
    }.cachedIn(viewModelScope)

    fun likeChallenge(missionHistory: MissionHistoryUiModel) {
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

    fun hateChallenge(missionHistory: MissionHistoryUiModel) {
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