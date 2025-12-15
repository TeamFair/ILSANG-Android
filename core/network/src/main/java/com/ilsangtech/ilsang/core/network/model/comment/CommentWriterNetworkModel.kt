package com.ilsangtech.ilsang.core.network.model.comment

import com.ilsangtech.ilsang.core.network.model.title.TitleNetworkModel
import kotlinx.serialization.Serializable

@Serializable
data class CommentWriterNetworkModel(
    val userId: String,
    val nickname: String,
    val profileImageId: String?,
    val title: TitleNetworkModel
)
