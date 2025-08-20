package com.ilsangtech.ilsang.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilsangtech.ilsang.core.domain.BannerRepository
import com.ilsangtech.ilsang.core.domain.QuestRepository
import com.ilsangtech.ilsang.core.domain.RankRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.model.MyInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val bannerRepository: BannerRepository,
    private val questRepository: QuestRepository,
    private val rankRepository: RankRepository
) : ViewModel() {
    private val _myInfo: MutableStateFlow<MyInfo?> =
        MutableStateFlow(userRepository.currentUser)
    val myInfo: StateFlow<MyInfo?> = _myInfo.asStateFlow()

    private val _selectedQuestId = MutableStateFlow<Int?>(null)
    private val selectedQuestId = _selectedQuestId.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val homeTabUiState: StateFlow<HomeTabUiState> =
        myInfo.flatMapLatest { state ->
            if (state == null) return@flatMapLatest flowOf(HomeTabUiState.Loading)

            val bannersFlow = flow { emit(bannerRepository.getBanners()) }
            val topRankUsersFlow = flow { emit(rankRepository.getTopRankUsers()) }

            val popularFlow =
                questRepository.getPopularQuests()
            val recommendedFlow =
                questRepository.getRecommendedQuests()
            val largeRewardFlow =
                questRepository.getLargeRewardQuests()

            combine(
                bannersFlow,
                popularFlow,
                recommendedFlow,
                largeRewardFlow,
                topRankUsersFlow
            ) { banners, popular, recommended, largeReward, topRank ->
                HomeTabUiState.Success(
                    HomeTabSuccessData(
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
    val selectedQuest = selectedQuestId.flatMapLatest { questId ->
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
                if (quest.favoriteYn) questRepository.deleteFavoriteQuest(quest.id)
                else questRepository.registerFavoriteQuest(quest.id)
            }
        }
    }
}