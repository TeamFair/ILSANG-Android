package com.ilsangtech.ilsang.feature.home

import android.content.Context
import android.net.Uri
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
import com.ilsangtech.ilsang.core.model.MyInfo
import com.ilsangtech.ilsang.core.model.UserXpStats
import com.ilsangtech.ilsang.feature.home.home.HomeTapSuccessData
import com.ilsangtech.ilsang.feature.home.home.HomeTapUiState
import com.ilsangtech.ilsang.feature.home.quest.QuestTabUiData
import com.ilsangtech.ilsang.feature.home.quest.QuestTabUiState
import com.ilsangtech.ilsang.feature.home.submit.SubmitUiState
import com.ilsangtech.ilsang.feature.home.util.FileManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
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

    val homeTapUiState: StateFlow<HomeTapUiState> = myInfo.map {
        if (it == null) {
            HomeTapUiState.Loading
        } else {
            val banners = bannerRepository.getBanners()
            val popularQuests = questRepository.getPopularQuests()
            val recommendedQuest = questRepository.getRecommendedQuests()
            val largeRewardQuests = questRepository.getLargeRewardQuests()
            val topRankUsers = rankRepository.getTopRankUsers()

            try {
                HomeTapUiState.Success(
                    data = HomeTapSuccessData(
                        banners = banners,
                        popularQuests = popularQuests,
                        recommendedQuests = recommendedQuest,
                        largeRewardQuests = largeRewardQuests,
                        topRankUsers = topRankUsers
                    )
                )
            } catch (e: Exception) {
                HomeTapUiState.Error(e)
            }
        }
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

    val challengePager = challengeRepository.getChallengePaging().cachedIn(viewModelScope)

    private val _selectedChallenge = MutableStateFlow<Challenge?>(null)
    val selectedChallenge = _selectedChallenge.asStateFlow()

    private val _editNickname = MutableStateFlow(myInfo.value?.nickname ?: "")
    val editNickname = _editNickname.asStateFlow()

    private val _nicknameEditErrorMessage = MutableStateFlow<String?>(null)
    val nicknameEditErrorMessage = _nicknameEditErrorMessage.asStateFlow()

    private val _isUserProfileEditSuccess = MutableStateFlow<Boolean?>(null)
    val isUserProfileEditSuccess = _isUserProfileEditSuccess.asStateFlow()


    val userXpStats = flow {
        emit(userRepository.getUserXpStats())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UserXpStats()
    )

    private fun isValidNickname(name: String): Boolean {
        val pattern = ".*[가-힣a-zA-Z0-9]+.*".toRegex()
        return pattern.matches(name) && name.length in 2..12
    }

    fun changeNickname(nickname: String) {
        _editNickname.value = nickname
        if (isValidNickname(nickname)) {
            _nicknameEditErrorMessage.value = null
        } else {
            _nicknameEditErrorMessage.value = "한글+영어+숫자 포함 2 ~ 12자 이하로 닉네임을 입력해주세요."
        }
    }

    fun updateUserProfile(uri: Uri?) {
        viewModelScope.launch {
            runCatching {
                if (uri != null) {
                    val fileData = FileManager.getBytesFromUri(context, uri)
                    userRepository.updateUserImage(fileData)
                }
                if (editNickname.value != myInfo.value?.nickname) {
                    userRepository.updateUserNickname(editNickname.value)
                }
            }.onSuccess {
                userRepository.updateMyInfo()
                _myInfo.update { userRepository.currentUser }
                _isUserProfileEditSuccess.update { true }
            }.onFailure {
                _isUserProfileEditSuccess.update { false }
            }
        }
    }

    fun deleteUserProfileImage() {
        viewModelScope.launch {
            userRepository.deleteUserImage()
                .onSuccess {
                    userRepository.updateMyInfo()
                    _myInfo.update { userRepository.currentUser }
                    _isUserProfileEditSuccess.update { true }
                }.onFailure {
                    _isUserProfileEditSuccess.update { false }
                }
        }
    }

    fun resetUserProfileEditSuccess() {
        _editNickname.value = _myInfo.value?.nickname ?: ""
        _nicknameEditErrorMessage.update { null }
        _isUserProfileEditSuccess.update { null }
    }

    private val _selectedQuest = MutableStateFlow<Quest?>(null)
    val selectedQuest = _selectedQuest.asStateFlow()

    private val _capturedImageUri = MutableStateFlow<Uri?>(null)
    val capturedImageFile = MutableStateFlow(FileManager.createCacheFile(context)).asStateFlow()

    fun setCapturedImageUri(uri: Uri) {
        _capturedImageUri.value = uri
    }

    private val _submitUiState = MutableStateFlow<SubmitUiState>(SubmitUiState.NotSubmitted)
    val submitUiState = _submitUiState.asStateFlow()

    fun submitApproveImage() {
        viewModelScope.launch {
            runCatching {
                _submitUiState.value = SubmitUiState.Loading
                challengeRepository.submitChallenge(
                    questId = selectedQuest.value!!.questId,
                    imageBytes = FileManager.getBytesFromUri(
                        context,
                        FileManager.getUriForFile(capturedImageFile.value, context)
                    )
                )
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
        _selectedQuest.value = null
    }

    fun selectQuest(quest: Quest) {
        _selectedQuest.value = quest
    }

    fun selectChallenge(challenge: Challenge) {
        _selectedChallenge.value = challenge
    }

    val rankingUiState = flow {
        emit(
            RewardType.entries.associateWith { rankRepository.getXpTypeRank(it) }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyMap()
    )

    override fun onCleared() {
        super.onCleared()
        capturedImageFile.value.delete()
    }
}