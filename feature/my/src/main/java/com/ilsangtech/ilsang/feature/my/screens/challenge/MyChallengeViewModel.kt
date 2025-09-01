package com.ilsangtech.ilsang.feature.my.screens.challenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.ilsangtech.ilsang.core.domain.MissionRepository
import com.ilsangtech.ilsang.core.model.mission.UserMissionHistory
import com.ilsangtech.ilsang.core.ui.mission.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MyChallengeViewModel @Inject constructor(
    missionRepository: MissionRepository
) : ViewModel() {
    val myMissionHistories = missionRepository.getUserMissionHistory()
        .cachedIn(viewModelScope)
        .map { pagingData ->
            pagingData.map(UserMissionHistory::toUiModel)
        }
}