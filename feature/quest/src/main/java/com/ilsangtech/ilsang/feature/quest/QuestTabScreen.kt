package com.ilsangtech.ilsang.feature.quest

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ilsangtech.ilsang.core.model.Quest
import com.ilsangtech.ilsang.core.model.QuestType
import com.ilsangtech.ilsang.core.model.RepeatQuestPeriod
import com.ilsangtech.ilsang.core.ui.quest.QuestBottomSheet
import com.ilsangtech.ilsang.core.util.FileManager
import com.ilsangtech.ilsang.feature.quest.component.QuestCardWithFavorite
import com.ilsangtech.ilsang.feature.quest.component.QuestTabHeader
import com.ilsangtech.ilsang.feature.quest.component.SortTypeMenuContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestTabScreen(
    questTabViewModel: QuestTabViewModel = hiltViewModel(),
    navigateToSubmit: () -> Unit
) {
    val selectedQuestType by questTabViewModel.selectedQuestType.collectAsStateWithLifecycle()
    val selectedRepeatPeriod by questTabViewModel.selectedRepeatPeriod.collectAsStateWithLifecycle()
    val selectedSortType by questTabViewModel.selectedSortType.collectAsStateWithLifecycle()
    val selectedQuest by questTabViewModel.selectedQuest.collectAsStateWithLifecycle()
    val questTabUiState by questTabViewModel.questTabUiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val tempFile by questTabViewModel.capturedImageFile.collectAsStateWithLifecycle()
    val tempFileUri = remember(tempFile) { FileManager.getUriForFile(tempFile, context) }
    val imageCaptureLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                val imageUri = FileManager.getUriForFile(tempFile, context)
                questTabViewModel.setCapturedImageUri(imageUri)
                navigateToSubmit()
            }
        }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    QuestTabScreen(
        bottomSheetState = bottomSheetState,
        selectedQuestType = selectedQuestType,
        selectedRepeatPeriod = selectedRepeatPeriod,
        selectedSortType = selectedSortType,
        selectedQuest = selectedQuest,
        questTabUiState = questTabUiState,
        onSelectQuestType = questTabViewModel::selectQuestType,
        onSelectRepeatPeriod = questTabViewModel::selectRepeatPeriod,
        onSelectSortType = questTabViewModel::selectSortType,
        onQuestClick = questTabViewModel::selectQuest,
        onFavoriteClick = questTabViewModel::updateQuestFavoriteStatus,
        onDismissRequest = questTabViewModel::unselectQuest,
        onApproveButtonClick = {
            questTabViewModel.selectQuest(it)
            imageCaptureLauncher.launch(tempFileUri)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuestTabScreen(
    bottomSheetState: SheetState,
    selectedQuestType: QuestType,
    selectedRepeatPeriod: RepeatQuestPeriod,
    selectedSortType: SortType,
    selectedQuest: Quest?,
    questTabUiState: QuestTabUiState,
    onSelectQuestType: (QuestType) -> Unit,
    onSelectRepeatPeriod: (RepeatQuestPeriod) -> Unit,
    onSelectSortType: (SortType) -> Unit,
    onQuestClick: (Quest) -> Unit,
    onFavoriteClick: (Quest) -> Unit,
    onDismissRequest: () -> Unit,
    onApproveButtonClick: (Quest) -> Unit
) {
    if (selectedQuest != null) {
        QuestBottomSheet(
            bottomSheetState = bottomSheetState,
            quest = selectedQuest,
            onDismiss = onDismissRequest,
            onFavoriteClick = { onFavoriteClick(selectedQuest) },
            onApproveButtonClick = { onApproveButtonClick(selectedQuest) }
        )
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column {
            QuestTabHeader(
                selectedQuestType = selectedQuestType,
                onSelectQuestType = onSelectQuestType
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
                            QuestCardWithFavorite(
                                quest = quest,
                                onFavoriteClick = { onFavoriteClick(quest) },
                                onClick = { onQuestClick(quest) }
                            )
                        }
                        item { Spacer(Modifier.height(64.dp)) }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(backgroundColor = 0xFFF6F6F6)
@Composable
private fun QuestTabScreenPreview() {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    QuestTabScreen(
        bottomSheetState = bottomSheetState,
        selectedQuestType = QuestType.NORMAL,
        selectedRepeatPeriod = RepeatQuestPeriod.DAILY,
        selectedSortType = SortType.POPULAR,
        selectedQuest = null,
        questTabUiState = QuestTabUiState.Success(
            QuestTabUiData(emptyList())
        ),
        onSelectQuestType = {},
        onSelectRepeatPeriod = {},
        onSelectSortType = {},
        onQuestClick = {},
        onFavoriteClick = {},
        onDismissRequest = {},
        onApproveButtonClick = {}
    )
}