package com.ilsangtech.ilsang.feature.my.screens.favorite_quest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ilsangtech.ilsang.core.domain.AreaRepository
import com.ilsangtech.ilsang.core.domain.QuestRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MyFavoriteQuestViewModel @Inject constructor(
    userRepository: UserRepository,
    areaRepository: AreaRepository,
    questRepository: QuestRepository
) : ViewModel() {
    private val myInfo = userRepository.getMyInfo()

    val commercialAreaName = myInfo.map {
        areaRepository
            .getCommercialArea(it.myCommericalAreaCode)
            .areaName
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
}