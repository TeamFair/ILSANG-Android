package com.ilsangtech.ilsang.core.network.model.comment

import kotlinx.serialization.Serializable

@Serializable
data class CommentReportRequest(val reason: String)
