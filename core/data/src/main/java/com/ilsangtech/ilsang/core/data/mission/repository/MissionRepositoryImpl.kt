package com.ilsangtech.ilsang.core.data.mission.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.ilsangtech.ilsang.core.data.mission.datasource.MissionDataSource
import com.ilsangtech.ilsang.core.data.mission.mapper.toRandomMissionHistory
import com.ilsangtech.ilsang.core.domain.MissionRepository
import com.ilsangtech.ilsang.core.model.mission.RandomMissionHistory
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
}