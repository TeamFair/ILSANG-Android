package com.ilsangtech.ilsang.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilsangtech.ilsang.core.domain.AreaRepository
import com.ilsangtech.ilsang.core.domain.BannerRepository
import com.ilsangtech.ilsang.core.domain.QuestRepository
import com.ilsangtech.ilsang.core.domain.RankRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.model.MyInfo
import com.ilsangtech.ilsang.feature.home.model.HomeTabSuccessData
import com.ilsangtech.ilsang.feature.home.model.HomeTabUiState
import com.ilsangtech.ilsang.feature.home.model.MyInfoUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    userRepository: UserRepository,
    private val areaRepository: AreaRepository,
    private val bannerRepository: BannerRepository,
    private val questRepository: QuestRepository,
    private val rankRepository: RankRepository
) : ViewModel() {
    private val questDetailRefreshTrigger = MutableSharedFlow<Unit>(replay = 1)

    private val _selectedQuestId = MutableStateFlow<Int?>(null)
    private val selectedQuestId = _selectedQuestId.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val homeTabUiState: StateFlow<HomeTabUiState> =
        userRepository.getMyInfo().flatMapLatest<MyInfo, HomeTabUiState> { myInfo ->
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
                questRepository.getLargeRewardQuests(myAreaCode)
            val topRankUsersFlow =
                rankRepository.getTotalTopRankUsers(myAreaCode)

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
                        banners = banners,
                        popularQuests = popular,
                        recommendedQuests = recommended,
                        largeRewardQuests = largeReward,
                        topRankUsers = topRank
                    )
                )
            }
        }
            .catch { e -> emit(HomeTabUiState.Error(e)) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = HomeTabUiState.Loading
            )

    @OptIn(ExperimentalCoroutinesApi::class)
    val selectedQuest = combine(
        selectedQuestId,
        questDetailRefreshTrigger.onStart { emit(Unit) }
    ) { questId, _ -> questId }.flatMapLatest { questId ->
        questId?.let { questRepository.getQuestDetail(questId) } ?: flowOf(null)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    fun selectQuest(questId: Int) {
        _selectedQuestId.update { questId }
    }

    fun unselectQuest() {
        _selectedQuestId.update { null }
    }

    fun updateQuestFavoriteStatus() {
        viewModelScope.launch {
            selectedQuest.value?.let { quest ->
                val result = if (quest.favoriteYn) {
                    questRepository.deleteFavoriteQuest(quest.id)
                } else {
                    questRepository.registerFavoriteQuest(quest.id)
                }
                result.onSuccess { questDetailRefreshTrigger.emit(Unit) }
            }
        }
    }
}