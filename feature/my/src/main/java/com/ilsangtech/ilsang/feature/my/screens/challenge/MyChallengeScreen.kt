package com.ilsangtech.ilsang.feature.my.screens.challenge

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ilsangtech.ilsang.core.ui.mission.UserMissionHistoryItem
import com.ilsangtech.ilsang.core.ui.mission.model.MissionSortTypes
import com.ilsangtech.ilsang.core.ui.mission.model.MissionTypes
import com.ilsangtech.ilsang.core.ui.mission.model.UserMissionHistoryUiModel
import com.ilsangtech.ilsang.designsystem.component.BorderedDropDownMenu
import com.ilsangtech.ilsang.designsystem.component.IlsangTabRow
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.feature.my.screens.challenge.component.MyChallengeEmptyContent
import com.ilsangtech.ilsang.feature.my.screens.challenge.component.MyChallengeHeader


@Composable
internal fun MyChallengeScreen(
    viewModel: MyChallengeViewModel = hiltViewModel(),
    onMissionHistoryClick: (UserMissionHistoryUiModel) -> Unit,
    onQuestNavButtonClick: () -> Unit,
    onBackButtonClick: () -> Unit
) {
    val myMissionHistoryItems = viewModel.myMissionHistories.collectAsLazyPagingItems()
    var selectedMissionType by remember { mutableStateOf(MissionTypes.IMAGE) }
    var selectedSortType by remember { mutableStateOf(MissionSortTypes.Latest) }

    LaunchedEffect(Unit) {
        myMissionHistoryItems.refresh()
    }

    MyChallengeScreen(
        selectedMissionType = selectedMissionType,
        selectedSortType = selectedSortType,
        onMissionTypeSelected = { selectedMissionType = it },
        onSortTypeSelected = { selectedSortType = it },
        myMissionHistoryItems = myMissionHistoryItems,
        onMissionHistoryClick = onMissionHistoryClick,
        onQuestNavButtonClick = onQuestNavButtonClick,
        onBackButtonClick = onBackButtonClick
    )
}

@Composable
private fun MyChallengeScreen(
    selectedMissionType: MissionTypes,
    selectedSortType: MissionSortTypes,
    onMissionTypeSelected: (MissionTypes) -> Unit,
    onSortTypeSelected: (MissionSortTypes) -> Unit,
    myMissionHistoryItems: LazyPagingItems<UserMissionHistoryUiModel>,
    onMissionHistoryClick: (UserMissionHistoryUiModel) -> Unit,
    onQuestNavButtonClick: () -> Unit,
    onBackButtonClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = background
    ) {
        Column {
            MyChallengeHeader(onBackButtonClick = onBackButtonClick)
            IlsangTabRow(
                tabList = MissionTypes.entries,
                selectedTab = selectedMissionType,
                onTabSelected = onMissionTypeSelected
            )
            if (myMissionHistoryItems.loadState.refresh is LoadState.NotLoading
                && myMissionHistoryItems.itemCount == 0
            ) {
                MyChallengeEmptyContent(
                    modifier = Modifier.weight(1f),
                    onQuestNavButtonClick = onQuestNavButtonClick
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 72.dp)
                ) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp, bottom = 24.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "수행한 챌린지",
                                style = TextStyle(
                                    fontFamily = pretendardFontFamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp,
                                    lineHeight = 16.sp,
                                    color = gray400
                                )
                            )
                            BorderedDropDownMenu(
                                modifier = Modifier,
                                list = MissionSortTypes.entries,
                                selectedItem = selectedSortType,
                                onItemSelected = onSortTypeSelected
                            )
                        }
                    }

                    items(myMissionHistoryItems.itemCount) { index ->
                        val item = myMissionHistoryItems[index]
                        item?.let {
                            UserMissionHistoryItem(
                                modifier = Modifier.padding(
                                    bottom =
                                        if (myMissionHistoryItems.itemCount - 1 != index) 9.dp else 0.dp
                                ),
                                userMissionHistory = item,
                                onClick = { onMissionHistoryClick(item) }
                            )
                        }
                    }
                }
            }
        }
    }
}