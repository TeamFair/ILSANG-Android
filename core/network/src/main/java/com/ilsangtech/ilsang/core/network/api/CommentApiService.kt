package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.comment.CommentCreationRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface CommentApiService {
    @POST("api/v1/mission/user/history/comment/{missionHistoryId}")
    suspend fun createComment(
        @Path("missionHistoryId") missionHistoryId: Int,
        @Body request: CommentCreationRequest
    )
}