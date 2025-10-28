package com.ilsangtech.ilsang.feature.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ilsangtech.ilsang.core.domain.MissionRepository
import com.ilsangtech.ilsang.feature.profile.model.ChallengeDetailUiState
import com.ilsangtech.ilsang.feature.profile.navigation.ChallengeRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class ChallengeViewModel(
    savedStateHandle: SavedStateHandle,
    private val missionRepository: MissionRepository
) : ViewModel() {
    private val missionHistoryId = savedStateHandle.toRoute<ChallengeRoute>().missionHistoryId

    val challengeDetailUiState = flow<ChallengeDetailUiState> {
        val data = missionRepository.getUserMissionHistoryDetail(missionHistoryId)
        emit(ChallengeDetailUiState.Success(data))
    }.catch {
        emit(ChallengeDetailUiState.Error)
    }.stateIn(
        scope = viewModelScope,
        initialValue = ChallengeDetailUiState.Loading,
        started = SharingStarted.WhileSubscribed(5000),
    )
}