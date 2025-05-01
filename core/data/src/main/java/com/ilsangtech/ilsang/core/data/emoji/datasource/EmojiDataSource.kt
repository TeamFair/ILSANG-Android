package com.ilsangtech.ilsang.core.data.emoji.datasource

import com.ilsangtech.ilsang.core.network.model.emoji.EmojiDeleteResponse
import com.ilsangtech.ilsang.core.network.model.emoji.EmojiRegistrationRequest
import com.ilsangtech.ilsang.core.network.model.emoji.EmojiRegistrationResponse
import com.ilsangtech.ilsang.core.network.model.emoji.EmojiResponse

interface EmojiDataSource {
    suspend fun getEmoji(
        authorization: String,
        challengeId: String
    ): EmojiResponse

    suspend fun registerEmoji(
        authorization: String,
        emojiRegistrationRequest: EmojiRegistrationRequest
    ): EmojiRegistrationResponse

    suspend fun deleteEmoji(
        authorization: String,
        emojiId: String
    ): EmojiDeleteResponse
}