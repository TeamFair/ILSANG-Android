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
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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

    private val _selectedQuest = MutableStateFlow<Quest?>(null)
    val selectedQuest = _selectedQuest.asStateFlow()

    private val _capturedImageUri = MutableStateFlow<Uri?>(null)
    val capturedImageFile = MutableStateFlow(FileManager.createCacheFile(context)).asStateFlow()

    private val _submitUiState = MutableStateFlow<SubmitUiState>(SubmitUiState.NotSubmitted)
    val submitUiState = _submitUiState.asStateFlow()

    val homeTapUiState: StateFlow<HomeTapUiState> = myInfo.map {
        if (it == null) {
            HomeTapUiState.Loading
        } else {
            try {
                coroutineScope {
                    val banners = async { bannerRepository.getBanners() }
                    val popularQuests = async { questRepository.getPopularQuests() }
                    val recommendedQuest = async { questRepository.getRecommendedQuests() }
                    val largeRewardQuests = async { questRepository.getLargeRewardQuests() }
                    val topRankUsers = async { rankRepository.getTopRankUsers() }

                    HomeTapUiState.Success(
                        data = HomeTapSuccessData(
                            banners = banners.await(),
                            popularQuests = popularQuests.await(),
                            recommendedQuests = recommendedQuest.await(),
                            largeRewardQuests = largeRewardQuests.await(),
                            topRankUsers = topRankUsers.await()
                        )
                    )
                }
            } catch (e: Exception) {
                HomeTapUiState.Error(e)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeTapUiState.Loading
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
        _selectedQuest.value = quest
    }

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

    override fun onCleared() {
        super.onCleared()
        capturedImageFile.value.delete()
    }
}