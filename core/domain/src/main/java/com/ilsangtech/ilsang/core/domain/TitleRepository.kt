package com.ilsangtech.ilsang.core.domain

import com.ilsangtech.ilsang.core.model.title.TitleDetail
import com.ilsangtech.ilsang.core.model.title.UserTitle

interface TitleRepository {
    suspend fun getTitleList(): List<TitleDetail>

    suspend fun getUserTitleList(): List<UserTitle>
}