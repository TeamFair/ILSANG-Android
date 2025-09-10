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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApprovalViewModel @Inject constructor(
    areaRepository: AreaRepository,
    private val missionRepository: MissionRepository
) : ViewModel() {
    private val _missionHistoryRefreshTrigger = MutableSharedFlow<Unit>(replay = 1)
    val missionHistoryRefreshTrigger = _missionHistoryRefreshTrigger.asSharedFlow()

    private val likeMissionHistorySet = MutableStateFlow(setOf<Int>())
    private val hateMissionHistorySet = MutableStateFlow(setOf<Int>())

    val randomMissionHistories = missionRepository.getRandomMissionHistory().map { pagingData ->
        pagingData.map { randomMissionHistory ->
            if (randomMissionHistory.currentUserEmojis.contains("LIKE")) {
                likeMissionHistorySet.update { it + randomMissionHistory.missionHistoryId }
            }
            if (randomMissionHistory.currentUserEmojis.contains("HATE")) {
                hateMissionHistorySet.update { it + randomMissionHistory.missionHistoryId }
            }

            val commercialArea =
                areaRepository.getCommercialArea(randomMissionHistory.commercialAreaCode)
            randomMissionHistory.toUiModel(commercialArea.areaName)
        }
    }.cachedIn(viewModelScope)
        .combine(likeMissionHistorySet) { pagingData, likeMissionHistorySet ->
            pagingData.map { missionHistory ->
                when {
                    likeMissionHistorySet.contains(missionHistory.missionHistoryId)
                            && !missionHistory.currentUserEmojis.contains("LIKE") -> {
                        missionHistory.copy(
                            currentUserEmojis = missionHistory.currentUserEmojis + "LIKE",
                            likeCount = missionHistory.likeCount + 1
                        )
                    }

                    !likeMissionHistorySet.contains(missionHistory.missionHistoryId)
                            && missionHistory.currentUserEmojis.contains("LIKE") -> {
                        missionHistory.copy(
                            currentUserEmojis = missionHistory.currentUserEmojis - "LIKE",
                            likeCount = missionHistory.likeCount - 1
                        )
                    }

                    else -> {
                        missionHistory
                    }
                }
            }
        }
        .combine(hateMissionHistorySet) { pagingData, hateMissionHistorySet ->
            pagingData.map { missionHistory ->
                when {
                    hateMissionHistorySet.contains(missionHistory.missionHistoryId)
                            && !missionHistory.currentUserEmojis.contains("HATE") -> {
                        missionHistory.copy(
                            currentUserEmojis = missionHistory.currentUserEmojis + "HATE",
                            hateCount = missionHistory.hateCount + 1
                        )
                    }

                    !hateMissionHistorySet.contains(missionHistory.missionHistoryId)
                            && missionHistory.currentUserEmojis.contains("HATE") -> {
                        missionHistory.copy(
                            currentUserEmojis = missionHistory.currentUserEmojis - "HATE",
                            hateCount = missionHistory.hateCount - 1
                        )
                    }

                    else -> {
                        missionHistory
                    }
                }
            }
        }

    fun likeChallenge(missionHistory: MissionHistoryUiModel) {
        viewModelScope.launch {
            val missionHistoryId = missionHistory.missionHistoryId
            val emojiTypes = missionHistory.currentUserEmojis

            if (!emojiTypes.contains("LIKE")) {
                missionRepository.likeMissionHistory(missionHistoryId).onSuccess {
                    likeMissionHistorySet.update { it + missionHistoryId }
                }
            } else {
                missionRepository.unlikeMissionHistory(missionHistoryId).onSuccess {
                    likeMissionHistorySet.update { it - missionHistoryId }
                }
            }
        }
    }

    fun hateChallenge(missionHistory: MissionHistoryUiModel) {
        viewModelScope.launch {
            val missionHistoryId = missionHistory.missionHistoryId
            val emojiTypes = missionHistory.currentUserEmojis

            if (!emojiTypes.contains("HATE")) {
                missionRepository.hateMissionHistory(missionHistoryId).onSuccess {
                    hateMissionHistorySet.update { it + missionHistoryId }
                }
            } else {
                missionRepository.unhateMissionHistory(missionHistoryId).onSuccess {
                    hateMissionHistorySet.update { it - missionHistoryId }
                }
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