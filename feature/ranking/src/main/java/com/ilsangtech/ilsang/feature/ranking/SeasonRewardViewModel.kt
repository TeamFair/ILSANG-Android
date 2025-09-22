package com.ilsangtech.ilsang.feature.ranking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilsangtech.ilsang.core.domain.TitleRepository
import com.ilsangtech.ilsang.core.model.title.TitleType
import com.ilsangtech.ilsang.feature.ranking.model.RewardUiModel
import com.ilsangtech.ilsang.feature.ranking.model.SeasonRewardScreenUiState
import com.ilsangtech.ilsang.feature.ranking.model.SeasonRewardTitleUiState
import com.ilsangtech.ilsang.feature.ranking.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SeasonRewardViewModel @Inject constructor(
    private val titleRepository: TitleRepository
) : ViewModel() {
    private val _selectedTitleType = MutableStateFlow(RewardUiModel.Metro)

    private val metroFlow = flow<SeasonRewardTitleUiState> {
        emit(
            SeasonRewardTitleUiState.Success(
                titleRepository.getSeasonRewardTitleList(TitleType.Metro)
                    .map { it.toUiModel() }
            )
        )
    }.catch {
        emit(SeasonRewardTitleUiState.Error)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = SeasonRewardTitleUiState.Loading
    )

    private val commercialFlow = flow<SeasonRewardTitleUiState> {
        emit(
            SeasonRewardTitleUiState.Success(
                titleRepository.getSeasonRewardTitleList(TitleType.Commercial)
                    .map { it.toUiModel() }
            )
        )
    }.catch {
        emit(SeasonRewardTitleUiState.Error)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = SeasonRewardTitleUiState.Loading
    )

    private val contributionFlow = flow<SeasonRewardTitleUiState> {
        emit(
            SeasonRewardTitleUiState.Success(
                titleRepository.getSeasonRewardTitleList(TitleType.Contribution)
                    .map { it.toUiModel() }
            )
        )
    }.catch {
        emit(SeasonRewardTitleUiState.Error)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = SeasonRewardTitleUiState.Loading
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState = _selectedTitleType.flatMapLatest { selectedType ->
        when (selectedType) {
            RewardUiModel.Metro -> metroFlow
            RewardUiModel.Commercial -> commercialFlow
            RewardUiModel.Contribution -> contributionFlow
        }.map { titles ->
            SeasonRewardScreenUiState(
                selectedType = selectedType,
                selectedRewardTitleState = titles
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SeasonRewardScreenUiState()
    )

    fun updateTitleType(titleType: RewardUiModel) {
        _selectedTitleType.update { titleType }
    }
}