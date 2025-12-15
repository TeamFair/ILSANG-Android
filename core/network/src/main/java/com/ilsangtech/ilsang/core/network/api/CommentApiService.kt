package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.comment.CommentCreationRequest
import com.ilsangtech.ilsang.core.network.model.comment.CommentNetworkModel
import com.ilsangtech.ilsang.core.network.model.comment.CommentReportRequest
import com.ilsangtech.ilsang.core.network.model.comment.CommentReportResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CommentApiService {
    @POST("api/v1/mission/user/history/comment/{missionHistoryId}")
    suspend fun createComment(
        @Path("missionHistoryId") missionHistoryId: Int,
        @Body request: CommentCreationRequest
    )

    @DELETE("api/v1/mission/user/history/comment/{commentId}")
    suspend fun deleteComment(@Path("commentId") commentId: Int)

    @GET("api/v1/mission/user/history/comment")
    suspend fun getComments(
        @Query("missionHistoryId") missionHistoryId: Int
    ): List<CommentNetworkModel>

    @POST("api/v1/mission/user/history/comment/{commentId}/report")
    suspend fun reportComment(
        @Path("commentId") commentId: Int,
        @Body request: CommentReportRequest
    ): CommentReportResponse
}