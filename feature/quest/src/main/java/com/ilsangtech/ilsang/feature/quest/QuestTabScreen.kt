package com.ilsangtech.ilsang.feature.quest

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ilsangtech.ilsang.core.model.quest.QuestDetail
import com.ilsangtech.ilsang.core.model.quest.TypedQuest
import com.ilsangtech.ilsang.core.ui.quest.CompletedQuestCard
import com.ilsangtech.ilsang.core.ui.quest.QuestCardWithFavorite
import com.ilsangtech.ilsang.core.ui.quest.bottomsheet.QuestBottomSheet
import com.ilsangtech.ilsang.core.ui.zone.MyZoneSelector
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.feature.quest.component.QuestTabHeader
import com.ilsangtech.ilsang.feature.quest.component.SortTypeMenuContent
import com.ilsangtech.ilsang.feature.quest.model.QuestTabUiModel
import com.ilsangtech.ilsang.feature.quest.model.RepeatQuestTypeUiModel
import com.ilsangtech.ilsang.feature.quest.model.SortTypeUiModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestTabScreen(
    questTabViewModel: QuestTabViewModel = hiltViewModel(),
    navigateToSubmit: (Int, Int) -> Unit,
    navigateToMyZone: () -> Unit,
    onMissionImageClick: (Int) -> Unit
) {
    val selectedQuestType by questTabViewModel.selectedQuestTab.collectAsStateWithLifecycle()
    val selectedRepeatType by questTabViewModel.selectedRepeatType.collectAsStateWithLifecycle()
    val selectedSortType by questTabViewModel.selectedSortType.collectAsStateWithLifecycle()
    val selectedQuest by questTabViewModel.selectedQuest.collectAsStateWithLifecycle()
    val typedQuests = questTabViewModel.typedQuests.collectAsLazyPagingItems()
    val areaName by questTabViewModel.areaName.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(Unit) {
        questTabViewModel.questDetailRefreshTrigger.collect {
            typedQuests.refresh()
        }
    }

    QuestTabScreen(
        bottomSheetState = bottomSheetState,
        selectedQuestTab = selectedQuestType,
        selectedRepeatType = selectedRepeatType,
        selectedSortType = selectedSortType,
        selectedQuest = selectedQuest,
        typedQuests = typedQuests,
        areaName = areaName,
        onSelectQuestTab = questTabViewModel::selectQuestType,
        onSelectRepeatType = questTabViewModel::selectRepeatPeriod,
        onSelectSortType = questTabViewModel::selectSortType,
        onQuestClick = questTabViewModel::selectQuest,
        onFavoriteClick = questTabViewModel::updateQuestFavoriteStatus,
        onDismissRequest = questTabViewModel::unselectQuest,
        onMyZoneClick = navigateToMyZone,
        onMissionImageClick = { missionId ->
            missionId?.let {
                coroutineScope.launch {
                    bottomSheetState.hide()
                    questTabViewModel.unselectQuest()
                    onMissionImageClick(it)
                }
            }
        },
        onApproveButtonClick = { questId, missionId ->
            coroutineScope.launch {
                bottomSheetState.hide()
                questTabViewModel.unselectQuest()
                navigateToSubmit(questId, missionId)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuestTabScreen(
    bottomSheetState: SheetState,
    selectedQuestTab: QuestTabUiModel,
    selectedRepeatType: RepeatQuestTypeUiModel?,
    selectedSortType: SortTypeUiModel,
    selectedQuest: QuestDetail?,
    areaName: String?,
    typedQuests: LazyPagingItems<TypedQuest>,
    onSelectQuestTab: (QuestTabUiModel) -> Unit,
    onSelectRepeatType: (RepeatQuestTypeUiModel) -> Unit,
    onSelectSortType: (SortTypeUiModel) -> Unit,
    onQuestClick: (Int) -> Unit,
    onFavoriteClick: (Int, Boolean) -> Unit,
    onApproveButtonClick: (Int, Int) -> Unit,
    onMyZoneClick: () -> Unit,
    onMissionImageClick: (Int?) -> Unit,
    onDismissRequest: () -> Unit
) {
    if (selectedQuest != null) {
        QuestBottomSheet(
            quest = selectedQuest,
            bottomSheetState = bottomSheetState,
            onDismiss = onDismissRequest,
            onMissionImageClick = {
                onMissionImageClick(selectedQuest.missions.firstOrNull()?.id)
            },
            onFavoriteClick = { onFavoriteClick(selectedQuest.id, selectedQuest.favoriteYn) },
            onApproveButtonClick = {
                val questId = selectedQuest.id
                val missionId = selectedQuest.missions.firstOrNull()?.id
                missionId?.let { missionId ->
                    onApproveButtonClick(questId, missionId)
                }
            }
        )
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        color = background
    ) {
        Column {
            QuestTabHeader(
                selectedQuestTab = selectedQuestTab,
                onQuestTabSelected = onSelectQuestTab
            )
            MyZoneSelector(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .padding(vertical = 16.dp),
                myCommercialAreaName = areaName.orEmpty(),
                onMyZoneClick = onMyZoneClick
            )
            Box(modifier = Modifier.fillMaxWidth()) {
                SortTypeMenuContent(
                    modifier = Modifier.zIndex(1f),
                    questTab = selectedQuestTab,
                    selectedRepeatType = selectedRepeatType,
                    selectedSortType = selectedSortType,
                    onSelectRepeatType = onSelectRepeatType,
                    onSelectSortType = onSelectSortType
                )
                LazyColumn(
                    modifier = Modifier
                        .offset(y = 64.dp)
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(typedQuests.itemCount) { index ->
                        val quest = typedQuests[index]
                        quest?.let {
                            if (selectedQuestTab == QuestTabUiModel.COMPLETED) {
                                CompletedQuestCard(
                                    quest = quest,
                                    onClick = { onQuestClick(quest.questId) }
                                )
                            } else {
                                QuestCardWithFavorite(
                                    quest = quest,
                                    onFavoriteClick = {
                                        onFavoriteClick(
                                            quest.questId,
                                            quest.favoriteYn
                                        )
                                    },
                                    onClick = { onQuestClick(quest.questId) }
                                )
                            }
                        }
                    }
                    item { Spacer(Modifier.height(64.dp)) }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun QuestTabScreenPreview() {
    val bottomSheetState = rememberModalBottomSheetState()
    val typedQuestsData = List(10) { index ->
        TypedQuest(
            questId = index,
            expireDate = "2023-12-31",
            favoriteYn = index % 2 == 0,
            imageId = "image_id_$index",
            mainImageId = "main_image_id_$index",
            rewards = emptyList(),
            title = "Quest Title $index",
            writerName = "Writer $index",
            questType = com.ilsangtech.ilsang.core.model.NewQuestType.Normal
        )
    }
    val typedQuests = flowOf(PagingData.from(typedQuestsData)).collectAsLazyPagingItems()

    QuestTabScreen(
        bottomSheetState = bottomSheetState,
        selectedQuestTab = QuestTabUiModel.NORMAL,
        selectedRepeatType = null,
        selectedSortType = SortTypeUiModel.Popular,
        selectedQuest = null,
        typedQuests = typedQuests,
        areaName = "서현",
        onSelectQuestTab = {},
        onSelectRepeatType = {},
        onSelectSortType = {},
        onQuestClick = {},
        onFavoriteClick = { _, _ -> },
        onApproveButtonClick = { _, _ -> },
        onMyZoneClick = {},
        onMissionImageClick = {},
        onDismissRequest = {}
    )
}