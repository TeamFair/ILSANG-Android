package com.ilsangtech.ilsang.core.model.user

import com.ilsangtech.ilsang.core.model.title.UserTitle

sealed interface User {
    val id: String
    val commercialAreaCode: String
    val nickname: String
    val profileImageId: String?
    val userTitle: UserTitle?

    data class SelfUser(
        override val id: String,
        override val commercialAreaCode: String,
        override val nickname: String,
        override val profileImageId: String?,
        override val userTitle: UserTitle?,
        val preferenceAreaCode: String,
        val shouldIsZoneDialog: Boolean,
        val shouldShowSeasonOpenDialog: Boolean
    ) : User

    data class OtherUser(
        override val id: String,
        override val commercialAreaCode: String,
        override val nickname: String,
        override val profileImageId: String?,
        override val userTitle: UserTitle?,
    ) : User
}