package com.ilsangtech.ilsang.feature.approval.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.model.title.Title
import com.ilsangtech.ilsang.core.model.title.TitleGrade
import com.ilsangtech.ilsang.core.model.title.TitleType
import com.ilsangtech.ilsang.core.ui.BuildConfig
import com.ilsangtech.ilsang.core.ui.title.TitleGradeIcon
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.badge01TextStyle
import com.ilsangtech.ilsang.designsystem.theme.badge02TextStyle
import com.ilsangtech.ilsang.designsystem.theme.caption01
import com.ilsangtech.ilsang.designsystem.theme.caption02
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary100
import com.ilsangtech.ilsang.designsystem.theme.primary300
import com.ilsangtech.ilsang.designsystem.theme.primary500
import com.ilsangtech.ilsang.designsystem.theme.subTitle02
import com.ilsangtech.ilsang.designsystem.theme.tapBoldTextStyle
import com.ilsangtech.ilsang.designsystem.theme.toSp
import com.ilsangtech.ilsang.feature.approval.model.CommentUiModel

@Composable
internal fun CommentItem(
    modifier: Modifier = Modifier,
    comment: CommentUiModel,
    onCommentButtonClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .drawBehind {
                drawRect(color = if (comment.parentId == null) Color.White else background)
                drawLine(
                    start = Offset(x = 0f, y = size.height),
                    end = Offset(x = size.width, y = size.height),
                    color = gray100,
                    strokeWidth = 1.dp.toPx()
                )
            }
    ) {
        when {
            comment.isReported -> {
                ReportedCommentBox()
            }

            comment.isDeleted -> {
                DeletedCommentBox()
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .padding(start = comment.parentId?.let { 20.dp } ?: 0.dp)
                ) {
                    CommentItemHeader(
                        commentWriter = comment.commentWriter,
                        isMyComment = comment.isMyComment
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = comment.comment,
                        style = caption01
                    )
                    CommentItemFooter(
                        createdAt = comment.createdAt,
                        parentId = comment.parentId,
                        onCommentButtonClick = onCommentButtonClick
                    )
                }
            }
        }
    }
}

@Composable
private fun CommentItemHeader(
    modifier: Modifier = Modifier,
    commentWriter: CommentUiModel.CommentWriterUiModel,
    isMyComment: Boolean
) {
    var showPopup by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(35.dp)
                .clip(CircleShape),
            model = BuildConfig.IMAGE_URL + commentWriter.profileImageId,
            placeholder = painterResource(R.drawable.default_user_profile),
            error = painterResource(R.drawable.default_user_profile),
            contentDescription = null
        )
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = commentWriter.nickname,
                    style = TextStyle(
                        fontFamily = pretendardFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        lineHeight = 12.sp
                    ),
                    color = gray500
                )
                if (commentWriter.isMissionHistoryUser) {
                    Box(
                        modifier = Modifier
                            .height(20.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(primary100),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 6.dp),
                            text = "수행자",
                            style = badge02TextStyle.copy(
                                fontSize = 10.dp.toSp(),
                                lineHeight = 12.dp.toSp()
                            ),
                            color = primary500
                        )
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                TitleGradeIcon(
                    modifier = Modifier.size(20.dp),
                    titleGrade = commentWriter.title.grade
                )
                Text(
                    text = commentWriter.title.name,
                    style = badge01TextStyle,
                    color = gray500
                )
            }
        }
        Spacer(Modifier.weight(1f))
        Icon(
            modifier = Modifier
                .size(30.dp)
                .clickable(
                    onClick = { showPopup = true },
                    indication = null,
                    interactionSource = null
                ),
            painter = painterResource(R.drawable.icon_more_vertical),
            tint = gray500,
            contentDescription = null
        )
        Box(modifier = Modifier.align(Alignment.Bottom)) {
            DropdownMenu(
                expanded = showPopup,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(color = gray100, width = 1.dp),
                containerColor = Color.White,
                offset = DpOffset(x = 0.dp, y = 10.dp),
                properties = PopupProperties(focusable = true),
                onDismissRequest = { showPopup = false }
            ) {
                Box(
                    modifier = Modifier
                        .width(150.dp)
                        .padding(horizontal = 12.dp)
                        .padding(vertical = 2.dp)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterStart),
                        text = if (isMyComment) "삭제" else "신고",
                        style = TextStyle(
                            fontFamily = pretendardFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                            lineHeight = 1.3.sp
                        ),
                        color = gray500
                    )
                    Icon(
                        modifier = Modifier
                            .size(15.dp)
                            .align(Alignment.CenterEnd),
                        painter = if (isMyComment) {
                            painterResource(R.drawable.icon_delete)
                        } else {
                            painterResource(R.drawable.report)
                        },
                        tint = gray500,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
private fun CommentItemFooter(
    modifier: Modifier = Modifier,
    createdAt: String,
    parentId: Int?,
    onCommentButtonClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = if (parentId == null) 12.dp else 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (parentId == null) {
            Text(
                modifier = Modifier.clickable(
                    onClick = onCommentButtonClick,
                    indication = null,
                    interactionSource = null
                ),
                text = "답글 달기",
                style = tapBoldTextStyle,
                color = primary300
            )
        }
        Text(
            text = createdAt,
            style = caption02,
            color = gray500
        )
    }
}

@Composable
private fun ReportedCommentBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(144.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "신고 누적으로 숨겨진 댓글입니다.",
            style = subTitle02,
            color = gray500,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun DeletedCommentBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(144.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "삭제된 댓글입니다.",
            style = subTitle02,
            color = gray500,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
internal fun EmptyCommentBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(144.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "댓글이 아직 없어요",
            style = subTitle02,
            color = gray500,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CommentItemPreview() {
    val commentContent =
        "가끔은 아무 이유 없이 마음이 공허할 때가 있다. 일을 하고, 사람을 만나고, " +
                "해야 할 일을 모두 끝냈는데도 어딘가 비어 있는 느낌이 든다." +
                " 그럴 때마다 나는 잠시 멈춰서 내가 지금 어디쯤 서 있는지 돌아본다." +
                " 목표를 향해 달리다 보면 정작 나 자신을 놓치기 쉽다." +
                " 그래서 나는 요즘 일부러 아무것도 하지 않는 시간을 만든다. " +
                "창문을 열고 바람을 느끼거나, 커피 한 잔을 천천히 마시는" +
                " 그 짧은 순간이 이상하게도 나를 다시 현실로 데려온다." +
                " 완벽하지 않아도 괜찮다는 사실을 기억하려 한다. 그렇게 하루를 살아내는 것만으로도 " +
                "충분히 잘하고 있다고."

    CommentItem(
        modifier = Modifier.padding(bottom = 10.dp),
        comment = CommentUiModel(
            id = 2,
            parentId = null,
            comment = commentContent,
            commentWriter = CommentUiModel.CommentWriterUiModel(
                userId = "",
                nickname = "일상123",
                profileImageId = null,
                title = Title(
                    name = "세상을 움직이는 자",
                    grade = TitleGrade.Standard,
                    type = TitleType.Contribution
                ),
                isMissionHistoryUser = false
            ),
            createdAt = "2025.04.12 12:00",
            isMyComment = false,
            isReported = false,
            isDeleted = false
        ),
        onCommentButtonClick = {}
    )
}