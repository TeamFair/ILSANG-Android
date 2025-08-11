package com.ilsangtech.ilsang.feature.ranking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilsangtech.ilsang.core.domain.RankRepository
import com.ilsangtech.ilsang.core.model.RewardType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RankingTabViewModel @Inject constructor(
    private val rankRepository: RankRepository
) : ViewModel() {
    val rankingUiState = flow {
        emit(
            RewardType.entries.associateWith { rankRepository.getXpTypeRank(it) }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyMap()
    )
}