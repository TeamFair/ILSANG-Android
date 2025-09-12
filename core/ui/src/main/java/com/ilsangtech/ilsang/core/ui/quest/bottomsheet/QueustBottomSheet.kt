package com.ilsangtech.ilsang.core.ui.quest.bottomsheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.core.model.coupon.QuestDetailCoupon
import com.ilsangtech.ilsang.core.model.mission.Mission
import com.ilsangtech.ilsang.core.model.quest.QuestDetail
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.core.model.reward.RewardPoint
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_regular
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_semibold
import com.ilsangtech.ilsang.designsystem.component.IlsangBottomSheet
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.primary300

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestBottomSheet(
    quest: QuestDetail,
    bottomSheetState: SheetState,
    onFavoriteClick: () -> Unit,
    onMissionImageClick: () -> Unit,
    onApproveButtonClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    IlsangBottomSheet(
        bottomSheetState = bottomSheetState,
        onDismissRequest = onDismiss
    ) {
        QuestBottomSheetHeader(
            isFavorite = quest.favoriteYn,
            onFavoriteClick = onFavoriteClick
        )
        QuestBottomSheetContent(
            quest = quest,
            onImageClick = onMissionImageClick
        )
        Spacer(Modifier.height(16.dp))
        QuestBottomSheetFooter(onClick = onApproveButtonClick)
    }
}

@Composable
private fun QuestBottomSheetHeader(
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 14.dp,
                bottom = 15.dp
            )
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "퀘스트 정보",
            style = questBottomSheetTitleStyle
        )
        Icon(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 20.dp)
                .clickable(
                    onClick = onFavoriteClick,
                    indication = null,
                    interactionSource = null
                ),
            painter = painterResource(com.ilsangtech.ilsang.designsystem.R.drawable.icon_favorite),
            contentDescription = "즐겨찾기",
            tint = if (isFavorite) primary300 else gray100
        )
    }
}

@Composable
private fun QuestBottomSheetContent(
    modifier: Modifier = Modifier,
    quest: QuestDetail,
    onImageClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        QuestInfoContent(quest = quest)
        Row(modifier = Modifier.fillMaxWidth()) {
            if (quest.missions.firstOrNull()?.type == "PHOTO") {
                val imageIds = if (quest.questType is QuestType.Repeat) {
                    listOf(quest.missions.first().exampleImageIds.firstOrNull())
                } else {
                    quest.missions.first().exampleImageIds
                        .plus(List(3 - quest.missions.first().exampleImageIds.size) { null })
                }

                QuestApprovalExample(
                    modifier = Modifier.weight(1f),
                    imageIds = imageIds,
                    onImageClick = onImageClick
                )
            }
            if (quest.questType is QuestType.Repeat) {
                MyQuestRank(
                    modifier = Modifier.weight(1f),
                    rank = quest.userRank
                )
            }
        }
        ObtainablePointContent(rewardPoints = quest.rewards)
    }
}

@Composable
private fun QuestBottomSheetFooter(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "퀘스트를 수행하셨나요?\n" +
                    "인증 후 포인트를 적립받으세요",
            textAlign = TextAlign.Center,
            style = questBottomSheetDescriptionTextStyle
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onClick,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primary
            ),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            Text(
                text = "퀘스트 인증하기",
                style = questBottomSheetApproveButtonTextStyle
            )
        }
    }
}

private val questBottomSheetTitleStyle = TextStyle(
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 17.sp,
    lineHeight = 22.sp,
    color = gray500
)

private val questBottomSheetDescriptionTextStyle = TextStyle(
    fontFamily = FontFamily(Font(pretendard_regular)),
    fontSize = 14.sp,
    lineHeight = 22.sp,
    color = gray500
)

private val questBottomSheetApproveButtonTextStyle = TextStyle(
    fontFamily = FontFamily(Font(pretendard_semibold)),
    fontSize = 16.sp,
    lineHeight = 18.sp,
    color = Color.White
)

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun QuestBottomSheetPreviewQuestDetail() {
    val quest = QuestDetail(
        id = 1,
        expireDate = "2023-12-31",
        favoriteYn = true,
        imageId = "imageId",
        mainImageId = "mainImageId",
        missions = listOf(
            Mission(
                id = 1,
                exampleImageIds = listOf("imageId1", "imageId2"),
                title = "사진 인증 미션",
                type = "PHOTO"
            )
        ),
        coupons = listOf(
            QuestDetailCoupon(
                id = 1,
                name = "쿠폰 이름",
                imageId = "couponImageId",
                storeName = "쿠폰 가게 이름",
                validTo = "2023-12-31",
                description = "쿠폰 설명"
            )
        ),
        questType = QuestType.Normal,
        rewards = listOf(
            RewardPoint.Metro(2),
            RewardPoint.Commercial(5),
            RewardPoint.Contribute(10)
        ),
        title = "Sample Quest",
        userRank = 1,
        writerName = "사장님"
    )
    val bottomSheetState = rememberStandardBottomSheetState()
    QuestBottomSheet(
        quest = quest,
        bottomSheetState = bottomSheetState,
        onFavoriteClick = {},
        onMissionImageClick = {},
        onApproveButtonClick = {},
        onDismiss = {}
    )
}
