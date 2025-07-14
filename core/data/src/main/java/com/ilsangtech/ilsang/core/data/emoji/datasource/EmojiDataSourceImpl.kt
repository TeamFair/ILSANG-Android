package com.ilsangtech.ilsang.core.data.emoji.datasource

import com.ilsangtech.ilsang.core.network.api.EmojiApiService
import com.ilsangtech.ilsang.core.network.model.emoji.EmojiDeleteResponse
import com.ilsangtech.ilsang.core.network.model.emoji.EmojiRegistrationRequest
import com.ilsangtech.ilsang.core.network.model.emoji.EmojiRegistrationResponse
import com.ilsangtech.ilsang.core.network.model.emoji.EmojiResponse

class EmojiDataSourceImpl(private val emojiApiService: EmojiApiService): EmojiDataSource {
    override suspend fun getEmoji(challengeId: String): EmojiResponse {
        return emojiApiService.getEmoji(challengeId)
    }

    override suspend fun registerEmoji(
        emojiRegistrationRequest: EmojiRegistrationRequest
    ): EmojiRegistrationResponse {
        return emojiApiService.registerEmoji(emojiRegistrationRequest)
    }

    override suspend fun deleteEmoji(emojiId: String): EmojiDeleteResponse {
        return emojiApiService.deleteEmoji(emojiId)
    }

}