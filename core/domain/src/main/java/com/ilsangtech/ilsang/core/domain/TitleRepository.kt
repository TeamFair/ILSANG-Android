package com.ilsangtech.ilsang.core.domain

import com.ilsangtech.ilsang.core.model.title.Title
import com.ilsangtech.ilsang.core.model.title.UserTitle

interface TitleRepository {
    suspend fun getTitleList(): List<Title>

    suspend fun getUserTitleList(): List<UserTitle>
}