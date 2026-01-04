package com.ilsangtech.ilsang.core.model.comment

import com.ilsangtech.ilsang.core.model.title.Title

data class CommentWriter(
    val userId: String,
    val nickname: String,
    val profileImageId: String?,
    val title: Title?
)
