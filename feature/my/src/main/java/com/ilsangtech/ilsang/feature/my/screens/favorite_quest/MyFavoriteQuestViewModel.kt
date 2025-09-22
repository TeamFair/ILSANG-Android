package com.ilsangtech.ilsang.feature.my.screens.favorite_quest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import com.ilsangtech.ilsang.core.domain.AreaRepository
import com.ilsangtech.ilsang.core.domain.QuestRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.model.quest.TypedQuest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyFavoriteQuestViewModel @Inject constructor(
    userRepository: UserRepository,
    areaRepository: AreaRepository,
    private val questRepository: QuestRepository
) : ViewModel() {
    private val myInfo = userRepository.getMyInfo()
    private val favoriteQuestCache = MutableStateFlow(setOf<Int>())

    val commercialAreaName = myInfo.map {
        areaRepository
            .getCommercialArea(it.myCommericalAreaCode)
            .areaName
    }.catch {
        emit("")
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val favoriteQuestList = myInfo.flatMapLatest { myInfo ->
        questRepository.getTypedQuests(
            commercialAreaCode = myInfo.myCommericalAreaCode,
            favoriteYn = true
        )
    }.cachedIn(viewModelScope)
        .combine(favoriteQuestCache) { typedQuests, favoriteQuestCache ->
            typedQuests.filter { it.questId !in favoriteQuestCache }
        }

    fun updateFavoriteStatus(typedQuest: TypedQuest) {
        viewModelScope.launch {
            val result = if (typedQuest.favoriteYn) {
                questRepository.deleteFavoriteQuest(typedQuest.questId)
            } else {
                questRepository.registerFavoriteQuest(typedQuest.questId)
            }
            result.onSuccess {
                favoriteQuestCache.update {
                    favoriteQuestCache.value + typedQuest.questId
                }
            }
        }
    }
}