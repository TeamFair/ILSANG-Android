package com.ilsangtech.ilsang.core.data.comment.repository

import com.ilsangtech.ilsang.core.data.comment.datasource.CommentDataSource
import com.ilsangtech.ilsang.core.domain.CommentRepository

class CommentRepositoryImpl(
    private val commentDataSource: CommentDataSource
) : CommentRepository {
}