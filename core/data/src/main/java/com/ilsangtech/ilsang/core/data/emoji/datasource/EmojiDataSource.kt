package com.ilsangtech.ilsang.core.data.emoji.datasource

import com.ilsangtech.ilsang.core.network.model.emoji.EmojiDeleteResponse
import com.ilsangtech.ilsang.core.network.model.emoji.EmojiRegistrationRequest
import com.ilsangtech.ilsang.core.network.model.emoji.EmojiRegistrationResponse
import com.ilsangtech.ilsang.core.network.model.emoji.EmojiResponse

interface EmojiDataSource {
    suspend fun getEmoji(
        challengeId: String
    ): EmojiResponse

    suspend fun registerEmoji(
        emojiRegistrationRequest: EmojiRegistrationRequest
    ): EmojiRegistrationResponse

    suspend fun deleteEmoji(
        emojiId: String
    ): EmojiDeleteResponse
}