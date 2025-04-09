package com.ilsangtech.ilsang.core.domain

import com.ilsangtech.ilsang.core.model.User

interface UserRepository {
    var currentUser: User?

    suspend fun login(user: User)
}