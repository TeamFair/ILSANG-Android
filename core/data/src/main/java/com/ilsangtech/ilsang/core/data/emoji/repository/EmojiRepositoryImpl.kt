package com.ilsangtech.ilsang.core.data.emoji.repository

import com.ilsangtech.ilsang.core.data.emoji.datasource.EmojiDataSource
import com.ilsangtech.ilsang.core.data.emoji.toEmoji
import com.ilsangtech.ilsang.core.domain.EmojiRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.model.Emoji
import com.ilsangtech.ilsang.core.model.EmojiType
import com.ilsangtech.ilsang.core.network.model.emoji.EmojiRegistrationRequest

class EmojiRepositoryImpl(
    private val userRepository: UserRepository,
    private val emojiDataSource: EmojiDataSource
) : EmojiRepository {
    override suspend fun getEmoji(challengeId: String): Emoji {
        return emojiDataSource.getEmoji(
            authorization = userRepository.currentUser?.authorization!!,
            challengeId = challengeId
        ).emojiData.toEmoji()
    }

    override suspend fun registerEmoji(targetId: String, targetType: String, emojiType: EmojiType): String {
        return emojiDataSource.registerEmoji(
            authorization = userRepository.currentUser?.authorization!!,
            emojiRegistrationRequest = EmojiRegistrationRequest(
                targetId = targetId,
                targetType = targetType,
                emojiType = emojiType.name
            )
        ).emojiRegistrationData.emojiId
    }

    override suspend fun deleteEmoji(emojiId: String) {
        emojiDataSource.deleteEmoji(
            authorization = userRepository.currentUser?.authorization!!,
            emojiId = emojiId
        )
    }
}