package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.emoji.EmojiDeleteResponse
import com.ilsangtech.ilsang.core.network.model.emoji.EmojiRegistrationRequest
import com.ilsangtech.ilsang.core.network.model.emoji.EmojiRegistrationResponse
import com.ilsangtech.ilsang.core.network.model.emoji.EmojiResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface EmojiApiService {
    @GET("customer/emoji")
    suspend fun getEmoji(
        @Query("challengeId") challengeId: String
    ): EmojiResponse

    @POST("customer/emoji")
    suspend fun registerEmoji(
        @Body emojiRegistrationRequest: EmojiRegistrationRequest
    ): EmojiRegistrationResponse

    @DELETE("customer/emoji")
    suspend fun deleteEmoji(
        @Query("emojiId") emojiId: String
    ): EmojiDeleteResponse
}