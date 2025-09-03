package com.ilsangtech.ilsang.feature.ranking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilsangtech.ilsang.core.domain.RankRepository
import com.ilsangtech.ilsang.core.domain.SeasonRepository
import com.ilsangtech.ilsang.core.model.rank.CommercialAreaRank
import com.ilsangtech.ilsang.core.model.rank.MetroAreaRank
import com.ilsangtech.ilsang.core.model.rank.UserRank
import com.ilsangtech.ilsang.core.model.season.Season
import com.ilsangtech.ilsang.feature.ranking.model.RankingTabUiState
import com.ilsangtech.ilsang.feature.ranking.model.SeasonUiModel
import com.ilsangtech.ilsang.feature.ranking.model.toAreaRankUiModel
import com.ilsangtech.ilsang.feature.ranking.model.toSeasonUiModel
import com.ilsangtech.ilsang.feature.ranking.model.toUserRankUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingTabViewModel @Inject constructor(
    private val seasonRepository: SeasonRepository,
    private val rankRepository: RankRepository
) : ViewModel() {
    private val refreshTrigger = MutableSharedFlow<Unit>(replay = 1)

    private val _selectedSeason = MutableStateFlow<SeasonUiModel>(SeasonUiModel.Total)
    val selectedSeason = _selectedSeason.asStateFlow()

    val seasonList = refreshTrigger.map {
        listOf(SeasonUiModel.Total) + seasonRepository.getSeasonList()
            .map(Season::toSeasonUiModel)
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val currentSeason = refreshTrigger.map {
        seasonRepository.getCurrentSeason().toSeasonUiModel()
    }
        .catch { }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val rankingTabUiState =
        selectedSeason.flatMapLatest { selectedSeason ->
            val seasonId = (selectedSeason as? SeasonUiModel.Season)?.seasonId
            val metroRankAreas = rankRepository.getMetroTopRankAreas(seasonId)
            val commercialRankAreas = rankRepository.getCommercialTopRankAreas(seasonId)
            val contributionRankUsers = rankRepository.getContributionTopRankUsers(seasonId)

            combine(
                metroRankAreas,
                commercialRankAreas,
                contributionRankUsers
            ) { metroRankAreas, commercialRankAreas, contributionRankUsers ->
                RankingTabUiState.Success(
                    metroRankAreas = metroRankAreas.map(MetroAreaRank::toAreaRankUiModel),
                    commercialRankAreas = commercialRankAreas.map(CommercialAreaRank::toAreaRankUiModel),
                    contributionRankUsers = contributionRankUsers.map(UserRank::toUserRankUiModel)
                ) as RankingTabUiState
            }
        }
            .catch { emit(RankingTabUiState.Error) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = RankingTabUiState.Loading
            )

    init {
        viewModelScope.launch {
            try {
                _selectedSeason.update {
                    seasonRepository.getCurrentSeason().toSeasonUiModel()
                }
            } catch (_: Exception) {
                _selectedSeason.update { SeasonUiModel.Total }
            }
            refreshTrigger.emit(Unit)
        }
    }

    fun updateSelectedSeason(seasonUiModel: SeasonUiModel) {
        _selectedSeason.update { seasonUiModel }
    }

    fun refreshSeason() {
        viewModelScope.launch {
            seasonRepository.getSeasonList(refresh = true)
            refreshTrigger.emit(Unit)
        }
    }
}