package com.ilsangtech.ilsang.core.domain

import kotlinx.coroutines.flow.StateFlow

interface QuestCompleteDateRepository {
    val questCompleteDateMapFlow: StateFlow<Map<Int, String>>

    fun updateQuestCompleteDate(questId: Int)
}