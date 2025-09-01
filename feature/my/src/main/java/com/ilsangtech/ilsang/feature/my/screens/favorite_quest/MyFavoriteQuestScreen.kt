package com.ilsangtech.ilsang.feature.my.screens.favorite_quest

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ilsangtech.ilsang.core.model.quest.TypedQuest

@Composable
internal fun MyFavoriteQuestScreen(
    viewModel: MyFavoriteQuestViewModel = hiltViewModel()
) {
    val favoriteQuestList = viewModel.favoriteQuestList.collectAsLazyPagingItems()
    MyFavoriteQuestScreen(favoriteQuestList)
}

@Composable
private fun MyFavoriteQuestScreen(
    favoriteQuestList: LazyPagingItems<TypedQuest>
) {
}