package com.ilsangtech.ilsang.feature.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.cachedIn
import com.ilsangtech.ilsang.core.domain.ChallengeRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.feature.profile.navigation.ProfileRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository,
    challengeRepository: ChallengeRepository
) : ViewModel() {
    private val userId: String = savedStateHandle.toRoute<ProfileRoute>().userId
    val profileUiState: StateFlow<ProfileUiState> = flow {
        val userInfoResult = userRepository.getUserInfo(userId)
        val userStatsResult = userRepository.getUserXpStats(userId)
        val uiState = userInfoResult.fold(
            onSuccess = {
                ProfileUiState.Success(
                    it,
                    userStatsResult
                )
            },
            onFailure = { ProfileUiState.Error }
        )
        emit(uiState)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ProfileUiState.Loading
    )

    val challengePaging = challengeRepository
        .getChallengePaging(userId)
        .cachedIn(viewModelScope)
}