package com.ilsangtech.ilsang.feature.my.screens.favorite_quest

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ilsangtech.ilsang.core.ui.quest.QuestCardWithFavorite
import com.ilsangtech.ilsang.core.ui.quest.model.TypedQuestUiModel
import com.ilsangtech.ilsang.core.ui.zone.MyZoneSelector
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.feature.my.screens.favorite_quest.component.MyFavoriteQuestEmptyContent
import com.ilsangtech.ilsang.feature.my.screens.favorite_quest.component.MyFavoriteQuestHeader

@Composable

internal fun MyFavoriteQuestScreen(
    viewModel: MyFavoriteQuestViewModel = hiltViewModel(),
    onMyZoneClick: () -> Unit,
    onQuestNavButtonClick: () -> Unit,
    onBackButtonClick: () -> Unit
) {
    val favoriteQuestList = viewModel.favoriteQuestList.collectAsLazyPagingItems()
    val commercialAreaName by viewModel.commercialAreaName.collectAsStateWithLifecycle()

    MyFavoriteQuestScreen(
        commercialAreaName = commercialAreaName,
        favoriteQuestList = favoriteQuestList,
        onMyZoneClick = onMyZoneClick,
        onFavoriteClick = viewModel::updateFavoriteStatus,
        onQuestNavButtonClick = onQuestNavButtonClick,
        onBackButtonClick = onBackButtonClick
    )
}

@Composable
private fun MyFavoriteQuestScreen(
    commercialAreaName: String?,
    favoriteQuestList: LazyPagingItems<TypedQuestUiModel>,
    onMyZoneClick: () -> Unit,
    onFavoriteClick: (TypedQuestUiModel) -> Unit,
    onQuestNavButtonClick: () -> Unit,
    onBackButtonClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = background
    ) {
        Column {
            MyFavoriteQuestHeader(onBackButtonClick = onBackButtonClick)
            MyZoneSelector(
                modifier = Modifier.padding(top = 16.dp, start = 20.dp),
                myCommercialAreaName = commercialAreaName.orEmpty(),
                onMyZoneClick = onMyZoneClick
            )
            if (favoriteQuestList.loadState.refresh is LoadState.NotLoading
                && favoriteQuestList.itemCount == 0
            ) {
                MyFavoriteQuestEmptyContent(
                    modifier = Modifier.weight(1f),
                    onQuestNavButtonClick = onQuestNavButtonClick
                )
            }
            LazyColumn(
                contentPadding = PaddingValues(
                    top = 24.dp, bottom = 72.dp,
                    start = 20.dp, end = 20.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(favoriteQuestList.itemCount) {
                    val item = favoriteQuestList[it]
                    item?.let {
                        QuestCardWithFavorite(
                            quest = item,
                            onClick = {},
                            onFavoriteClick = { onFavoriteClick(item) }
                        )
                    }
                }
            }
        }
    }
}