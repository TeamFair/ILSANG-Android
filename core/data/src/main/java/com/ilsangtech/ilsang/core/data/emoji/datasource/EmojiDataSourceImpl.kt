package com.ilsangtech.ilsang.core.data.emoji.datasource

import com.ilsangtech.ilsang.core.network.api.EmojiApiService
import com.ilsangtech.ilsang.core.network.model.emoji.EmojiDeleteResponse
import com.ilsangtech.ilsang.core.network.model.emoji.EmojiRegistrationRequest
import com.ilsangtech.ilsang.core.network.model.emoji.EmojiRegistrationResponse
import com.ilsangtech.ilsang.core.network.model.emoji.EmojiResponse

class EmojiDataSourceImpl(private val emojiApiService: EmojiApiService): EmojiDataSource {
    override suspend fun getEmoji(authorization: String, challengeId: String): EmojiResponse {
        return emojiApiService.getEmoji(authorization, challengeId)
    }

    override suspend fun registerEmoji(
        authorization: String,
        emojiRegistrationRequest: EmojiRegistrationRequest
    ): EmojiRegistrationResponse {
        return emojiApiService.registerEmoji(authorization, emojiRegistrationRequest)
    }

    override suspend fun deleteEmoji(authorization: String, emojiId: String): EmojiDeleteResponse {
        return emojiApiService.deleteEmoji(authorization, emojiId)
    }

}