package com.ilsangtech.ilsang.feature.home.approval

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.ilsangtech.ilsang.core.domain.ChallengeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ApprovalViewModel @Inject constructor(
    private val challengeRepository: ChallengeRepository
) : ViewModel() {
    val randomChallenges = Pager(
        PagingConfig(
            pageSize = 10,
            initialLoadSize = 10
        )
    ) {
        challengeRepository.randomChallengePagingSource
    }.flow.cachedIn(viewModelScope)
}