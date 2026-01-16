package com.ilsangtech.ilsang.feature.quest

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.core.ui.quest.QuestCardWithFavorite
import com.ilsangtech.ilsang.core.ui.quest.model.TypedQuestUiModel
import com.ilsangtech.ilsang.core.ui.zone.MyZoneSelector
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.feature.quest.component.EmptyQuestContent
import com.ilsangtech.ilsang.feature.quest.component.QuestTabHeader
import com.ilsangtech.ilsang.feature.quest.component.SortTypeMenuContent
import com.ilsangtech.ilsang.feature.quest.model.QuestTabUiModel
import com.ilsangtech.ilsang.feature.quest.model.RepeatQuestTypeUiModel
import com.ilsangtech.ilsang.feature.quest.model.SortTypeUiModel
import kotlinx.coroutines.flow.flowOf

@Composable
fun QuestTabScreen(
    questTabViewModel: QuestTabViewModel = hiltViewModel(),
    navigateToMyZone: () -> Unit,
    onQuestClick: (Int) -> Unit
) {
    val selectedQuestType by questTabViewModel.selectedQuestTab.collectAsStateWithLifecycle()
    val selectedRepeatType by questTabViewModel.selectedRepeatType.collectAsStateWithLifecycle()
    val selectedSortType by questTabViewModel.selectedSortType.collectAsStateWithLifecycle()
    val typedQuests = questTabViewModel.typedQuests.collectAsLazyPagingItems()
    val areaName by questTabViewModel.areaName.collectAsStateWithLifecycle()

    QuestTabScreen(
        selectedQuestTab = selectedQuestType,
        selectedRepeatType = selectedRepeatType,
        selectedSortType = selectedSortType,
        typedQuests = typedQuests,
        areaName = areaName,
        onSelectQuestTab = questTabViewModel::selectQuestType,
        onSelectRepeatType = questTabViewModel::selectRepeatPeriod,
        onSelectSortType = questTabViewModel::selectSortType,
        onQuestClick = onQuestClick,
        onFavoriteClick = questTabViewModel::updateQuestFavoriteStatus,
        onMyZoneClick = navigateToMyZone
    )
}

@Composable
private fun QuestTabScreen(
    selectedQuestTab: QuestTabUiModel,
    selectedRepeatType: RepeatQuestTypeUiModel?,
    selectedSortType: SortTypeUiModel,
    areaName: String?,
    typedQuests: LazyPagingItems<TypedQuestUiModel>,
    onSelectQuestTab: (QuestTabUiModel) -> Unit,
    onSelectRepeatType: (RepeatQuestTypeUiModel) -> Unit,
    onSelectSortType: (SortTypeUiModel) -> Unit,
    onQuestClick: (Int) -> Unit,
    onFavoriteClick: (Int, Boolean) -> Unit,
    onMyZoneClick: () -> Unit
) {
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

                if (typedQuests.loadState.refresh is LoadState.NotLoading) {
                    if (typedQuests.itemCount == 0) {
                        EmptyQuestContent(selectedQuestType = selectedQuestTab)
                    } else {
                        LazyColumn(
                            modifier = Modifier.offset(y = 64.dp),
                            contentPadding = PaddingValues(
                                start = 20.dp, end = 20.dp, bottom = 72.dp
                            ),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(typedQuests.itemCount) { index ->
                                val quest = typedQuests[index]
                                quest?.let {
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
                            item { Spacer(Modifier.height(64.dp)) }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun QuestTabScreenPreview() {
    val typedQuestsData = List(10) { index ->
        TypedQuestUiModel(
            questId = index,
            expireDate = "2023-12-31",
            favoriteYn = index % 2 == 0,
            imageId = "image_id_$index",
            mainImageId = "main_image_id_$index",
            rewards = emptyList(),
            title = "Quest Title $index",
            writerName = "Writer $index",
            questType = QuestType.Normal
        )
    }
    val typedQuests = flowOf(PagingData.from(typedQuestsData)).collectAsLazyPagingItems()

    QuestTabScreen(
        selectedQuestTab = QuestTabUiModel.NORMAL,
        selectedRepeatType = null,
        selectedSortType = SortTypeUiModel.Popular,
        typedQuests = typedQuests,
        areaName = "서현",
        onSelectQuestTab = {},
        onSelectRepeatType = {},
        onSelectSortType = {},
        onQuestClick = {},
        onMyZoneClick = {},
        onFavoriteClick = { _, _ -> }
    )
}