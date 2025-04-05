package com.ilsangtech.ilsang.core.data.quest.datasource

import com.ilsangtech.ilsang.core.network.api.QuestApiService
import com.ilsangtech.ilsang.core.network.model.quest.UncompletedTotalQuestResponse
import javax.inject.Inject

class QuestDataSourceImpl @Inject constructor(
    private val questApiService: QuestApiService
) : QuestDataSource {
    override suspend fun getUncompletedTotalQuest(
        authorization: String,
        popularYn: Boolean?,
        page: Int,
        size: Int,
        sort: List<String>
    ): UncompletedTotalQuestResponse {
        return questApiService.getUncompletedTotalQuest(
            authorization = authorization,
            page = page,
            size = size,
            sort = sort
        )
    }
}