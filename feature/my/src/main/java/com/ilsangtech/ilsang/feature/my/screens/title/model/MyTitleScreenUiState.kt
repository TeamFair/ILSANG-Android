package com.ilsangtech.ilsang.feature.my.screens.title.model

sealed interface MyTitleScreenUiState {
    data object Loading : MyTitleScreenUiState
    data class Success(val titleList: List<MyTitleUiModel>) : MyTitleScreenUiState
    data object Error : MyTitleScreenUiState
}