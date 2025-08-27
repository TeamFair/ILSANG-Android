package com.ilsangtech.ilsang.core.data.mission.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ilsangtech.ilsang.core.data.mission.ExampleMissionHistoryPagingSource
import com.ilsangtech.ilsang.core.data.mission.RandomMissionHistoryPagingSource
import com.ilsangtech.ilsang.core.network.api.MissionApiService
import com.ilsangtech.ilsang.core.network.model.mission.ExampleMissionHistoryNetworkModel
import com.ilsangtech.ilsang.core.network.model.mission.MissionHistoryEmojiRegistrationRequest
import com.ilsangtech.ilsang.core.network.model.mission.RandomMissionHistoryNetworkModel
import kotlinx.coroutines.flow.Flow

class MissionDataSourceImpl(
    private val missionApiService: MissionApiService
) : MissionDataSource {
    override fun getRandomMissionHistory(): Flow<PagingData<RandomMissionHistoryNetworkModel>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                RandomMissionHistoryPagingSource(missionApiService)
            }
        ).flow
    }

    override fun exampleMissionHistory(missionId: Int): Flow<PagingData<ExampleMissionHistoryNetworkModel>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                ExampleMissionHistoryPagingSource(missionId, missionApiService)
            }
        ).flow
    }

    override suspend fun registerMissionHistoryEmoji(
        missionHistoryId: Int,
        emojiType: String
    ) {
        return missionApiService.registerMissionHistoryEmoji(
            missionHistoryId = missionHistoryId,
            request = MissionHistoryEmojiRegistrationRequest(emojiType)
        )
    }

    override suspend fun deleteMissionHistoryEmoji(missionHistoryId: Int, emojiType: String) {
        return missionApiService.deleteMissionHistoryEmoji(missionHistoryId, emojiType)
    }

    override suspend fun reportMissionHistory(missionHistoryId: Int) {
        return missionApiService.reportMissionHistory(missionHistoryId)
    }
}