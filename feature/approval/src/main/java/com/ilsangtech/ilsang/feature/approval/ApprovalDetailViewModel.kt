package com.ilsangtech.ilsang.feature.approval

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ilsangtech.ilsang.core.domain.CommentRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.model.comment.Comment
import com.ilsangtech.ilsang.core.model.user.MyInfo
import com.ilsangtech.ilsang.feature.approval.model.CommentUiState
import com.ilsangtech.ilsang.feature.approval.model.toUiModel
import com.ilsangtech.ilsang.feature.approval.navigation.ApprovalDetailRoute
import com.ilsangtech.ilsang.feature.approval.navigation.missionHistoryUiModelTypeMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ApprovalDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    userRepository: UserRepository,
    private val commentRepository: CommentRepository
) : ViewModel() {
    val missionHistoryUiModel =
        savedStateHandle.toRoute<ApprovalDetailRoute>(missionHistoryUiModelTypeMap).missionHistory
    private val missionHistoryId = missionHistoryUiModel.missionHistoryId
    private val missionUserId = missionHistoryUiModel.user.userId

    val commentUiState = combine<MyInfo, List<Comment>, CommentUiState>(
        userRepository.getMyInfo(),
        flow { emit(commentRepository.getComments(missionHistoryId)) }
    ) { myInfo, comments ->
        val commentUiModels = comments.flatMap { comment ->
            listOf(comment) + comment.children
        }.map { comment ->
            comment.toUiModel(
                isMyComment = comment.writer.userId == myInfo.id,
                isMissionHistoryUser = comment.writer.userId == missionUserId
            )
        }
        val validCommentsSize = commentUiModels
            .filter { comment -> !comment.isDeleted && !comment.isReported }
            .size

        CommentUiState.Success(
            comments = commentUiModels,
            validCommentsSize = validCommentsSize
        )
    }.catch { e ->
        emit(CommentUiState.Error)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CommentUiState.Loading
    )
}