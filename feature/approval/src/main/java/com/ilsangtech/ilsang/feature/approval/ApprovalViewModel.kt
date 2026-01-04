package com.ilsangtech.ilsang.feature.approval

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.ilsangtech.ilsang.core.domain.AreaRepository
import com.ilsangtech.ilsang.core.domain.MissionRepository
import com.ilsangtech.ilsang.core.domain.QuestRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.ui.quest.model.toUiModel
import com.ilsangtech.ilsang.feature.approval.model.MissionHistoryUiModel
import com.ilsangtech.ilsang.feature.approval.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApprovalViewModel @Inject constructor(
    areaRepository: AreaRepository,
    userRepository: UserRepository,
    private val missionRepository: MissionRepository,
    private val questRepository: QuestRepository
) : ViewModel() {
    private val _missionHistoryRefreshTrigger = MutableSharedFlow<Unit>(replay = 1)
    val missionHistoryRefreshTrigger = _missionHistoryRefreshTrigger.asSharedFlow()

    private val likeMissionHistorySet = MutableStateFlow(setOf<Int>())
    private val hateMissionHistorySet = MutableStateFlow(setOf<Int>())

    val randomMissionHistories = combine(
        userRepository.getMyInfo(),
        missionRepository.getRandomMissionHistory()
    ) { myInfo, pagingData ->
        pagingData.map { randomMissionHistory ->
            if (randomMissionHistory.currentUserEmojis.contains("LIKE")) {
                likeMissionHistorySet.update { it + randomMissionHistory.missionHistoryId }
            }
            if (randomMissionHistory.currentUserEmojis.contains("HATE")) {
                hateMissionHistorySet.update { it + randomMissionHistory.missionHistoryId }
            }

            val commercialArea =
                areaRepository.getCommercialArea(randomMissionHistory.commercialAreaCode)
            randomMissionHistory.toUiModel(
                isIsZoneQuest = myInfo.isCommercialAreaCode == commercialArea.code,
                areaName = commercialArea.areaName
            )
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

    private val _selectedQuestId = MutableStateFlow<Int?>(null)
    private val questDetailRefreshTrigger = MutableSharedFlow<Unit>(replay = 1)

    @OptIn(ExperimentalCoroutinesApi::class)
    val questDetail = combine(
        _selectedQuestId,
        questDetailRefreshTrigger.onStart { emit(Unit) }
    ) { questId, _ -> questId }.flatMapLatest { questId ->
        questId?.let {
            questRepository.getQuestDetail(questId).map { it.toUiModel() }
        } ?: flowOf(null)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

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

    fun selectQuest(questId: Int) {
        _selectedQuestId.update { questId }
    }

    fun unselectQuest() {
        _selectedQuestId.update { null }
    }

    fun updateQuestFavoriteStatus() {
        viewModelScope.launch {
            questDetail.value?.let { questDetail ->
                if (!questDetail.favoriteYn) {
                    questRepository.registerFavoriteQuest(questDetail.id).onSuccess {
                        questDetailRefreshTrigger.emit(Unit)
                    }
                } else {
                    questRepository.deleteFavoriteQuest(questDetail.id).onSuccess {
                        questDetailRefreshTrigger.emit(Unit)
                    }
                }
            }
        }
    }
}