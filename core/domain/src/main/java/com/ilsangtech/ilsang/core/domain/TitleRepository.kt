package com.ilsangtech.ilsang.core.domain

import com.ilsangtech.ilsang.core.model.title.Title

interface TitleRepository {
    suspend fun getTitleList(): List<Title>
}