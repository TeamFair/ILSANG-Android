package com.ilsangtech.ilsang.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilsangtech.ilsang.core.domain.AreaRepository
import com.ilsangtech.ilsang.core.domain.BannerRepository
import com.ilsangtech.ilsang.core.domain.QuestCompleteDateRepository
import com.ilsangtech.ilsang.core.domain.QuestRepository
import com.ilsangtech.ilsang.core.domain.RankRepository
import com.ilsangtech.ilsang.core.domain.SeasonRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.model.user.MyInfo
import com.ilsangtech.ilsang.feature.home.model.HomeTabSuccessData
import com.ilsangtech.ilsang.feature.home.model.HomeTabUiState
import com.ilsangtech.ilsang.feature.home.model.MyInfoUiModel
import com.ilsangtech.ilsang.feature.home.model.toOpenSeasonUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val seasonRepository: SeasonRepository,
    private val areaRepository: AreaRepository,
    private val bannerRepository: BannerRepository,
    private val questRepository: QuestRepository,
    private val rankRepository: RankRepository,
    private val questCompleteDateRepository: QuestCompleteDateRepository
) : ViewModel() {
    private val _shouldShowSeasonOpenDialog = MutableStateFlow<Boolean?>(null)
    val shouldShowSeasonOpenDialog = _shouldShowSeasonOpenDialog.asStateFlow()

    private val _myInfo = userRepository.getMyInfo()

    @OptIn(ExperimentalCoroutinesApi::class)
    val homeTabUiState: StateFlow<HomeTabUiState> =
        _myInfo.flatMapLatest<MyInfo, HomeTabUiState> { myInfo ->
            if (shouldShowSeasonOpenDialog.value == null) {
                _shouldShowSeasonOpenDialog.update { myInfo.shouldShowSeasonOpenDialog }
            }
            val myAreaCode = myInfo.myCommericalAreaCode
            val isAreaCode = myInfo.isCommercialAreaCode

            val myCommercialAreaName =
                areaRepository.getCommercialArea(myAreaCode).areaName
            val isCommercialAreaName = isAreaCode?.let {
                areaRepository.getCommercialArea(isAreaCode).areaName
            }

            val bannersFlow = bannerRepository.getBanners()
            val popularFlow =
                questRepository.getPopularQuests(myAreaCode)
            val recommendedFlow =
                questRepository.getRecommendedQuests(myAreaCode)
            val largeRewardFlow =
                questRepository.getLargeRewardQuests(
                    commercialAreaCode = myAreaCode,
                    isZoneCode = isAreaCode
                )
            val topRankUsersFlow =
                rankRepository.getTotalTopRankUsers(myAreaCode)

            questCompleteDateRepository.questCompleteDateMapFlow.flatMapLatest {
                combine(
                    bannersFlow,
                    popularFlow,
                    recommendedFlow,
                    largeRewardFlow,
                    topRankUsersFlow
                ) { banners, popular, recommended, largeReward, topRank ->
                    HomeTabUiState.Success(
                        HomeTabSuccessData(
                            myInfo = MyInfoUiModel(
                                nickname = myInfo.nickname,
                                profileImageId = myInfo.profileImageId,
                                myCommercialAreaName = myCommercialAreaName,
                                isCommericalAreaName = isCommercialAreaName
                            ),
                            season = seasonRepository.getCurrentSeason().toOpenSeasonUiModel(),
                            banners = banners,
                            popularQuests = popular,
                            recommendedQuests = recommended,
                            largeRewardQuests = largeReward,
                            topRankUsers = topRank
                        )
                    )
                }
            }
        }
            .catch { e -> emit(HomeTabUiState.Error(e)) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = HomeTabUiState.Loading
            )

    fun seasonOpenDialogShown(checked: Boolean) {
        viewModelScope.launch {
            if (checked) {
                userRepository.updateSeasonOpenDialogRejected(true)
            }
            _shouldShowSeasonOpenDialog.update { false }
        }
    }
}