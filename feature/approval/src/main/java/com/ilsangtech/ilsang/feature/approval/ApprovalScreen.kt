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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ilsangtech.ilsang.core.model.RandomChallenge
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.heading01
import com.ilsangtech.ilsang.designsystem.theme.tapRegularTextStyle
import com.ilsangtech.ilsang.feature.approval.component.ApprovalItem
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun ApprovalScreen(
    approvalViewModel: ApprovalViewModel = hiltViewModel(),
    navigateToProfile: (String) -> Unit
) {
    val randomChallenges = approvalViewModel.randomChallenges.collectAsLazyPagingItems()
    val isReportSuccess by approvalViewModel.isReportSuccess.collectAsStateWithLifecycle()

    LaunchedEffect(isReportSuccess) {
        if (isReportSuccess == true) {
            randomChallenges.refresh()
        }
        isReportSuccess?.let {
            approvalViewModel.resetReportStatus()
        }
    }
    ApprovalScreen(
        randomChallenges = randomChallenges,
        onLikeButtonClick = approvalViewModel::likeChallenge,
        onHateButtonClick = approvalViewModel::hateChallenge,
        onReportButtonClick = approvalViewModel::reportChallenge,
        navigateToProfile = navigateToProfile
    )
}

@Composable
private fun ApprovalScreen(
    randomChallenges: LazyPagingItems<RandomChallenge>,
    onLikeButtonClick: (RandomChallenge) -> Unit,
    onHateButtonClick: (RandomChallenge) -> Unit,
    onReportButtonClick: (RandomChallenge) -> Unit,
    navigateToProfile: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        color = background
    ) {
        if (
            randomChallenges.loadState.refresh is LoadState.NotLoading &&
            randomChallenges.itemCount == 0
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
                        painter = painterResource(R.drawable.icon_approval_empty),
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
            items(randomChallenges.itemCount) {
                randomChallenges[it]?.let { randomChallenge ->
                    ApprovalItem(
                        challenge = randomChallenge,
                        onProfileClick = { navigateToProfile(randomChallenge.customerId) },
                        onLikeButtonClick = { onLikeButtonClick(randomChallenge) },
                        onHateButtonClick = { onHateButtonClick(randomChallenge) },
                        onReportButtonClick = { onReportButtonClick(randomChallenge) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ApprovalScreenPreview() {
    val randomChallenges = listOf(
        RandomChallenge(
            challengeId = "1",
            createdAt = "2023-10-27T10:00:00Z",
            customerId = "customer1",
            hateCnt = 0,
            likeCnt = 10,
            missionTitle = "Mission 1",
            receiptImageId = "receipt1",
            status = "APPROVED",
            userNickName = "User1",
            userProfileImage = null,
            viewCount = 100,
            emoji = null
        ),
        RandomChallenge(
            challengeId = "2",
            createdAt = "2023-10-27T11:00:00Z",
            customerId = "customer2",
            hateCnt = 2,
            likeCnt = 5,
            missionTitle = "Mission 2",
            receiptImageId = "receipt2",
            status = "PENDING",
            userNickName = "User2",
            userProfileImage = "image_url",
            viewCount = 50,
            emoji = null
        )
    )
    val lazyPagingItems = flowOf(PagingData.from(randomChallenges)).collectAsLazyPagingItems()

    ApprovalScreen(
        randomChallenges = lazyPagingItems,
        onLikeButtonClick = {},
        onHateButtonClick = {},
        onReportButtonClick = {},
        navigateToProfile = {})
}