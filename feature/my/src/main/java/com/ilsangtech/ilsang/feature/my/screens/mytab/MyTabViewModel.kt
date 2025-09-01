package com.ilsangtech.ilsang.feature.my.screens.mytab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilsangtech.ilsang.core.domain.AreaRepository
import com.ilsangtech.ilsang.core.domain.SeasonRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.model.season.Season
import com.ilsangtech.ilsang.core.ui.season.model.SeasonUiModel
import com.ilsangtech.ilsang.core.ui.season.model.toUiModel
import com.ilsangtech.ilsang.core.ui.user.model.UserCommercialPointUiModel
import com.ilsangtech.ilsang.core.ui.user.model.toUiModel
import com.ilsangtech.ilsang.core.util.XpLevelCalculator
import com.ilsangtech.ilsang.feature.my.screens.mytab.model.MyProfileInfoUiModel
import com.ilsangtech.ilsang.feature.my.screens.mytab.model.MyTabScreenUiState
import com.ilsangtech.ilsang.feature.my.screens.mytab.model.toMyPointSummaryUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MyTabViewModel @Inject constructor(
    userRepository: UserRepository,
    areaRepository: AreaRepository,
    seasonRepository: SeasonRepository
) : ViewModel() {
    private val _selectedSeason = MutableStateFlow<SeasonUiModel>(SeasonUiModel.Total)
    val selectedSeason = _selectedSeason.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val myTabScreenUiState = combine(
        userRepository.getMyInfo(),
        selectedSeason.flatMapLatest { season ->
            val seasonId = (season as? SeasonUiModel.Specific)?.id
            userRepository.getUserPoint(seasonId = seasonId)
        },
        userRepository.getUserCommercialPoint(),
        flow { emit(seasonRepository.getCurrentSeason()) }.flatMapLatest { season ->
            userRepository.getUserPointSummary(season.id)
        }
    ) { myInfo, userPoint, userCommercialPoint, userPointSummary ->
        MyTabScreenUiState.Success(
            myProfileInfo = MyProfileInfoUiModel(
                nickname = myInfo.nickname,
                profileImageId = myInfo.profileImageId,
                levelProgress = XpLevelCalculator.getLevelProgress(myInfo.totalPoint),
                level = XpLevelCalculator.getCurrentLevel(myInfo.totalPoint),
                title = myInfo.title
            ),
            myCommercialPoint = UserCommercialPointUiModel(
                nickname = myInfo.nickname,
                topCommercialArea = userCommercialPoint.topCommercialArea?.let {
                    it.toUiModel(
                        areaName = areaRepository.getCommercialArea(
                            it.commercialAreaCode
                        ).areaName
                    )
                },
                totalOwnerContributions = userCommercialPoint.totalOwnerContributions.map {
                    it.toUiModel(
                        areaName = areaRepository.getCommercialArea(
                            it.commercialAreaCode
                        ).areaName
                    )
                }
            ),
            myObtainedPoint = userPoint.toUiModel(
                seasonList = listOf(SeasonUiModel.Total) +
                        seasonRepository.getSeasonList().map(Season::toUiModel)
            ),
            myPointSummary = userPointSummary.toMyPointSummaryUiModel(
                nickName = myInfo.nickname,
                season = seasonRepository.getCurrentSeason(),
                changeCodeToMetroName = { areaCode ->
                    areaRepository.getMetroArea(areaCode).areaName
                },
                changeCodeToCommercialName = { areaCode ->
                    areaRepository.getCommercialArea(areaCode).areaName
                }
            )
        ) as MyTabScreenUiState
    }.catch { e ->
        emit(MyTabScreenUiState.Error(e.message.orEmpty()))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(0),
        initialValue = MyTabScreenUiState.Loading
    )

    fun updateSeason(season: SeasonUiModel) {
        _selectedSeason.update { season }
    }
}