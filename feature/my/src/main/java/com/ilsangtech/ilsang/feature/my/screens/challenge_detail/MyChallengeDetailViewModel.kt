package com.ilsangtech.ilsang.feature.my.screens.challenge_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ilsangtech.ilsang.core.domain.MissionRepository
import com.ilsangtech.ilsang.feature.my.navigation.MyChallengeDetailRoute
import com.ilsangtech.ilsang.feature.my.screens.challenge_detail.model.MyChallengeDetailUiState
import com.ilsangtech.ilsang.feature.my.screens.challenge_detail.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyChallengeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val missionRepository: MissionRepository
) : ViewModel() {
    private val missionHistoryId =
        savedStateHandle.toRoute<MyChallengeDetailRoute>().missionHistoryId
    val challengeUiState = flow<MyChallengeDetailUiState> {
        val uiModel = missionRepository.getUserMissionHistoryDetail(missionHistoryId).toUiModel()
        emit(MyChallengeDetailUiState.Success(uiModel))
    }.catch {
        emit(MyChallengeDetailUiState.Error)
    }.stateIn(
        scope = viewModelScope,
        initialValue = MyChallengeDetailUiState.Loading,
        started = SharingStarted.WhileSubscribed(5000)
    )

    private val _isChallengeDeleteSuccess = MutableStateFlow<Boolean?>(null)
    val isChallengeDeleteSuccess = _isChallengeDeleteSuccess.asStateFlow()


    fun deleteChallenge() {
        viewModelScope.launch {
            val missionHistoryId =
                (challengeUiState.value as? MyChallengeDetailUiState.Success)?.data?.missionHistoryId
            missionHistoryId?.let { id ->
                missionRepository.deleteMissionHistory(id)
                    .onSuccess {
                        _isChallengeDeleteSuccess.update { true }
                    }.onFailure {
                        _isChallengeDeleteSuccess.update { false }
                    }
            }
        }
    }
}