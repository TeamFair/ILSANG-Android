package com.ilsangtech.ilsang.core.datastore.user

import com.ilsangtech.ilsang.core.datastore.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {
    fun getUserPreferences(): Flow<UserPreferences>

    suspend fun updateOnBoardingCompleted(onBoardingCompleted: Boolean)

    suspend fun updateUserMyZone(myZone: String)

    suspend fun updateSeasonOpenDialogRejected(isRejected: Boolean)

    suspend fun updateIsZoneDialogRejectDate(isZoneDialogRejectDate: String)
}