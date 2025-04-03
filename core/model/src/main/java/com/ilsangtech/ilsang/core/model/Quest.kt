package com.ilsangtech.ilsang.core.model

data class Quest(
    val createDate: String,
    val creatorRole: String,
    val expireDate: String,
    val favoriteYn: Boolean,
    val imageId: String,
    val mainImageId: String?,
    val marketId: String?,
    val missionId: String,
    val missionTitle: String,
    val missionType: String,
    val popularYn: Boolean,
    val questId: String,
    val rewardNetworkModelList: List<Reward>,
    val score: Int,
    val status: String,
    val target: String,
    val type: String,
    val writer: String
)
