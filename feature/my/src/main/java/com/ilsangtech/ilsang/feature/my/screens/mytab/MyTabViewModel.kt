package com.ilsangtech.ilsang.feature.my.screens.mytab

import androidx.lifecycle.ViewModel
import com.ilsangtech.ilsang.core.ui.season.model.SeasonUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MyTabViewModel @Inject constructor(
) : ViewModel() {
    private val _selectedSeason = MutableStateFlow<SeasonUiModel>(SeasonUiModel.Total)
    val selectedSeason = _selectedSeason.asStateFlow()

}