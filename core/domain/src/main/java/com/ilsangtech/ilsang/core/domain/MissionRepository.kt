package com.ilsangtech.ilsang.core.domain

import androidx.paging.PagingData
import com.ilsangtech.ilsang.core.model.mission.ExampleMissionHistory
import com.ilsangtech.ilsang.core.model.mission.RandomMissionHistory
import kotlinx.coroutines.flow.Flow

interface MissionRepository {
    fun getRandomMissionHistory(): Flow<PagingData<RandomMissionHistory>>

    fun getExampleMissionHistory(missionId: Int): Flow<PagingData<ExampleMissionHistory>>

    suspend fun likeMissionHistory(missionHistoryId: Int): Result<Unit>

    suspend fun unlikeMissionHistory(missionHistoryId: Int): Result<Unit>

    suspend fun hateMissionHistory(missionHistoryId: Int): Result<Unit>

    suspend fun unhateMissionHistory(missionHistoryId: Int): Result<Unit>

    suspend fun reportMissionHistory(missionHistoryId: Int): Result<Unit>
}