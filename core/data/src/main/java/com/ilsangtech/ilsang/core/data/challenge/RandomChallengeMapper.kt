package com.ilsangtech.ilsang.core.data.challenge

import com.ilsangtech.ilsang.core.model.RandomChallenge
import com.ilsangtech.ilsang.core.network.model.challenge.RandomChallengeNetworkModel

fun RandomChallengeNetworkModel.toRandomChallenge(): RandomChallenge {
    return RandomChallenge(
        challengeId = challengeId,
        createdAt = createdAt,
        customerId = customerId,
        hateCnt = hateCnt,
        likeCnt = likeCnt,
        missionTitle = missionTitle,
        receiptImageId = receiptImageId,
        status = status,
        userNickName = userNickName,
        userProfileImage = userProfileImage,
        viewCount = viewCount
    )
}