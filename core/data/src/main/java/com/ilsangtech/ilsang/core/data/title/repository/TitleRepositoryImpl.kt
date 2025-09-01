package com.ilsangtech.ilsang.core.data.title.repository

import com.ilsangtech.ilsang.core.data.title.datasource.TitleDataSource
import com.ilsangtech.ilsang.core.data.title.mapper.toUserTitle
import com.ilsangtech.ilsang.core.domain.TitleRepository
import com.ilsangtech.ilsang.core.model.title.Title
import com.ilsangtech.ilsang.core.model.title.TitleGrade
import com.ilsangtech.ilsang.core.model.title.TitleType
import com.ilsangtech.ilsang.core.model.title.UserTitle
import com.ilsangtech.ilsang.core.network.model.title.UserTitleNetworkModel

class TitleRepositoryImpl(
    private val titleDataSource: TitleDataSource
) : TitleRepository {
    override suspend fun getTitleList(): List<Title> {
        return titleDataSource.getTitleList().map {
            Title(
                name = it.name,
                grade = when (it.grade) {
                    "STANDARD" -> TitleGrade.Standard
                    "RARE" -> TitleGrade.Rare
                    else -> TitleGrade.Legend
                },
                type = when (it.type) {
                    "METRO" -> TitleType.Metro
                    "COMMERCIAL" -> TitleType.Commercial
                    else -> TitleType.Contribution
                }
            )
        }
    }

    override suspend fun getUserTitleList(): List<UserTitle> {
        return titleDataSource.getUserTitleList().map(UserTitleNetworkModel::toUserTitle)
    }
}