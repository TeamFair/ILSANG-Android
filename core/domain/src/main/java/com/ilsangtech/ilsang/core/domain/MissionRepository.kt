package com.ilsangtech.ilsang.core.domain

import androidx.paging.PagingData
import com.ilsangtech.ilsang.core.model.mission.RandomMissionHistory
import kotlinx.coroutines.flow.Flow

interface MissionRepository {
    fun getRandomMissionHistory(): Flow<PagingData<RandomMissionHistory>>
}