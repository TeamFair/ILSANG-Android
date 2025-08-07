package com.ilsangtech.ilsang.feature.home

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilsangtech.ilsang.core.domain.BannerRepository
import com.ilsangtech.ilsang.core.domain.ChallengeRepository
import com.ilsangtech.ilsang.core.domain.QuestRepository
import com.ilsangtech.ilsang.core.domain.RankRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.model.MyInfo
import com.ilsangtech.ilsang.core.model.Quest
import com.ilsangtech.ilsang.core.model.RewardType
import com.ilsangtech.ilsang.core.util.FileManager
import com.ilsangtech.ilsang.feature.home.home.HomeTapSuccessData
import com.ilsangtech.ilsang.feature.home.home.HomeTapUiState
import com.ilsangtech.ilsang.feature.home.submit.SubmitUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
    @ApplicationContext private val context: Context,
    private val userRepository: UserRepository,
    private val bannerRepository: BannerRepository,
    private val questRepository: QuestRepository,
    private val rankRepository: RankRepository,
    private val challengeRepository: ChallengeRepository
) : ViewModel() {
    private val _myInfo: MutableStateFlow<MyInfo?> =
        MutableStateFlow(userRepository.currentUser)
    val myInfo: StateFlow<MyInfo?> = _myInfo.asStateFlow()

    private val _selectedQuestId = MutableStateFlow<String?>(null)
    private val selectedQuestId = _selectedQuestId.asStateFlow()

    private val _capturedImageUri = MutableStateFlow<Uri?>(null)
    val capturedImageFile = MutableStateFlow(FileManager.createCacheFile(context)).asStateFlow()

    private val _submitUiState = MutableStateFlow<SubmitUiState>(SubmitUiState.NotSubmitted)
    val submitUiState = _submitUiState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val homeTapUiState: StateFlow<HomeTapUiState> =
        myInfo.flatMapLatest { state ->
            if (state == null) return@flatMapLatest flowOf(HomeTapUiState.Loading)

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
                HomeTapUiState.Success(
                    HomeTapSuccessData(
                        banners = banners,
                        popularQuests = popular,
                        recommendedQuests = recommended,
                        largeRewardQuests = largeReward,
                        topRankUsers = topRank
                    )
                )
            }
        }
            .catch { e -> emit(HomeTapUiState.Error(e)) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = HomeTapUiState.Loading
            )

    val selectedQuest = selectedQuestId.combine(homeTapUiState) { questId, uiState ->
        if (questId == null || uiState !is HomeTapUiState.Success) return@combine null
        uiState.data.popularQuests.find { it.questId == questId }
            ?: uiState.data.recommendedQuests.find { it.questId == questId }
            ?: uiState.data.largeRewardQuests.values.flatten().find { it.questId == questId }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val rankingUiState = flow {
        emit(
            RewardType.entries.associateWith { rankRepository.getXpTypeRank(it) }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyMap()
    )

    fun selectQuest(quest: Quest) {
        _selectedQuestId.update { quest.questId }
    }

    fun unselectQuest() {
        _selectedQuestId.update { null }
    }

    fun submitApproveImage() {
        viewModelScope.launch {
            runCatching {
                _submitUiState.value = SubmitUiState.Loading
                selectedQuestId.value?.let { questId ->
                    challengeRepository.submitChallenge(
                        questId = questId,
                        imageBytes = FileManager.getBytesFromUri(
                            context,
                            FileManager.getUriForFile(capturedImageFile.value, context)
                        )
                    )
                }
            }.onSuccess {
                _submitUiState.value = SubmitUiState.Success
            }.onFailure {
                _submitUiState.value = SubmitUiState.Error
            }
        }
    }

    fun completeSubmit() {
        _submitUiState.value = SubmitUiState.NotSubmitted
        _capturedImageUri.value = null
        _selectedQuestId.update { null }
    }

    fun updateQuestFavoriteStatus() {
        viewModelScope.launch {
            selectedQuest.value?.let { quest ->
                if (quest.favoriteYn) questRepository.deleteFavoriteQuest(quest.questId)
                else questRepository.registerFavoriteQuest(quest.questId)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        capturedImageFile.value.delete()
    }
}