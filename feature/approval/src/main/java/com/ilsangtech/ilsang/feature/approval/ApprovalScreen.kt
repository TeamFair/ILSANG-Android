package com.ilsangtech.ilsang.feature.approval

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ilsangtech.ilsang.core.model.mission.MissionHistoryUser
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.core.model.title.Title
import com.ilsangtech.ilsang.core.model.title.TitleGrade
import com.ilsangtech.ilsang.core.model.title.TitleType
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.heading01
import com.ilsangtech.ilsang.designsystem.theme.tapRegularTextStyle
import com.ilsangtech.ilsang.feature.approval.component.ApprovalItem
import com.ilsangtech.ilsang.feature.approval.model.MissionHistoryUiModel
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun ApprovalScreen(
    approvalViewModel: ApprovalViewModel = hiltViewModel(),
    navigateToApprovalDetail: (MissionHistoryUiModel) -> Unit,
    navigateToQuestTab: (Int) -> Unit,
    navigateToProfile: (String) -> Unit,
    navigateToReport: (Int) -> Unit
) {
    val randomMissionHistoryItems =
        approvalViewModel.randomMissionHistories.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        approvalViewModel.missionHistoryRefreshTrigger.collect {
            randomMissionHistoryItems.refresh()
        }
    }

    ApprovalScreen(
        missionHistoryItems = randomMissionHistoryItems,
        onApprovalItemClick = navigateToApprovalDetail,
        onCtaCardClick = navigateToQuestTab,
        onLikeButtonClick = approvalViewModel::likeChallenge,
        onReportButtonClick = navigateToReport,
        navigateToProfile = navigateToProfile
    )
}

@Composable
private fun ApprovalScreen(
    missionHistoryItems: LazyPagingItems<MissionHistoryUiModel>,
    onApprovalItemClick: (MissionHistoryUiModel) -> Unit,
    onCtaCardClick: (Int) -> Unit,
    onLikeButtonClick: (MissionHistoryUiModel) -> Unit,
    onReportButtonClick: (Int) -> Unit,
    navigateToProfile: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        color = background
    ) {
        if (
            missionHistoryItems.loadState.refresh is LoadState.NotLoading &&
            missionHistoryItems.itemCount == 0
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_item_empty),
                        tint = Color.Unspecified,
                        contentDescription = null
                    )
                    Text(
                        text = "기록을 만들어 볼까요?",
                        style = heading01,
                        color = gray500
                    )
                    Text(
                        text = "아직 유저들의 인증사진이\n" +
                                "등록되지 않았어요!",
                        style = tapRegularTextStyle,
                        color = gray400,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 48.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(Modifier.statusBarsPadding()) }
            items(missionHistoryItems.itemCount) {
                missionHistoryItems[it]?.let { randomMissionHistory ->
                    ApprovalItem(
                        missionHistory = randomMissionHistory,
                        onItemClick = { onApprovalItemClick(randomMissionHistory) },
                        onProfileClick = { navigateToProfile(randomMissionHistory.user.userId) },
                        onCtaCardClick = { /*TODO 실제 퀘스트 id 적용 필요*/ onCtaCardClick(0) },
                        onLikeButtonClick = { onLikeButtonClick(randomMissionHistory) },
                        onReportButtonClick = { onReportButtonClick(randomMissionHistory.missionHistoryId) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ApprovalScreenPreview() {
    val missionHistoryItems = listOf(
        MissionHistoryUiModel(
            commercialAreaName = "강남",
            createdAt = "2023.10.27 10:00",
            currentUserEmojis = listOf("LIKE"),
            hateCount = 0,
            likeCount = 10,
            missionHistoryId = 1,
            submitImageId = "sample_image_1",
            title = "강남역 10번 출구에서 사진 찍기",
            writerName = "홍길동",
            user = MissionHistoryUser(
                userId = "user1",
                nickname = "개발자",
                profileImageId = null,
                title = Title(
                    name = "개발의 신",
                    grade = TitleGrade.Legend,
                    type = TitleType.Contribution
                )
            ),
            viewCount = 100,
            questType = QuestType.Normal,
            commentCount = 20,
            shareCount = 20,
            lastCompleteDate = null,
            expireDate = "",
            isIsZoneQuest = false,
            questId = 0
        ),
        MissionHistoryUiModel(
            commercialAreaName = "홍대",
            createdAt = "2023.10.28 12:00",
            currentUserEmojis = emptyList(),
            hateCount = 2,
            likeCount = 5,
            missionHistoryId = 2,
            submitImageId = "sample_image_2",
            title = "홍대에서 버스킹 구경하기",
            writerName = "김철수",
            user = MissionHistoryUser(
                userId = "user2",
                nickname = "디자이너",
                profileImageId = "profile_image_2",
                title = Title(
                    name = "디자인의 달인",
                    grade = TitleGrade.Rare,
                    type = TitleType.Commercial
                )
            ),
            viewCount = 50,
            questType = QuestType.Repeat.Weekly,
            commentCount = 20,
            shareCount = 20,
            lastCompleteDate = null,
            expireDate = "",
            isIsZoneQuest = false,
            questId = 0
        )
    )
    val lazyPagingItems =
        flowOf(PagingData.from(missionHistoryItems))
            .collectAsLazyPagingItems()

    ApprovalScreen(
        missionHistoryItems = lazyPagingItems,
        onApprovalItemClick = {},
        onCtaCardClick = {},
        onLikeButtonClick = {},
        onReportButtonClick = {},
        navigateToProfile = {}
    )
}