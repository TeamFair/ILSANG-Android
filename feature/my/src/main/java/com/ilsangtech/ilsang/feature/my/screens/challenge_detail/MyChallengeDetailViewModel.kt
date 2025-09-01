package com.ilsangtech.ilsang.feature.my.screens.challenge_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ilsangtech.ilsang.core.domain.ChallengeRepository
import com.ilsangtech.ilsang.feature.my.navigation.MyChallengeDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyChallengeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val challengeRepository: ChallengeRepository
) : ViewModel() {
    val challengeUiState = flow {
        val challenge = savedStateHandle.toRoute<MyChallengeDetailRoute>()
        emit(
            MyChallengeDetailUiState(
                title = challenge.title,
                challengeId = challenge.challengeId,
                receiptImageId = challenge.receiptImageId,
                questImageId = challenge.questImageId,
                likeCount = challenge.likeCount
            )
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    private val _isChallengeDeleteSuccess = MutableStateFlow<Boolean?>(null)
    val isChallengeDeleteSuccess = _isChallengeDeleteSuccess.asStateFlow()


    fun deleteChallenge() {
        viewModelScope.launch {
            challengeRepository.deleteChallenge(
                challengeUiState.value!!.challengeId
            ).onSuccess {
                _isChallengeDeleteSuccess.update { true }
            }.onFailure {
                _isChallengeDeleteSuccess.update { false }
            }
        }
    }
}