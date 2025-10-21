package com.ilsangtech.ilsang.feature.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.cachedIn
import androidx.paging.map
import com.ilsangtech.ilsang.core.domain.AreaRepository
import com.ilsangtech.ilsang.core.domain.MissionRepository
import com.ilsangtech.ilsang.core.domain.SeasonRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.model.mission.MissionType
import com.ilsangtech.ilsang.core.model.season.Season
import com.ilsangtech.ilsang.core.ui.mission.model.MissionTypes
import com.ilsangtech.ilsang.core.ui.mission.model.toUiModel
import com.ilsangtech.ilsang.core.ui.season.model.SeasonUiModel
import com.ilsangtech.ilsang.core.ui.season.model.toUiModel
import com.ilsangtech.ilsang.core.ui.user.model.UserCommercialPointUiModel
import com.ilsangtech.ilsang.core.ui.user.model.toUiModel
import com.ilsangtech.ilsang.feature.profile.model.ProfileUiState
import com.ilsangtech.ilsang.feature.profile.model.toUserProfileInfoUiModel
import com.ilsangtech.ilsang.feature.profile.navigation.ProfileRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    userRepository: UserRepository,
    seasonRepository: SeasonRepository,
    areaRepository: AreaRepository,
    missionRepository: MissionRepository
) : ViewModel() {
    private val userId: String = savedStateHandle.toRoute<ProfileRoute>().userId

    private val _selectedSeason = MutableStateFlow<SeasonUiModel>(SeasonUiModel.Total)
    val selectedSeason = _selectedSeason.asStateFlow()

    private val _missionType = MutableStateFlow(MissionTypes.IMAGE)
    val missionType = _missionType.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val missionHistories = _missionType.flatMapLatest { missionType ->
        missionRepository.getUserMissionHistory(
            userId = userId,
            missionType = when (missionType) {
                MissionTypes.IMAGE -> MissionType.Photo
                MissionTypes.OX -> MissionType.Ox
                MissionTypes.WORDS -> MissionType.Words
            }
        )
    }
        .cachedIn(viewModelScope)
        .map { pagingData ->
            pagingData.map { it.toUiModel() }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val profileUiState = combine(
        userRepository.getUserInfo(userId),
        userRepository.getUserCommercialPoint(userId),
        userRepository.getUserPoint(userId)
    ) { userInfo, userCommercialPoint, userTotalPoint ->
        Triple(userInfo, userCommercialPoint, userTotalPoint)
    }.flatMapLatest { (userInfo, userCommercialPoint, userTotalPoint) ->
        selectedSeason.flatMapLatest { season ->
            val seasonId = (season as? SeasonUiModel.Specific)?.id
            userRepository.getUserPoint(userId, seasonId).map { userPoint ->
                val totalPoint =
                    userTotalPoint.metroAreaPoint + userTotalPoint.commercialAreaPoint + userTotalPoint.contributionPoint
                ProfileUiState.Success(
                    userProfileInfo = userInfo.toUserProfileInfoUiModel(totalPoint),
                    userPoint = userPoint.toUiModel(
                        seasonList =
                            listOf(SeasonUiModel.Total) + seasonRepository.getSeasonList()
                                .map(Season::toUiModel)
                    ),
                    userCommercialPoint = UserCommercialPointUiModel(
                        nickname = userInfo.nickname,
                        topCommercialArea = userCommercialPoint.topCommercialArea?.let { topCommercialArea ->
                            topCommercialArea.toUiModel(
                                areaRepository.getCommercialArea(
                                    topCommercialArea.commercialAreaCode
                                ).areaName
                            )
                        },
                        totalOwnerContributions = userCommercialPoint.totalOwnerContributions.map {
                            it.toUiModel(
                                areaRepository.getCommercialArea(
                                    it.commercialAreaCode
                                ).areaName
                            )
                        }
                    )
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ProfileUiState.Loading
    )

    fun updateSeason(season: SeasonUiModel) {
        _selectedSeason.update { season }
    }

    fun updateMissionType(missionType: MissionTypes) {
        _missionType.update { missionType }
    }
}