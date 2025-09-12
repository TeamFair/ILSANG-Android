package com.ilsangtech.ilsang.core.domain

import androidx.paging.PagingData
import com.ilsangtech.ilsang.core.model.title.LegendTitle
import com.ilsangtech.ilsang.core.model.title.TitleDetail
import com.ilsangtech.ilsang.core.model.title.UserTitle
import kotlinx.coroutines.flow.Flow

interface TitleRepository {
    suspend fun getTitleList(): List<TitleDetail>

    suspend fun getUserTitleList(): List<UserTitle>

    suspend fun getUnreadTitleList(): List<UserTitle>

    suspend fun readTitle(titleHistoryId: Int): Result<Unit>

    fun getLegendTitleRankList(titleId: String): Flow<PagingData<LegendTitle>>
}