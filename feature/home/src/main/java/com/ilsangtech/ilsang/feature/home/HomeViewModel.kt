package com.ilsangtech.ilsang.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilsangtech.ilsang.core.domain.BannerRepository
import com.ilsangtech.ilsang.core.domain.QuestRepository
import com.ilsangtech.ilsang.core.domain.RankRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.feature.home.home.HomeTapSuccessData
import com.ilsangtech.ilsang.feature.home.home.HomeTapUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val bannerRepository: BannerRepository,
    private val questRepository: QuestRepository,
    private val rankRepository: RankRepository
) : ViewModel() {
    val userNickname: StateFlow<String?> = flow {
        emit(userRepository.currentUser?.nickname)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val homeTapUiState: StateFlow<HomeTapUiState> = combine(
        flow { emit(bannerRepository.getBanners()) },
        flow { emit(questRepository.getPopularQuests()) },
        flow { emit(questRepository.getRecommendedQuests()) },
        flow { emit(questRepository.getLargeRewardQuests()) },
        flow { emit(rankRepository.getTopRankUsers()) }
    ) { banners, popularQuests, recommendedQuests, largeRewardQuests, topRankUsers ->
        HomeTapUiState.Success(
            data = HomeTapSuccessData(
                banners = banners,
                popularQuests = popularQuests,
                recommendedQuests = recommendedQuests,
                largeRewardQuests = largeRewardQuests,
                topRankUsers = topRankUsers
            )
        )
    }.catch {
        HomeTapUiState.Error(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeTapUiState.Loading
    )
}