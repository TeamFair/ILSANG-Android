package com.ilsangtech.ilsang.core.data.mission.datasource

import androidx.paging.PagingData
import com.ilsangtech.ilsang.core.network.model.mission.ExampleMissionHistoryNetworkModel
import com.ilsangtech.ilsang.core.network.model.mission.RandomMissionHistoryNetworkModel
import kotlinx.coroutines.flow.Flow

interface MissionDataSource {
    fun getRandomMissionHistory(): Flow<PagingData<RandomMissionHistoryNetworkModel>>

    fun exampleMissionHistory(missionId: Int): Flow<PagingData<ExampleMissionHistoryNetworkModel>>

    suspend fun registerMissionHistoryEmoji(missionHistoryId: Int, emojiType: String)

    suspend fun deleteMissionHistoryEmoji(missionHistoryId: Int, emojiType: String)

    suspend fun reportMissionHistory(missionHistoryId: Int)
}