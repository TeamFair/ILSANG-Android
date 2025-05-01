package com.ilsangtech.ilsang.core.domain

import com.ilsangtech.ilsang.core.model.Emoji
import com.ilsangtech.ilsang.core.model.EmojiType

interface EmojiRepository {
    suspend fun getEmoji(challengeId: String): Emoji

    suspend fun registerEmoji(
        targetId: String,
        targetType: String,
        emojiType: EmojiType
    )

    suspend fun deleteEmoji(emojiId: String)
}