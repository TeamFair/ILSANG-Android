package com.ilsangtech.ilsang.feature.my.screens.challenge_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ilsangtech.ilsang.core.domain.MissionRepository
import com.ilsangtech.ilsang.core.ui.mission.model.UserMissionHistoryUiModel
import com.ilsangtech.ilsang.core.ui.mission.model.userMissionHistoryNavType
import com.ilsangtech.ilsang.feature.my.navigation.MyChallengeDetailRoute
import com.ilsangtech.ilsang.feature.my.screens.challenge_detail.model.MyChallengeDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.typeOf

@HiltViewModel
class MyChallengeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val missionRepository: MissionRepository
) : ViewModel() {
    private val challengeData =
        savedStateHandle.toRoute<MyChallengeDetailRoute>(
            typeMap = mapOf(typeOf<UserMissionHistoryUiModel>() to userMissionHistoryNavType)
        ).userMissionHistory
    val challengeUiState = MyChallengeDetailUiState(
        title = challengeData.title,
        missionHistoryId = challengeData.missionHistoryId,
        submitImageId = challengeData.submitImageId,
        questImageId = challengeData.questImageId,
        likeCount = challengeData.likeCount
    )

    private val _isChallengeDeleteSuccess = MutableStateFlow<Boolean?>(null)
    val isChallengeDeleteSuccess = _isChallengeDeleteSuccess.asStateFlow()


    fun deleteChallenge() {
        viewModelScope.launch {
            missionRepository.deleteMissionHistory(
                missionHistoryId = challengeUiState.missionHistoryId
            ).onSuccess {
                _isChallengeDeleteSuccess.update { true }
            }.onFailure {
                _isChallengeDeleteSuccess.update { false }
            }
        }
    }
}