package com.ilsangtech.ilsang.feature.my.screens.challenge_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ilsangtech.ilsang.core.domain.ChallengeRepository
import com.ilsangtech.ilsang.feature.my.navigation.MyChallengeDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyChallengeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val challengeRepository: ChallengeRepository
) : ViewModel() {
    private val challengeData = savedStateHandle.toRoute<MyChallengeDetailRoute>()
    val challengeUiState = MyChallengeDetailUiState(
        title = challengeData.title,
        challengeId = challengeData.challengeId,
        receiptImageId = challengeData.receiptImageId,
        questImageId = challengeData.questImageId,
        likeCount = challengeData.likeCount
    )

    private val _isChallengeDeleteSuccess = MutableStateFlow<Boolean?>(null)
    val isChallengeDeleteSuccess = _isChallengeDeleteSuccess.asStateFlow()


    fun deleteChallenge() {
        viewModelScope.launch {
            challengeRepository.deleteChallenge(
                challengeUiState.challengeId
            ).onSuccess {
                _isChallengeDeleteSuccess.update { true }
            }.onFailure {
                _isChallengeDeleteSuccess.update { false }
            }
        }
    }
}