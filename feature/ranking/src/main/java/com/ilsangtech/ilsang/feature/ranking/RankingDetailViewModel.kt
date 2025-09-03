package com.ilsangtech.ilsang.feature.ranking

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ilsangtech.ilsang.core.domain.RankRepository
import com.ilsangtech.ilsang.core.domain.SeasonRepository
import com.ilsangtech.ilsang.core.model.rank.UserRanksWithMyRank
import com.ilsangtech.ilsang.core.util.DateConverter
import com.ilsangtech.ilsang.feature.ranking.model.AreaRankUiModel
import com.ilsangtech.ilsang.feature.ranking.model.RankingDetailUiState
import com.ilsangtech.ilsang.feature.ranking.model.SeasonUiModel
import com.ilsangtech.ilsang.feature.ranking.model.toMyAreaRankUiModel
import com.ilsangtech.ilsang.feature.ranking.model.toUserRankUiModel
import com.ilsangtech.ilsang.feature.ranking.navigation.RankingDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val seasonRepository: SeasonRepository,
    rankRepository: RankRepository
) : ViewModel() {
    private val rankingDetailInfo = savedStateHandle.toRoute<RankingDetailRoute>()
    private val areaRankUiModel = AreaRankUiModel(
        areaCode = rankingDetailInfo.areaCode,
        areaName = rankingDetailInfo.areaName,
        rank = rankingDetailInfo.rank,
        point = rankingDetailInfo.point,
        images = rankingDetailInfo.images
    )

    private val userRanks = if (rankingDetailInfo.isMetro) {
        rankRepository.getMetroTopRankUsers(
            seasonId = rankingDetailInfo.seasonId,
            metroAreaCode = rankingDetailInfo.areaCode
        )
    } else {
        rankRepository.getCommercialTopRankUsers(
            seasonId = rankingDetailInfo.seasonId,
            commercialAreaCode = rankingDetailInfo.areaCode
        )
    }

    private val refreshTrigger = MutableSharedFlow<Unit>(replay = 1)

    @OptIn(ExperimentalCoroutinesApi::class)
    val rankingDetailUiState = refreshTrigger.flatMapLatest {
        userRanks.map<UserRanksWithMyRank, RankingDetailUiState> {
            val currentSeason = seasonRepository.getCurrentSeason()

            RankingDetailUiState.Success(
                currentSeason = SeasonUiModel.Season(
                    seasonId = currentSeason.id,
                    seasonNumber = currentSeason.seasonNumber,
                    startDate = DateConverter.formatDate(
                        input = currentSeason.startDate,
                        outputPattern = "yyyy-MM-dd"
                    ),
                    endDate = DateConverter.formatDate(
                        input = currentSeason.endDate,
                        outputPattern = "yyyy-MM-dd"
                    )
                ),
                areaRankUiModel = areaRankUiModel,
                myRankUiModel = it.myRank.toMyAreaRankUiModel(),
                userRankList = it.userRanks.map { userRank -> userRank.toUserRankUiModel() }
            )
        }
    }
        .catch { emit(RankingDetailUiState.Error) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RankingDetailUiState.Loading
        )

    init {
        viewModelScope.launch {
            refreshTrigger.emit(Unit)
        }
    }

    fun refreshSeason() {
        viewModelScope.launch {
            seasonRepository.getSeasonList(refresh = true)
            refreshTrigger.emit(Unit)
        }
    }
}