package com.ilsangtech.ilsang.core.data.mission.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.ilsangtech.ilsang.core.data.mission.datasource.MissionDataSource
import com.ilsangtech.ilsang.core.data.mission.mapper.toExampleMissionHistory
import com.ilsangtech.ilsang.core.data.mission.mapper.toRandomMissionHistory
import com.ilsangtech.ilsang.core.domain.MissionRepository
import com.ilsangtech.ilsang.core.model.mission.ExampleMissionHistory
import com.ilsangtech.ilsang.core.model.mission.RandomMissionHistory
import com.ilsangtech.ilsang.core.network.model.mission.ExampleMissionHistoryNetworkModel
import com.ilsangtech.ilsang.core.network.model.mission.RandomMissionHistoryNetworkModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MissionRepositoryImpl(
    private val missionDataSource: MissionDataSource
) : MissionRepository {
    override fun getRandomMissionHistory(): Flow<PagingData<RandomMissionHistory>> {
        return missionDataSource.getRandomMissionHistory().map { pagingData ->
            pagingData.map(RandomMissionHistoryNetworkModel::toRandomMissionHistory)
        }
    }

    override fun getExampleMissionHistory(missionId: Int): Flow<PagingData<ExampleMissionHistory>> {
        return missionDataSource.exampleMissionHistory(missionId).map { pagingData ->
            pagingData.map(ExampleMissionHistoryNetworkModel::toExampleMissionHistory)
        }
    }

    override suspend fun likeMissionHistory(missionHistoryId: Int): Result<Unit> {
        return runCatching {
            missionDataSource.registerMissionHistoryEmoji(
                missionHistoryId = missionHistoryId,
                emojiType = "LIKE"
            )
        }
    }

    override suspend fun unlikeMissionHistory(missionHistoryId: Int): Result<Unit> {
        return runCatching {
            missionDataSource.deleteMissionHistoryEmoji(
                missionHistoryId = missionHistoryId,
                emojiType = "LIKE"
            )
        }
    }

    override suspend fun hateMissionHistory(missionHistoryId: Int): Result<Unit> {
        return runCatching {
            missionDataSource.registerMissionHistoryEmoji(
                missionHistoryId = missionHistoryId,
                emojiType = "HATE"
            )
        }
    }

    override suspend fun unhateMissionHistory(missionHistoryId: Int): Result<Unit> {
        return runCatching {
            missionDataSource.deleteMissionHistoryEmoji(
                missionHistoryId = missionHistoryId,
                emojiType = "HATE"
            )
        }
    }

    override suspend fun reportMissionHistory(missionHistoryId: Int): Result<Unit> {
        return runCatching {
            missionDataSource.reportMissionHistory(missionHistoryId)
        }
    }
}