package com.ilsangtech.ilsang.core.data.mission.datasource

import androidx.paging.PagingData
import com.ilsangtech.ilsang.core.network.model.mission.ExampleMissionHistoryNetworkModel
import com.ilsangtech.ilsang.core.network.model.mission.RandomMissionHistoryNetworkModel
import com.ilsangtech.ilsang.core.network.model.mission.UserMissionHistoryNetworkModel
import kotlinx.coroutines.flow.Flow

interface MissionDataSource {
    fun getRandomMissionHistory(): Flow<PagingData<RandomMissionHistoryNetworkModel>>

    fun exampleMissionHistory(missionId: Int): Flow<PagingData<ExampleMissionHistoryNetworkModel>>

    fun getUserMissionHistory(userId: String?): Flow<PagingData<UserMissionHistoryNetworkModel>>

    suspend fun registerMissionHistoryEmoji(missionHistoryId: Int, emojiType: String)

    suspend fun deleteMissionHistoryEmoji(missionHistoryId: Int, emojiType: String)

    suspend fun reportMissionHistory(missionHistoryId: Int)

    suspend fun submitMission(
        missionId: Int,
        imageId: String? = null,
        quizId: Int? = null,
        answer: String? = null
    )
}