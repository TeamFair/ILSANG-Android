package com.ilsangtech.ilsang.core.data.quest.mapper

import com.ilsangtech.ilsang.core.model.quest.RecommendedQuest
import com.ilsangtech.ilsang.core.network.model.quest.RecommendedQuestNetworkModel

internal fun RecommendedQuestNetworkModel.toRecommendedQuest(): RecommendedQuest {
    return RecommendedQuest(
        questId = questId,
        imageId = imageId,
        mainImageId = mainImageId,
        title = title,
        writerName = writerName
    )
}