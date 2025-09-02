package com.ilsangtech.ilsang.core.model.rank

data class UserRanksWithMyRank(
    val userRanks: List<UserRank>,
    val myRank: MyAreaRank
)