package com.ilsangtech.ilsang.feature.my.screens.challenge_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ilsangtech.ilsang.core.model.mission.MissionType
import com.ilsangtech.ilsang.core.ui.mission.MissionHistoryDetailDateItem
import com.ilsangtech.ilsang.core.ui.mission.MissionHistoryDetailInfoItem
import com.ilsangtech.ilsang.core.ui.mission.MissionHistoryDetailPhotoItem
import com.ilsangtech.ilsang.core.ui.mission.MissionHistoryDetailPointItem
import com.ilsangtech.ilsang.core.ui.mission.MissionHistoryDetailWriterItem
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.gray200
import com.ilsangtech.ilsang.feature.my.screens.challenge_detail.component.ChallengeDeleteDialog
import com.ilsangtech.ilsang.feature.my.screens.challenge_detail.component.MissionHistoryDetailQuizItem
import com.ilsangtech.ilsang.feature.my.screens.challenge_detail.component.MyChallengeDetailHeader
import com.ilsangtech.ilsang.feature.my.screens.challenge_detail.model.MyChallengeDetailUiState

@Composable
fun MyChallengeDetailScreen(
    viewModel: MyChallengeDetailViewModel = hiltViewModel(),
    navigateToMyTabMain: () -> Unit
) {
    val challengeUiState by viewModel.challengeUiState.collectAsStateWithLifecycle()
    val isChallengeDeleteSuccess by viewModel.isChallengeDeleteSuccess.collectAsStateWithLifecycle()

    LaunchedEffect(isChallengeDeleteSuccess) {
        if (isChallengeDeleteSuccess == true) {
            navigateToMyTabMain()
        }
    }

    MyChallengeDetailScreen(
        uiState = challengeUiState,
        onDeleteButtonClick = viewModel::deleteChallenge,
        navigateToMyTabMain = navigateToMyTabMain
    )
}

@Composable
fun MyChallengeDetailScreen(
    uiState: MyChallengeDetailUiState,
    onDeleteButtonClick: () -> Unit,
    navigateToMyTabMain: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        ChallengeDeleteDialog(
            onDeleteButtonClick = {
                onDeleteButtonClick()
                showDeleteDialog = false
            },
            onDismissRequest = { showDeleteDialog = false }
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            MyChallengeDetailHeader(
                onBackButtonClick = navigateToMyTabMain,
                onShareButtonClick = {},
                onDeleteButtonClick = { showDeleteDialog = true }
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(background),
                contentPadding = PaddingValues(bottom = 72.dp)
            ) {
                when (uiState) {
                    MyChallengeDetailUiState.Loading -> {
                        item(key = "로딩", contentType = "Loading") {
                            Box(
                                modifier = Modifier.fillParentMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = gray200)
                            }
                        }
                    }

                    MyChallengeDetailUiState.Error -> {}
                    is MyChallengeDetailUiState.Success -> {
                        val uiModel = uiState.data
                        if (uiModel.missionType is MissionType.Photo) {
                            item(key = "퀘스트 수행 이미지", contentType = "Image") {
                                MissionHistoryDetailPhotoItem(imageId = uiModel.submitImageId)
                            }
                        }
                        item(key = "퀘스트 정보", contentType = "Info") {
                            MissionHistoryDetailInfoItem(
                                title = uiModel.title,
                                questType = uiModel.questType,
                                missionType = uiModel.missionType,
                                likeCount = uiModel.likeCount,
                                createdAt = uiModel.createdAt
                            )
                        }
                        item(key = "퀘스트 정보 여백", contentType = "Spacer") {
                            Spacer(Modifier.height(24.dp))
                        }
                        item(key = "퀘스트 상세 정보", contentType = "DetailInfo") {
                            Column(
                                modifier = Modifier.padding(horizontal = 20.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                if (uiModel.missionType != MissionType.Photo) {
                                    uiModel.quiz?.let { quiz ->
                                        MissionHistoryDetailQuizItem(
                                            missionType = uiModel.missionType,
                                            completedQuiz = quiz
                                        )
                                    }
                                }
                                MissionHistoryDetailPointItem(
                                    metroGainPoint = uiModel.metroGainPoint,
                                    commercialGainPoint = uiModel.commercialGainPoint,
                                    contributionGainPoint = uiModel.contributionGainPoint,
                                    isContributionDouble = uiModel.isContributionDouble
                                )
                                MissionHistoryDetailWriterItem(writerName = uiModel.writerName)
                                MissionHistoryDetailDateItem(createdAt = uiModel.createdAt)
                            }
                        }
                    }
                }
            }
        }
    }
}
