package com.ilsangtech.ilsang.core.data.challenge

import com.ilsangtech.ilsang.core.model.Challenge
import com.ilsangtech.ilsang.core.network.model.challenge.ChallengeNetworkModel

fun ChallengeNetworkModel.toChallenge(): Challenge {
    return Challenge(
        challengeId = challengeId,
        createdAt = createdAt,
        hateCnt = hateCnt,
        likeCnt = likeCnt,
        missionTitle = missionTitle,
        questImage = questImage,
        receiptImageId = receiptImageId,
        status = status,
        userNickName = userNickName,
        viewCount = viewCount
    )
}