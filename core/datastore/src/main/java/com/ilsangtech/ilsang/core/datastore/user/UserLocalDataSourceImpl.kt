package com.ilsangtech.ilsang.core.datastore.user

import androidx.datastore.core.DataStore
import com.ilsangtech.ilsang.core.datastore.UserPreferences
import com.ilsangtech.ilsang.core.datastore.copy
import kotlinx.coroutines.flow.Flow

class UserLocalDataSourceImpl(
    private val userDataStore: DataStore<UserPreferences>
) : UserLocalDataSource {
    override fun getUserPreferences(): Flow<UserPreferences> {
        return userDataStore.data
    }

    override suspend fun updateOnBoardingCompleted(onBoardingCompleted: Boolean) {
        userDataStore.updateData { preferences ->
            preferences.copy { this.isOnBoardingCompleted = onBoardingCompleted }
        }
    }

    override suspend fun updateUserMyZone(myZone: String) {
        userDataStore.updateData { preferences ->
            preferences.copy { userMyZone = myZone }
        }
    }

    override suspend fun updateSeasonOpenDialogRejected(isRejected: Boolean) {
        userDataStore.updateData { preferences ->
            preferences.copy { isSeasonOpenDialogRejected = isRejected }
        }
    }

    override suspend fun updateIsZoneDialogRejectDate(isZoneDialogRejectDate: String) {
        userDataStore.updateData { preferences ->
            preferences.copy { this.isZoneDialogRejectDate = isZoneDialogRejectDate }
        }
    }
}