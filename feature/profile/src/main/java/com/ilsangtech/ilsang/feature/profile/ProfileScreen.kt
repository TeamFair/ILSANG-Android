package com.ilsangtech.ilsang.feature.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ilsangtech.ilsang.core.model.Challenge
import com.ilsangtech.ilsang.core.model.UserInfo
import com.ilsangtech.ilsang.core.model.UserXpStats
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.title02
import com.ilsangtech.ilsang.feature.profile.component.UserInfoContent
import com.ilsangtech.ilsang.feature.profile.component.UserStatsContent
import com.ilsangtech.ilsang.feature.profile.component.userChallengeContent
import kotlinx.coroutines.flow.flowOf

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    navigateToChallenge: (Challenge) -> Unit,
    onPopBackStack: () -> Unit
) {
    val uiState by profileViewModel.profileUiState.collectAsStateWithLifecycle()
    val challengePaging = profileViewModel.challengePaging.collectAsLazyPagingItems()
    ProfileScreen(
        uiState = uiState,
        challengePaging = challengePaging,
        navigateToChallenge = navigateToChallenge,
        onBackButtonClick = onPopBackStack
    )
}

@Composable
private fun ProfileScreen(
    uiState: ProfileUiState,
    challengePaging: LazyPagingItems<Challenge>,
    navigateToChallenge: (Challenge) -> Unit,
    onBackButtonClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = background
    ) {
        Column(modifier = Modifier.statusBarsPadding()) {
            ProfileScreenHeader(
                onBackButtonClick = onBackButtonClick
            )
            LazyColumn(
                contentPadding = PaddingValues(
                    top = 16.dp,
                    bottom = 62.dp,
                    start = 20.dp,
                    end = 20.dp
                )
            ) {
                when (uiState) {
                    is ProfileUiState.Success -> {
                        item {
                            UserInfoContent(
                                nickname = uiState.userInfo.nickname,
                                xpPoint = uiState.userInfo.xpPoint
                            )
                        }
                        item { Spacer(Modifier.height(24.dp)) }
                        item {
                            UserStatsContent(userXpStats = uiState.userXpStats)
                        }
                        item { Spacer(Modifier.height(24.dp)) }
                        userChallengeContent(
                            challengePaging = challengePaging,
                            onChallengeClick = navigateToChallenge
                        )
                    }

                    is ProfileUiState.Loading -> {

                    }

                    is ProfileUiState.Error -> {

                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileScreenHeader(
    onBackButtonClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 45.dp)
            .padding(
                vertical = 7.dp,
                horizontal = 12.dp
            )
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable(
                    onClick = onBackButtonClick,
                    interactionSource = null,
                    indication = null
                ),
            painter = painterResource(R.drawable.icon_back),
            tint = gray500,
            contentDescription = null
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "프로필 정보",
            style = title02.copy(color = gray500)
        )

    }
}

@Composable
@Preview
private fun ProfileScreenPreview() {
    ProfileScreen(
        uiState = ProfileUiState.Success(
            userInfo = UserInfo(
                completeChallengeCount = 0,
                couponCount = 0,
                nickname = "가나다라",
                profileImage = null,
                status = "",
                xpPoint = 0
            ),
            userXpStats = UserXpStats(
                charmStat = 0,
                funStat = 0,
                intellectStat = 0,
                strengthStat = 0,
            )
        ),
        challengePaging = flowOf(
            PagingData.from(
                data = listOf<Challenge>()
            )
        ).collectAsLazyPagingItems(),
        navigateToChallenge = {},
        onBackButtonClick = {}
    )
}