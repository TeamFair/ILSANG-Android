package com.ilsangtech.ilsang.feature.home.quest

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ilsangtech.ilsang.core.model.Quest
import com.ilsangtech.ilsang.core.model.QuestType
import com.ilsangtech.ilsang.core.model.RepeatQuestPeriod
import com.ilsangtech.ilsang.core.model.RewardType
import com.ilsangtech.ilsang.feature.home.HomeViewModel
import com.ilsangtech.ilsang.feature.home.home.LargeRewardQuestBadge
import com.ilsangtech.ilsang.feature.home.util.FileManager

@Composable
fun QuestTabScreen(
    homeViewModel: HomeViewModel,
    navigateToSubmit: () -> Unit
) {
    val selectedQuestType by homeViewModel.selectedQuestType.collectAsStateWithLifecycle()
    val selectedRewardType by homeViewModel.selectedRewardType.collectAsStateWithLifecycle()
    val selectedRepeatPeriod by homeViewModel.selectedRepeatPeriod.collectAsStateWithLifecycle()
    val selectedSortType by homeViewModel.selectedSortType.collectAsStateWithLifecycle()
    val questTabUiState by homeViewModel.questTabUiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val tempFile by homeViewModel.capturedImageFile.collectAsStateWithLifecycle()
    val tempFileUri = remember(tempFile) { FileManager.getUriForFile(tempFile, context) }
    val imageCaptureLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                val imageUri = FileManager.getUriForFile(tempFile, context)
                homeViewModel.setCapturedImageUri(imageUri)
                navigateToSubmit()
            }
        }

    QuestTabScreen(
        selectedQuestType = selectedQuestType,
        selectedRewardType = selectedRewardType,
        selectedRepeatPeriod = selectedRepeatPeriod,
        selectedSortType = selectedSortType,
        questTabUiState = questTabUiState,
        onSelectQuestType = homeViewModel::selectQuestType,
        onSelectRewardType = homeViewModel::selectRewardType,
        onSelectRepeatPeriod = homeViewModel::selectRepeatPeriod,
        onSelectSortType = homeViewModel::selectSortType,
        onApproveButtonClick = {
            homeViewModel.selectQuest(it)
            imageCaptureLauncher.launch(tempFileUri)
        }
    )
}

@Composable
fun QuestTabScreen(
    selectedQuestType: QuestType,
    selectedRewardType: RewardType,
    selectedRepeatPeriod: RepeatQuestPeriod,
    selectedSortType: String,
    questTabUiState: QuestTabUiState,
    onSelectQuestType: (QuestType) -> Unit,
    onSelectRewardType: (RewardType) -> Unit,
    onSelectRepeatPeriod: (RepeatQuestPeriod) -> Unit,
    onSelectSortType: (String) -> Unit,
    onApproveButtonClick: (Quest) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column {
            QuestTapHeader(
                selectedQuestType = selectedQuestType,
                selectedRewardType = selectedRewardType,
                onSelectQuestType = onSelectQuestType,
                onSelectRewardType = onSelectRewardType
            )
            Box(modifier = Modifier.fillMaxWidth()) {
                SortTypeMenuContent(
                    modifier = Modifier.zIndex(1f),
                    questType = selectedQuestType,
                    selectedRepeatPeriod = selectedRepeatPeriod,
                    selectedSortType = selectedSortType,
                    onSelectRepeatPeriod = onSelectRepeatPeriod,
                    onSelectSortType = onSelectSortType
                )
                LazyColumn(
                    modifier = Modifier
                        .offset(y = 64.dp)
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (questTabUiState is QuestTabUiState.Success) {
                        items(questTabUiState.data.questList) { quest ->
                            DefaultQuestCard(
                                quest = quest,
                                badge = { modifier ->
                                    if (selectedQuestType == QuestType.REPEAT) {
                                        QuestTypeBadge(
                                            modifier = modifier,
                                            repeatType = when (quest.target) {
                                                "DAILY" -> "일간"
                                                "WEEKLY" -> "주간"
                                                else -> "월간"
                                            }
                                        )
                                    } else {
                                        LargeRewardQuestBadge(
                                            modifier = modifier,
                                            xpSum = quest.rewardList.sumOf { it.quantity },
                                        )
                                    }
                                },
                                onApproveButtonClick = { onApproveButtonClick(quest) }
                            )
                        }
                        item {
                            Spacer(Modifier.height(64.dp))
                        }
                    }
                }
            }
        }
    }
}

@Preview(backgroundColor = 0xFFF6F6F6)
@Composable
fun QuestTabScreenPreview() {
    QuestTabScreen(
        selectedQuestType = QuestType.NORMAL,
        selectedRewardType = RewardType.STRENGTH,
        selectedRepeatPeriod = RepeatQuestPeriod.DAILY,
        selectedSortType = "최신순",
        questTabUiState = QuestTabUiState.Success(
            QuestTabUiData(emptyList())
        ),
        onSelectQuestType = {},
        onSelectRewardType = {},
        onSelectRepeatPeriod = {},
        onSelectSortType = {},
        onApproveButtonClick = {}
    )
}
