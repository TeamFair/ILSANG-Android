package com.ilsangtech.ilsang.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.ilsangtech.ilsang.core.domain.BannerRepository
import com.ilsangtech.ilsang.core.domain.ChallengeRepository
import com.ilsangtech.ilsang.core.domain.QuestRepository
import com.ilsangtech.ilsang.core.domain.RankRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.model.Challenge
import com.ilsangtech.ilsang.core.model.Quest
import com.ilsangtech.ilsang.core.model.QuestType
import com.ilsangtech.ilsang.core.model.RepeatQuestPeriod
import com.ilsangtech.ilsang.core.model.RewardType
import com.ilsangtech.ilsang.core.model.UserInfo
import com.ilsangtech.ilsang.feature.home.home.HomeTapSuccessData
import com.ilsangtech.ilsang.feature.home.home.HomeTapUiState
import com.ilsangtech.ilsang.feature.home.quest.QuestTabUiData
import com.ilsangtech.ilsang.feature.home.quest.QuestTabUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val rankRepository: RankRepository,
    private val challengeRepository: ChallengeRepository
) : ViewModel() {
    val userInfo: StateFlow<UserInfo?> = flow {
        emit(userRepository.currentUser)
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

    private var _selectedQuestType = MutableStateFlow(QuestType.NORMAL)
    val selectedQuestType = _selectedQuestType.asStateFlow()

    private var _selectedRewardType = MutableStateFlow(RewardType.STRENGTH)
    val selectedRewardType = _selectedRewardType.asStateFlow()

    private var _selectedRepeatPeriod = MutableStateFlow<RepeatQuestPeriod>(RepeatQuestPeriod.DAILY)
    val selectedRepeatPeriod = _selectedRepeatPeriod.asStateFlow()

    private var _selectedSortType = MutableStateFlow("포인트 높은 순")
    val selectedSortType = _selectedSortType.asStateFlow()

    val questTabUiState: StateFlow<QuestTabUiState> = combine(
        selectedQuestType, selectedRepeatPeriod
    ) { questType, repeatPeriod ->
        when (questType) {
            QuestType.NORMAL -> questRepository.getUncompletedNormalQuests()
            QuestType.REPEAT -> questRepository.getUncompletedRepeatQuests(
                when (repeatPeriod) {
                    RepeatQuestPeriod.DAILY -> "DAILY"
                    RepeatQuestPeriod.WEEKLY -> "WEEKLY"
                    RepeatQuestPeriod.MONTHLY -> "MONTHLY"
                }
            )

            QuestType.EVENT -> questRepository.getUncompletedEventQuests()
            else -> emptyList()
        }
    }
        .combine(selectedRewardType) { quests, rewardType ->
            quests.filter { quest ->
                quest.rewardList.find { it.content == rewardType.name } != null
            }
        }.combine<List<Quest>, String, QuestTabUiState>(selectedSortType) { quests, sortType ->
            val sortedQuests = quests.sortedBy { quest ->
                when (sortType) {
                    "포인트 높은 순" -> {
                        quest.rewardList.sumOf { reward ->
                            -reward.quantity
                        }
                    }

                    "포인트 낮은 순" -> {
                        quest.rewardList.sumOf { reward ->
                            reward.quantity
                        }
                    }

                    else -> {
                        -quest.score
                    }
                }
            }
            QuestTabUiState.Success(
                QuestTabUiData(
                    questList = sortedQuests
                )
            )
        }
        .catch {
            emit(QuestTabUiState.Error(it))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = QuestTabUiState.Loading
        )

    fun selectQuestType(questType: QuestType) {
        _selectedQuestType.value = questType
    }

    fun selectRewardType(rewardType: RewardType) {
        _selectedRewardType.value = rewardType
    }

    fun selectRepeatPeriod(repeatQuestPeriod: RepeatQuestPeriod) {
        _selectedRepeatPeriod.value = repeatQuestPeriod
    }

    fun selectSortType(sortType: String) {
        _selectedSortType.value = sortType
    }

    val challengePager = Pager(
        PagingConfig(
            pageSize = 10,
            initialLoadSize = 10
        )
    ) { challengeRepository.getChallengePaging() }
        .flow.cachedIn(scope = viewModelScope)

    private val _selectedChallenge = MutableStateFlow<Challenge?>(null)
    val selectedChallenge = _selectedChallenge.asStateFlow()

}