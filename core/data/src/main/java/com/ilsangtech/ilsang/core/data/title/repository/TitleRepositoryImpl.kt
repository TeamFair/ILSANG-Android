package com.ilsangtech.ilsang.core.data.title.repository

import com.ilsangtech.ilsang.core.data.title.datasource.TitleDataSource
import com.ilsangtech.ilsang.core.domain.TitleRepository
import com.ilsangtech.ilsang.core.model.Title

class TitleRepositoryImpl(
    private val titleDataSource: TitleDataSource
) : TitleRepository {
    override suspend fun getTitleList(): List<Title> {
        return titleDataSource.getTitleList().data.map { (title, history) ->
            Title(
                id = title.id,
                historyId = history?.id,
                name = title.name,
                type = title.type,
                condition = title.condition,
                createdAt = title.createdAt
            )
        }
    }
}