package com.ilsangtech.ilsang.core.data.quest

import com.ilsangtech.ilsang.core.model.Quest
import com.ilsangtech.ilsang.core.network.model.quest.QuestNetworkModel
import com.ilsangtech.ilsang.core.network.model.quest.RewardNetworkModel

fun QuestNetworkModel.toQuest(): Quest {
    return Quest(
        createDate = createDate,
        creatorRole = creatorRole,
        expireDate = expireDate,
        favoriteYn = favoriteYn,
        imageId = imageId,
        mainImageId = mainImageId,
        marketId = marketId,
        missionId = missionId,
        missionTitle = missionTitle,
        missionType = missionType,
        popularYn = popularYn,
        questId = questId,
        rewardList = rewardNetworkModelList.map(RewardNetworkModel::toReward),
        score = score,
        status = status,
        target = target,
        type = type,
        writer = writer
    )
}