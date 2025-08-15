package com.ilsangtech.ilsang.feature.approval.component

import android.content.Intent
import android.graphics.Bitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.model.RandomChallenge
import com.ilsangtech.ilsang.core.util.DateConverter
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_regular
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_semibold
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray200
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.heading02
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.title01
import com.ilsangtech.ilsang.feature.approval.BuildConfig
import java.io.File

@Composable
fun ApprovalItem(
    challenge: RandomChallenge,
    onProfileClick: () -> Unit,
    onLikeButtonClick: () -> Unit,
    onHateButtonClick: () -> Unit,
    onReportButtonClick: () -> Unit
) {
    val context = LocalContext.current
    val graphicsLayer = rememberGraphicsLayer()
    var isSharing by remember { mutableStateOf(false) }
    var showReportDialog by remember { mutableStateOf(false) }

    if (showReportDialog) {
        ChallengeReportDialog(
            onReportButtonClick = {
                showReportDialog = false
                onReportButtonClick()
            },
            onDismissRequest = { showReportDialog = false }
        )
    }

    LaunchedEffect(isSharing) {
        if (isSharing) {
            val imageBitmap = graphicsLayer.toImageBitmap()
            val fileName = "approval_item.png"
            val file = File(context.cacheDir, fileName).apply {
                deleteOnExit()
            }
            val outputStream = file.outputStream()

            imageBitmap.asAndroidBitmap()
                .compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.close()

            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )
            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                type = "image/png"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                setDataAndType(uri, "image/png")
            }
            context.startActivity(Intent.createChooser(sendIntent, "공유하기"))
            isSharing = false
        }
    }

    Surface(
        modifier = Modifier.drawWithContent {
            if (isSharing) {
                graphicsLayer.record {
                    this@drawWithContent.drawContent()
                }
                drawLayer(graphicsLayer)
            } else {
                drawContent()
            }
        },
        color = Color.White,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp,
                    vertical = 16.dp
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ApprovalItemHeader(
                userProfileImage = challenge.userProfileImage,
                userNickname = challenge.userNickName,
                createdAt = challenge.createdAt,
                onProfileClick = onProfileClick,
                onShareButtonClick = { isSharing = true },
                onReportButtonClick = { showReportDialog = true }
            )
            ApprovalItemBody(
                title = challenge.missionTitle,
                challengeImage = challenge.receiptImageId,
                likeCount = challenge.likeCnt,
                hateCount = challenge.hateCnt
            )
            if (!isSharing) {
                ApprovalFeedbackButtonRow(
                    isLiked = challenge.emoji?.isLike == true,
                    isHated = challenge.emoji?.isHate == true,
                    onLikeButtonClick = onLikeButtonClick,
                    onHateButtonClick = onHateButtonClick
                )
            }
        }
    }
}

@Composable
fun ApprovalItemHeader(
    userProfileImage: String?,
    userNickname: String,
    createdAt: String,
    onProfileClick: () -> Unit,
    onShareButtonClick: () -> Unit,
    onReportButtonClick: () -> Unit
) {
    var showDropDownMenu by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxWidth(),
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
        Column {
            Text(
                modifier = Modifier.clickable(
                    onClick = onProfileClick,
                    indication = null,
                    interactionSource = null
                ),
                text = userNickname,
                style = approvalItemNicknameTextStyle
            )
            Text(
                text = DateConverter.timeAgoSinceDate(createdAt),
                style = approvalItemTimeTextStyle
            )
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
fun ApprovalItemBody(
    title: String,
    challengeImage: String,
    likeCount: Int,
    hateCount: Int
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = title,
            style = title01,
            overflow = TextOverflow.Ellipsis
        )
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(5 / 4f)
                .clip(RoundedCornerShape(12.dp)),
            model = BuildConfig.IMAGE_URL + challengeImage,
            contentScale = ContentScale.Crop,
            contentDescription = null
        )

        Row(modifier = Modifier.height(24.dp)) {
            Box(modifier = Modifier.size(24.dp)) {
                Icon(
                    modifier = Modifier.align(Alignment.TopStart),
                    painter = painterResource(R.drawable.thumbs_up),
                    tint = gray200,
                    contentDescription = null
                )
            }
            Spacer(Modifier.width(4.dp))
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = likeCount.toString(),
                style = heading02,
                color = gray200
            )
            Spacer(Modifier.width(16.dp))
            Box(modifier = Modifier.size(24.dp)) {
                Icon(
                    modifier = Modifier.align(Alignment.BottomStart),
                    painter = painterResource(R.drawable.thumbs_down),
                    tint = gray200,
                    contentDescription = null
                )
            }
            Spacer(Modifier.width(4.dp))
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = hateCount.toString(),
                style = heading02,
                color = gray200
            )
        }
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
    fontFamily = FontFamily(Font(pretendard_semibold)),
    fontSize = 14.sp,
    lineHeight = 12.sp,
    letterSpacing = 0.sp,
    color = gray500
)

private val approvalItemTimeTextStyle = TextStyle(
    fontFamily = FontFamily(Font(pretendard_regular)),
    fontSize = 12.sp,
    lineHeight = 12.sp,
    letterSpacing = 0.sp,
    color = gray500
)

@Preview(showBackground = true)
@Composable
fun ApprovalItemPreview() {
    val challenge = RandomChallenge(
        challengeId = "",
        createdAt = "3시간전",
        hateCnt = 1,
        likeCnt = 2,
        missionTitle = "바닐라라떼 마시기",
        receiptImageId = "",
        status = "",
        userNickName = "일상123",
        customerId = "",
        userProfileImage = "",
        viewCount = 4,
        emoji = null
    )

    ApprovalItem(
        challenge = challenge,
        onProfileClick = {},
        onLikeButtonClick = {},
        onHateButtonClick = {},
        onReportButtonClick = {}
    )
}