package com.ilsangtech.ilsang.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ilsangtech.ilsang.core.model.mission.MissionType
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.core.ui.mission.MissionHistoryDetailDateItem
import com.ilsangtech.ilsang.core.ui.mission.MissionHistoryDetailInfoItem
import com.ilsangtech.ilsang.core.ui.mission.MissionHistoryDetailPhotoItem
import com.ilsangtech.ilsang.core.ui.mission.MissionHistoryDetailPointItem
import com.ilsangtech.ilsang.core.ui.mission.MissionHistoryDetailWriterItem
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.gray200
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.feature.profile.model.ChallengeDetailUiModel
import com.ilsangtech.ilsang.feature.profile.model.ChallengeDetailUiState

@Composable
internal fun ChallengeScreen(
    viewModel: ChallengeViewModel = hiltViewModel(),
    popBackStack: () -> Unit
) {
    val challengeDetailUiState by viewModel.challengeDetailUiState.collectAsStateWithLifecycle()

    ChallengeScreen(
        uiState = challengeDetailUiState,
        onBackButtonClick = popBackStack
    )
}

@Composable
private fun ChallengeScreen(
    uiState: ChallengeDetailUiState,
    onBackButtonClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            when (uiState) {
                ChallengeDetailUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = gray200)
                    }
                }

                ChallengeDetailUiState.Error -> {}
                is ChallengeDetailUiState.Success -> {
                    val uiModel = uiState.data
                    ChallengeScreenHeader(
                        missionType = uiModel.missionType,
                        onShareButtonClick = {},
                        onBackButtonClick = onBackButtonClick
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(background),
                        contentPadding = PaddingValues(bottom = 72.dp)
                    ) {
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
                                MissionHistoryDetailPointItem(
                                    metroGainPoint = uiModel.metroGainPoint,
                                    commercialGainPoint = uiModel.commercialGainPoint,
                                    contributionGainPoint = uiModel.contributionGainPoint
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

@Composable
private fun ChallengeScreenHeader(
    missionType: MissionType,
    onShareButtonClick: () -> Unit,
    onBackButtonClick: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(
                    horizontal = 12.dp,
                    vertical = 7.dp
                )
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable(
                        onClick = onBackButtonClick,
                        indication = null,
                        interactionSource = null
                    ),
                painter = painterResource(R.drawable.icon_back),
                contentDescription = null
            )
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "챌린지 정보",
                style = TextStyle(
                    fontFamily = pretendardFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = gray500
                )
            )
            if (missionType == MissionType.Photo) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable(
                            onClick = { isExpanded = true },
                            indication = null,
                            interactionSource = null
                        ),
                    painter = painterResource(R.drawable.icon_more_vertical),
                    tint = gray500,
                    contentDescription = null
                )
            }
        }
        ChallengeDropDownMenu(
            modifier = Modifier.align(Alignment.BottomEnd),
            isExpanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            onShareButtonClick = {
                isExpanded = false
                onShareButtonClick()
            }
        )
    }
}

@Composable
private fun ChallengeDropDownMenu(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    onDismissRequest: () -> Unit,
    onShareButtonClick: () -> Unit
) {
    DropdownMenu(
        modifier = modifier.width(180.dp),
        expanded = isExpanded,
        offset = DpOffset(x = (-20).dp, y = 10.dp),
        shape = RoundedCornerShape(8.dp),
        onDismissRequest = onDismissRequest,
        containerColor = Color.White
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = onShareButtonClick,
                    interactionSource = null,
                    indication = null
                )
                .padding(horizontal = 12.dp, vertical = 2.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                style = TextStyle(
                    fontFamily = pretendardFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                    lineHeight = 1.3.em,
                    color = gray500
                ),
                text = "공유하기"
            )
            Icon(
                modifier = Modifier.align(Alignment.CenterEnd),
                painter = painterResource(R.drawable.share),
                tint = gray500,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun ChallengeScreenSuccessPreview() {
    val uiModel = ChallengeDetailUiModel(
        missionHistoryId = 1,
        title = "맛있는 음식 사진 찍기",
        submitImageId = "image_id_123",
        viewCount = 120,
        likeCount = 45,
        createdAt = "2023.10.27",
        commercialAreaCode = "12345",
        missionType = MissionType.Photo,
        writerName = "일상챌린저",
        commercialGainPoint = 50,
        metroGainPoint = 10,
        contributionGainPoint = 5,
        quiz = null,
        questType = QuestType.Normal
    )
    ChallengeScreen(
        uiState = ChallengeDetailUiState.Success(uiModel),
        onBackButtonClick = {}
    )
}