package com.ilsangtech.ilsang.core.data.quest.repository

import com.ilsangtech.ilsang.core.domain.QuestCompleteDateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class QuestCompleteDateRepositoryImpl : QuestCompleteDateRepository {
    private val questCompleteDateMap = HashMap<Int, String>()
    private val _questCompleteDateMapFlow = MutableStateFlow<Map<Int, String>>(questCompleteDateMap)
    override val questCompleteDateMapFlow = _questCompleteDateMapFlow

    override fun updateQuestCompleteDate(questId: Int) {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val date = sdf.format(Date())
        questCompleteDateMap[questId] = date
        _questCompleteDateMapFlow.value = questCompleteDateMap
    }
}
