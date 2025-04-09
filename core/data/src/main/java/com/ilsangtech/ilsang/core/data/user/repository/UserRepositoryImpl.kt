package com.ilsangtech.ilsang.core.data.user.repository

import com.ilsangtech.ilsang.core.data.user.datasource.UserDataSource
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.model.User
import com.ilsangtech.ilsang.core.network.model.auth.LoginRequest
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository {
    override var currentUser: User? = null

    override suspend fun login(user: User) {
        val userResponse = userDataSource.login(
            loginRequest = LoginRequest(
                accessToken = user.accessToken,
                channel = "GOOGLE",
                email = user.email!!,
                refreshToken = "",
                userType = "CUSTOMER"
            )
        )
        currentUser = User(
            authorization = userResponse.data.authorization,
            email = user.email,
            accessToken = user.accessToken
        )
    }
}