package com.ilsangtech.ilsang.core.domain

import androidx.paging.PagingData
import com.ilsangtech.ilsang.core.model.mission.ExampleMissionHistory
import com.ilsangtech.ilsang.core.model.mission.MissionType
import com.ilsangtech.ilsang.core.model.mission.RandomMissionHistory
import com.ilsangtech.ilsang.core.model.mission.UserMissionHistory
import com.ilsangtech.ilsang.core.model.mission.UserMissionHistoryDetail
import kotlinx.coroutines.flow.Flow

interface MissionRepository {
    fun getRandomMissionHistory(): Flow<PagingData<RandomMissionHistory>>

    fun getExampleMissionHistory(missionId: Int): Flow<PagingData<ExampleMissionHistory>>

    fun getUserMissionHistory(
        userId: String? = null,
        missionType: MissionType
    ): Flow<PagingData<UserMissionHistory>>

    suspend fun getUserMissionHistoryDetail(missionHistoryId: Int): UserMissionHistoryDetail

    suspend fun likeMissionHistory(missionHistoryId: Int): Result<Unit>

    suspend fun unlikeMissionHistory(missionHistoryId: Int): Result<Unit>

    suspend fun hateMissionHistory(missionHistoryId: Int): Result<Unit>

    suspend fun unhateMissionHistory(missionHistoryId: Int): Result<Unit>

    suspend fun reportMissionHistory(missionHistoryId: Int): Result<Unit>

    suspend fun submitImageMission(missionId: Int, imageId: String): Result<Unit>

    suspend fun submitQuizMission(missionId: Int, quizId: Int, answer: String): Result<Boolean>

    suspend fun deleteMissionHistory(missionHistoryId: Int): Result<Unit>
}