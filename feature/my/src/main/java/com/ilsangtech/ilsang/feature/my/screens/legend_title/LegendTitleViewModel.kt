package com.ilsangtech.ilsang.feature.my.screens.legend_title

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.cachedIn
import androidx.paging.map
import com.ilsangtech.ilsang.core.domain.TitleRepository
import com.ilsangtech.ilsang.core.model.title.LegendTitle
import com.ilsangtech.ilsang.feature.my.navigation.LegendTitleRoute
import com.ilsangtech.ilsang.feature.my.screens.legend_title.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class LegendTitleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    titleRepository: TitleRepository
) : ViewModel() {
    private val titleId = savedStateHandle.toRoute<LegendTitleRoute>().titleId
    val titleName = savedStateHandle.toRoute<LegendTitleRoute>().titleName

    val legendTitlePagingList =
        titleRepository.getLegendTitleRankList(titleId).map { pagingData ->
            pagingData.map(LegendTitle::toUiModel)
        }.cachedIn(viewModelScope)
}