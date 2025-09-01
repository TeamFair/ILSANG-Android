package com.ilsangtech.ilsang.feature.my.screens.challenge

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.ilsangtech.ilsang.core.ui.mission.model.UserMissionHistoryUiModel
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
    MyChallengeScreen(
        myMissionHistoryItems = myMissionHistoryItems,
        onMissionHistoryClick = onMissionHistoryClick,
        onQuestNavButtonClick = onQuestNavButtonClick,
        onBackButtonClick = onBackButtonClick
    )
}

@Composable
private fun MyChallengeScreen(
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
            LazyColumn(
                contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 72.dp)
            ) {
                item {
                    Text(
                        modifier = Modifier.padding(top = 16.dp, bottom = 20.dp),
                        text = "수행한 챌린지",
                        style = TextStyle(
                            fontFamily = pretendardFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            lineHeight = 16.sp,
                            color = gray400
                        )
                    )
                }
                if (myMissionHistoryItems.loadState.refresh is LoadState.NotLoading
                    && myMissionHistoryItems.itemCount == 0
                ) {
                    item {
                        MyChallengeEmptyContent(
                            modifier = Modifier.weight(1f),
                            onQuestNavButtonClick = onQuestNavButtonClick
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