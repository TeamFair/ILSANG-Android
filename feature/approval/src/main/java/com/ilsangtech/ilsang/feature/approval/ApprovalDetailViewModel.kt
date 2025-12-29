package com.ilsangtech.ilsang.feature.approval

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.delete
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ilsangtech.ilsang.core.domain.CommentRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.feature.approval.model.CommentAlertUiState
import com.ilsangtech.ilsang.feature.approval.model.CommentUiModel
import com.ilsangtech.ilsang.feature.approval.model.CommentUiState
import com.ilsangtech.ilsang.feature.approval.model.toUiModel
import com.ilsangtech.ilsang.feature.approval.navigation.ApprovalDetailRoute
import com.ilsangtech.ilsang.feature.approval.navigation.missionHistoryUiModelTypeMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

    private val selectedComment = MutableStateFlow<CommentUiModel?>(null)
    val selectedCommentWriter = selectedComment.map { comment ->
        comment?.commentWriter?.nickname
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val commentTextField = TextFieldState()

    private val commentRetryFlow = MutableSharedFlow<Unit>()

    @OptIn(ExperimentalCoroutinesApi::class)
    val commentUiState = commentRetryFlow
        .onStart {
            emit(Unit)
        }.flatMapLatest {
            combine(
                userRepository.getMyInfo(),
                flow { emit(commentRepository.getComments(missionHistoryId)) },
                commentAlertUiState
            ) { myInfo, comments, alertUiState ->
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
                    validCommentsSize = validCommentsSize,
                    alertUiState = alertUiState
                ) as CommentUiState
            }
        }.catch { e ->
            emit(CommentUiState.Error)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CommentUiState.Loading
        )
    private val _commentAlertUiState = MutableStateFlow<CommentAlertUiState?>(null)
    val commentAlertUiState: StateFlow<CommentAlertUiState?> = _commentAlertUiState

    private val _listScrollRequestPosition = MutableStateFlow<Int?>(null)
    val listScrollPosition: StateFlow<Int?> = _listScrollRequestPosition

    fun selectComment(commentUiModel: CommentUiModel) {
        selectedComment.update { commentUiModel }
    }

    fun unselectComment() {
        selectedComment.update { null }
    }

    fun createComment() {
        viewModelScope.launch {
            val text = commentTextField.text.toString()
            when {
                text.trim().isBlank() -> _commentAlertUiState.update {
                    CommentAlertUiState.Empty
                }

                else -> commentRepository.createComment(
                    missionHistoryId = missionHistoryId,
                    parentId = selectedComment.value?.id,
                    comment = commentTextField.text.toString()
                ).onSuccess {
                    commentRetryFlow.emit(Unit)
                    (commentUiState.value as? CommentUiState.Success)?.let { state ->
                        _listScrollRequestPosition.update {
                            if (selectedComment.value?.id == null) {
                                state.comments.size - 1
                            } else {
                                state.comments.indexOfLast {
                                    it.parentId == selectedComment.value?.id
                                }
                            }
                        }
                    }
                }
            }
            commentTextField.clearText()
            unselectComment()
        }
    }

    fun deleteComment(commentId: Int) {
        viewModelScope.launch {
            commentRepository.deleteComment(commentId).onSuccess {
                commentRetryFlow.emit(Unit)
            }
        }
    }

    fun clearScrollPosition() {
        _listScrollRequestPosition.update { null }
    }

    suspend fun validateComment() {
        val invalidInputRegex =
            Regex("(<[^>]+>)|(https?://\\S+)")
        snapshotFlow { commentTextField.text }.collectLatest { text ->
            if (text.length > 300) {
                commentTextField.edit { delete(300, text.length) }
                _commentAlertUiState.update { CommentAlertUiState.TooLong }
            }
            if (invalidInputRegex.containsMatchIn(text)) {
                commentTextField.clearText()
                _commentAlertUiState.update { CommentAlertUiState.Invalid }
            }
        }
    }

    fun shownCommentAlert() {
        _commentAlertUiState.update { null }
    }
}