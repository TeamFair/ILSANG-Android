package com.ilsangtech.ilsang.feature.my.screens.challenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.ilsangtech.ilsang.core.domain.MissionRepository
import com.ilsangtech.ilsang.core.model.mission.MissionType
import com.ilsangtech.ilsang.core.model.mission.UserMissionHistory
import com.ilsangtech.ilsang.core.ui.mission.model.MissionTypes
import com.ilsangtech.ilsang.core.ui.mission.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MyChallengeViewModel @Inject constructor(
    missionRepository: MissionRepository
) : ViewModel() {
    private val _missionType = MutableStateFlow(MissionTypes.IMAGE)
    val missionType = _missionType.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val myMissionHistories = _missionType.flatMapLatest { missionType ->
        missionRepository.getUserMissionHistory(
            missionType = when (missionType) {
                MissionTypes.IMAGE -> MissionType.Photo
                MissionTypes.OX -> MissionType.Ox
                MissionTypes.WORDS -> MissionType.Words
            }
        )
    }.cachedIn(viewModelScope)
        .map { pagingData ->
            pagingData.map(UserMissionHistory::toUiModel)
        }

    fun updateMissionType(missionType: MissionTypes) {
        _missionType.update { missionType }
    }
}