package com.ilsangtech.ilsang.feature.my

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ilsangtech.ilsang.core.domain.ChallengeRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.model.UserXpStats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MyTabViewModel @Inject constructor(
    private val userRepository: UserRepository,
    challengeRepository: ChallengeRepository
) : ViewModel() {
    val myInfo = userRepository.getMyInfo().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = null
    )

    val challengePager = challengeRepository.getChallengePaging().cachedIn(viewModelScope)

    val userXpStats = flow {
        emit(userRepository.getUserXpStats())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UserXpStats()
    )
}