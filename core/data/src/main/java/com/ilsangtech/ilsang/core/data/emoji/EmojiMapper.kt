package com.ilsangtech.ilsang.core.data.emoji

import com.ilsangtech.ilsang.core.model.Emoji
import com.ilsangtech.ilsang.core.network.model.emoji.EmojiData

fun EmojiData.toEmoji(): Emoji {
    return Emoji(
        likeId = likeId,
        isLike = isLike,
        hateId = hateId,
        isHate = isHate
    )
}