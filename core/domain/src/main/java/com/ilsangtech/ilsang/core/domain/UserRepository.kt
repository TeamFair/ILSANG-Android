package com.ilsangtech.ilsang.core.domain

import com.ilsangtech.ilsang.core.model.UserInfo

interface UserRepository {
    var currentUser: UserInfo?

    suspend fun login(user: User)
}