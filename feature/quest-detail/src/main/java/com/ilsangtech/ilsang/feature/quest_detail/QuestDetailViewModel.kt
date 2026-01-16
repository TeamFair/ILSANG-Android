package com.ilsangtech.ilsang.feature.quest_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.ilsangtech.ilsang.core.domain.QuestRepository
import com.ilsangtech.ilsang.feature.quest_detail.navigation.QuestDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuestDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val questRepository: QuestRepository
) : ViewModel() {
    private val questId = savedStateHandle.toRoute<QuestDetailRoute>().questId
    
}