package com.ilsangtech.ilsang.feature.approval.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.model.title.TitleGrade
import com.ilsangtech.ilsang.core.ui.title.TitleGradeIcon
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.badge01TextStyle
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily

@Composable
internal fun ApprovalItemUserInfo(
    modifier: Modifier = Modifier,
    userProfileImage: String?,
    userNickname: String,
    titleGrade: TitleGrade?,
    titleName: String?,
    onProfileClick: () -> Unit,
    onShareButtonClick: () -> Unit,
    onReportButtonClick: () -> Unit
) {
    var showDropDownMenu by remember { mutableStateOf(false) }
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(CircleShape)
                .size(35.dp)
                .clickable(
                    onClick = onProfileClick,
                    indication = null,
                    interactionSource = null
                ),
            model = userProfileImage,
            contentDescription = null,
            error = painterResource(R.drawable.default_user_profile),
            placeholder = painterResource(R.drawable.default_user_profile)
        )
        Spacer(Modifier.width(10.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                modifier = Modifier.clickable(
                    onClick = onProfileClick,
                    indication = null,
                    interactionSource = null
                ),
                text = userNickname,
                style = approvalItemNicknameTextStyle
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (titleGrade != null && titleName != null) {
                    TitleGradeIcon(
                        modifier = Modifier.size(20.dp),
                        titleGrade = titleGrade
                    )
                    Text(
                        text = titleName,
                        style = badge01TextStyle,
                        color = gray500
                    )
                }
            }
        }
        Spacer(Modifier.weight(1f))
        Icon(
            modifier = Modifier
                .size(30.dp)
                .clickable(
                    indication = null,
                    interactionSource = null,
                    onClick = { showDropDownMenu = true }
                ),
            painter = painterResource(R.drawable.more_vertical),
            tint = gray500,
            contentDescription = null
        )
        ApprovalDropDownMenu(
            modifier = Modifier.align(Alignment.Bottom),
            showDropDownMenu = showDropDownMenu,
            onDismissRequest = { showDropDownMenu = false },
            onShareButtonClick = {
                onShareButtonClick()
                showDropDownMenu = false
            },
            onReportButtonClick = {
                onReportButtonClick()
                showDropDownMenu = false
            }
        )
    }
}

@Composable
private fun ApprovalDropDownMenu(
    modifier: Modifier = Modifier,
    showDropDownMenu: Boolean,
    onDismissRequest: () -> Unit,
    onShareButtonClick: () -> Unit,
    onReportButtonClick: () -> Unit
) {
    Box(modifier = modifier) {
        DropdownMenu(
            offset = DpOffset(
                x = 0.dp,
                y = 2.5.dp
            ),
            expanded = showDropDownMenu,
            onDismissRequest = onDismissRequest,
            shape = RoundedCornerShape(12.dp),
            containerColor = Color.White
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .clickable(
                            indication = null,
                            interactionSource = null,
                            onClick = onShareButtonClick
                        )
                        .padding(horizontal = 12.dp)
                        .padding(
                            top = 2.dp,
                            bottom = 10.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        modifier = Modifier.width(128.dp),
                        text = "공유하기",
                        style = TextStyle(
                            fontFamily = pretendardFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                            lineHeight = 1.3.em,
                            color = gray500
                        )
                    )
                    Icon(
                        modifier = Modifier.size(18.dp),
                        painter = painterResource(R.drawable.share),
                        tint = gray500,
                        contentDescription = null
                    )
                }
                HorizontalDivider(color = gray100)
                Row(
                    modifier = Modifier
                        .clickable(
                            indication = null,
                            interactionSource = null,
                            onClick = onReportButtonClick
                        )
                        .padding(horizontal = 12.dp)
                        .padding(
                            top = 10.dp,
                            bottom = 2.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        modifier = Modifier.width(128.dp),
                        text = "신고하기",
                        style = TextStyle(
                            fontFamily = pretendardFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                            lineHeight = 1.3.em,
                            color = gray500
                        )
                    )
                    Icon(
                        modifier = Modifier.size(18.dp),
                        painter = painterResource(R.drawable.report),
                        tint = gray500,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

private val approvalItemNicknameTextStyle = TextStyle(
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 14.sp,
    lineHeight = 12.sp,
    letterSpacing = 0.sp,
    color = gray500
)

@Preview(showBackground = true)
@Composable
private fun ApprovalItemUserInfoPreview() {
    ApprovalItemUserInfo(
        userProfileImage = null,
        userNickname = " 일상123",
        titleGrade = TitleGrade.Standard,
        titleName = "세상을 움직이는 자",
        onProfileClick = {},
        onShareButtonClick = {},
        onReportButtonClick = {}
    )
}




