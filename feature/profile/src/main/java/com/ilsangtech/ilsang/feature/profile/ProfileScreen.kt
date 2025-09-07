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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ilsangtech.ilsang.core.ui.mission.model.UserMissionHistoryUiModel
import com.ilsangtech.ilsang.core.ui.season.model.SeasonUiModel
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.title02
import com.ilsangtech.ilsang.feature.profile.component.UserCommercialPointContent
import com.ilsangtech.ilsang.feature.profile.component.UserObtainedPointContent
import com.ilsangtech.ilsang.feature.profile.component.UserProfileInfoCard
import com.ilsangtech.ilsang.feature.profile.component.userMissionHistoryContent
import com.ilsangtech.ilsang.feature.profile.model.ProfileUiState

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    navigateToChallenge: (UserMissionHistoryUiModel) -> Unit,
    onPopBackStack: () -> Unit
) {
    val uiState by profileViewModel.profileUiState.collectAsStateWithLifecycle()
    val selectedSeason by profileViewModel.selectedSeason.collectAsStateWithLifecycle()
    val userMissionHistories = profileViewModel.missionHistories.collectAsLazyPagingItems()

    ProfileScreen(
        uiState = uiState,
        selectedSeason = selectedSeason,
        userMissionHistories = userMissionHistories,
        onSeasonChanged = profileViewModel::updateSeason,
        onMissionHistoryClick = navigateToChallenge,
        onBackButtonClick = onPopBackStack
    )
}

@Composable
private fun ProfileScreen(
    uiState: ProfileUiState,
    selectedSeason: SeasonUiModel,
    userMissionHistories: LazyPagingItems<UserMissionHistoryUiModel>,
    onSeasonChanged: (SeasonUiModel) -> Unit,
    onMissionHistoryClick: (UserMissionHistoryUiModel) -> Unit,
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
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(
                    start = 20.dp, end = 20.dp,
                    bottom = 72.dp
                )
            ) {
                when (uiState) {
                    is ProfileUiState.Success -> {
                        item {
                            UserProfileInfoCard(
                                nickname = uiState.userProfileInfo.nickname,
                                profileImageId = uiState.userProfileInfo.profileImageId,
                                title = uiState.userProfileInfo.title,
                                point = uiState.userProfileInfo.point,
                            )
                        }
                        item { Spacer(Modifier.height(48.dp)) }

                        if (uiState.userCommercialPoint.topCommercialArea != null &&
                            uiState.userCommercialPoint.totalOwnerContributions.isNotEmpty()
                        ) {
                            item {
                                UserCommercialPointContent(
                                    userCommercialPointUiModel = uiState.userCommercialPoint
                                )
                            }
                            item { Spacer(Modifier.height(48.dp)) }
                        }

                        item {
                            UserObtainedPointContent(
                                nickname = uiState.userProfileInfo.nickname,
                                userObtainedPointUiModel = uiState.userPoint,
                                selectedSeason = selectedSeason,
                                onSeasonChange = onSeasonChanged
                            )
                        }
                        item { Spacer(Modifier.height(48.dp)) }
                        userMissionHistoryContent(
                            userMissionHistoryItems = userMissionHistories,
                            onClick = onMissionHistoryClick
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