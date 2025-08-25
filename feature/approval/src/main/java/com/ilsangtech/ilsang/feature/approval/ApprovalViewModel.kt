package com.ilsangtech.ilsang.feature.approval

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.ilsangtech.ilsang.core.domain.AreaRepository
import com.ilsangtech.ilsang.core.domain.ChallengeRepository
import com.ilsangtech.ilsang.core.domain.EmojiRepository
import com.ilsangtech.ilsang.core.domain.MissionRepository
import com.ilsangtech.ilsang.core.model.Emoji
import com.ilsangtech.ilsang.core.model.EmojiType
import com.ilsangtech.ilsang.core.model.RandomChallenge
import com.ilsangtech.ilsang.feature.approval.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApprovalViewModel @Inject constructor(
    missionRepository: MissionRepository,
    areaRepository: AreaRepository,
    private val challengeRepository: ChallengeRepository,
    private val emojiRepository: EmojiRepository
) : ViewModel() {
    private val emojiMap = MutableStateFlow<Map<String, Emoji>>(mapOf())

    val randomMissionHistories =
        missionRepository.getRandomMissionHistory()
            .map { pagingData ->
                pagingData.map { randomMissionHistory ->
                    val commercialArea =
                        areaRepository.getCommercialArea(randomMissionHistory.commercialAreaCode)
                    randomMissionHistory.toUiModel(commercialArea.areaName)
                }
            }.cachedIn(viewModelScope)

    private val _isReportSuccess = MutableStateFlow<Boolean?>(null)
    val isReportSuccess = _isReportSuccess.asStateFlow()

    fun likeChallenge(challenge: RandomChallenge) {
        viewModelScope.launch {
            val emoji = emojiMap.value[challenge.challengeId] ?: challenge.emoji
            if (emoji?.isLike == true) {
                runCatching {
                    emojiRepository.deleteEmoji(challenge.emoji!!.likeId!!)
                }.onSuccess {
                    emojiMap.update {
                        it + (challenge.challengeId to emoji.copy(
                            isLike = false,
                            likeId = null
                        ))
                    }
                }
            } else {
                runCatching {
                    emojiRepository.registerEmoji(
                        targetId = challenge.challengeId,
                        targetType = "challenge",
                        emojiType = EmojiType.LIKE
                    )
                }.onSuccess { emojiId ->
                    emojiMap.update {
                        it + (challenge.challengeId to emoji!!.copy(
                            isLike = true,
                            likeId = emojiId
                        ))
                    }
                }
            }
        }
    }

    fun hateChallenge(challenge: RandomChallenge) {
        viewModelScope.launch {
            val emoji = emojiMap.value[challenge.challengeId] ?: challenge.emoji
            if (emoji?.isHate == true) {
                runCatching {
                    emojiRepository.deleteEmoji(emoji.hateId!!)
                }.onSuccess {
                    emojiMap.update {
                        it + (challenge.challengeId to emoji.copy(
                            isHate = false,
                            hateId = null
                        ))
                    }
                }
            } else {
                runCatching {
                    emojiRepository.registerEmoji(
                        targetId = challenge.challengeId,
                        targetType = "challenge",
                        emojiType = EmojiType.HATE
                    )
                }.onSuccess { emojiId ->
                    emojiMap.update {
                        it + (challenge.challengeId to emoji!!.copy(
                            isHate = true,
                            hateId = emojiId
                        ))
                    }
                }
            }
        }
    }

    fun reportChallenge(challenge: RandomChallenge) {
        viewModelScope.launch {
            runCatching {
                challengeRepository.reportChallenge(challenge.challengeId)
            }.onSuccess {
                _isReportSuccess.update { true }
            }.onFailure {
                _isReportSuccess.update { false }
            }
        }
    }

    fun resetReportStatus() {
        _isReportSuccess.update { null }
    }
}