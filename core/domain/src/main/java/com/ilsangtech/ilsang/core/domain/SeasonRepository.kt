package com.ilsangtech.ilsang.core.domain

import com.ilsangtech.ilsang.core.model.season.Season

interface SeasonRepository {
    suspend fun getSeasonList(): List<Season>
}