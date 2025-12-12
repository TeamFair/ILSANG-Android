package com.ilsangtech.ilsang.core.data.comment.datasource

import com.ilsangtech.ilsang.core.network.api.CommentApiService

class CommentDataSourceImpl(
    private val commentApiService: CommentApiService
) : CommentDataSource {
}