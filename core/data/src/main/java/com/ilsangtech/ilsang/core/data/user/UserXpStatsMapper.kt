package com.ilsangtech.ilsang.core.data.user

import com.ilsangtech.ilsang.core.model.UserXpStats
import com.ilsangtech.ilsang.core.network.model.user.UserXpStatsNetworkModel

fun UserXpStatsNetworkModel.toUserXpStats(): UserXpStats {
    return UserXpStats(
        charmStat = this.charmStat,
        funStat = this.funStat,
        intellectStat = this.intellectStat,
        sociabilityStat = this.sociabilityStat,
        strengthStat = this.strengthStat
    )
}
