package com.ilsangtech.ilsang.feature.approval

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ilsangtech.ilsang.core.model.mission.MissionHistoryUser
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.core.model.title.Title
import com.ilsangtech.ilsang.core.model.title.TitleGrade
import com.ilsangtech.ilsang.core.model.title.TitleType
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.bodyTextStyle
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray200
import com.ilsangtech.ilsang.designsystem.theme.heading01
import com.ilsangtech.ilsang.feature.approval.component.ApprovalDetailHeader
import com.ilsangtech.ilsang.feature.approval.component.ApprovalDetailItem
import com.ilsangtech.ilsang.feature.approval.component.CommentItem
import com.ilsangtech.ilsang.feature.approval.component.CommentTextField
import com.ilsangtech.ilsang.feature.approval.component.EmptyCommentBox
import com.ilsangtech.ilsang.feature.approval.component.RecommentProgressCard
import com.ilsangtech.ilsang.feature.approval.model.CommentUiModel
import com.ilsangtech.ilsang.feature.approval.model.CommentUiState
import com.ilsangtech.ilsang.feature.approval.model.MissionHistoryUiModel

@Composable
internal fun ApprovalDetailScreen(
    viewModel: ApprovalDetailViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit,
    onProfileClick: (String) -> Unit,
    onMissionHistoryReportClick: (Int) -> Unit
) {
    val missionHistory = viewModel.missionHistoryUiModel
    val commentTextField = viewModel.commentTextField
    val commentUiState by viewModel.commentUiState.collectAsStateWithLifecycle()
    val selectedCommentWriter by viewModel.selectedCommentWriter.collectAsStateWithLifecycle()
    val listScrollPosition by viewModel.listScrollPosition.collectAsStateWithLifecycle()

    ApprovalDetailScreen(
        missionHistory = missionHistory,
        commentTextFieldState = commentTextField,
        commentUiState = commentUiState,
        selectedCommentWriter = selectedCommentWriter,
        listScrollPosition = listScrollPosition,
        onBackButtonClick = onBackButtonClick,
        onProfileClick = onProfileClick,
        onShareButtonClick = {},
        onMissionHistoryReportClick = {
            onMissionHistoryReportClick(missionHistory.missionHistoryId)
        },
        onCtaButtonClick = {},
        onCommentSelected = viewModel::selectComment,
        onCommentUnselected = viewModel::unselectComment,
        onSendButtonClick = viewModel::createComment,
        onCommentReportClick = {},
        onCommentDeleteClick = viewModel::deleteComment,
        onListScrolled = viewModel::clearScrollPosition
    )
}

@Composable
private fun ApprovalDetailScreen(
    missionHistory: MissionHistoryUiModel,
    commentTextFieldState: TextFieldState,
    commentUiState: CommentUiState,
    listScrollPosition: Int?,
    selectedCommentWriter: String?,
    onBackButtonClick: () -> Unit,
    onProfileClick: (String) -> Unit,
    onShareButtonClick: () -> Unit,
    onMissionHistoryReportClick: () -> Unit,
    onCtaButtonClick: () -> Unit,
    onCommentSelected: (CommentUiModel) -> Unit,
    onCommentUnselected: () -> Unit,
    onSendButtonClick: () -> Unit,
    onCommentDeleteClick: (Int) -> Unit,
    onCommentReportClick: (Int) -> Unit,
    onListScrolled: () -> Unit
) {
    val focusRequest = remember { FocusRequester() }
    val lazyListState = rememberLazyListState()

    LaunchedEffect(listScrollPosition) {
        listScrollPosition?.let { position ->
            lazyListState.animateScrollToItem(position + 3)
            onListScrolled()
        }
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        color = Color.White
    ) {
        Column {
            ApprovalDetailHeader(
                modifier = Modifier.background(background),
                onBackButtonClick = onBackButtonClick
            )
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                    .background(color = Color.White),
                state = lazyListState,
            ) {
                item {
                    ApprovalDetailItem(
                        missionHistory = missionHistory,
                        onProfileClick = { onProfileClick(missionHistory.user.userId) },
                        onShareButtonClick = onShareButtonClick,
                        onReportButtonClick = onMissionHistoryReportClick,
                        onCtaButtonClick = onCtaButtonClick
                    )
                }
                val successUiState = commentUiState as? CommentUiState.Success
                val comments = successUiState?.comments ?: emptyList()
                val commentsCount = successUiState?.validCommentsSize ?: 0

                item {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .padding(bottom = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "댓글",
                            style = heading01
                        )
                        if (commentUiState !is CommentUiState.Loading) {
                            Text(
                                text = "$commentsCount",
                                style = bodyTextStyle
                            )
                        }
                    }
                }
                item { HorizontalDivider(color = gray100) }
                if (commentUiState is CommentUiState.Loading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(144.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = gray200)
                        }
                    }
                } else {
                    if (comments.isEmpty()) item { EmptyCommentBox() }
                    items(
                        items = comments,
                        key = { it.id }
                    ) { comment ->
                        CommentItem(
                            comment = comment,
                            onProfileClick = {
                                onProfileClick(comment.commentWriter.userId)
                            },
                            onCommentButtonClick = {
                                onCommentSelected(comment)
                                focusRequest.requestFocus()
                            },
                            onReportButtonClick = {
                                onCommentReportClick(comment.id)
                            },
                            onDeleteButtonClick = {
                                onCommentDeleteClick(comment.id)
                            }
                        )
                    }
                }
            }
            selectedCommentWriter?.let { commentWriter ->
                RecommentProgressCard(
                    commentWriterName = commentWriter,
                    onCancelButtonClick = {
                        onCommentUnselected()
                        focusRequest.freeFocus()
                    }
                )
            }
            CommentTextField(
                modifier = Modifier.focusRequester(focusRequest),
                textFieldState = commentTextFieldState,
                onButtonClick = onSendButtonClick
            )
        }
    }
}

@Preview
@Composable
private fun ApprovalDetailScreenPreview() {
    ApprovalDetailScreen()
}