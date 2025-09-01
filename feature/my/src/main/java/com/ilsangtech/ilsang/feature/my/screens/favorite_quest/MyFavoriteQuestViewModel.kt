package com.ilsangtech.ilsang.feature.my.screens.favorite_quest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ilsangtech.ilsang.core.domain.QuestRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class MyFavoriteQuestViewModel @Inject constructor(
    userRepository: UserRepository,
    questRepository: QuestRepository
) : ViewModel() {
    @OptIn(ExperimentalCoroutinesApi::class)
    val favoriteQuestList = userRepository.getMyInfo().flatMapLatest { myInfo ->
        questRepository.getTypedQuests(
            commercialAreaCode = myInfo.myCommericalAreaCode,
            favoriteYn = true
        )
    }.cachedIn(viewModelScope)
}