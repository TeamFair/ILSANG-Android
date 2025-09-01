package com.ilsangtech.ilsang.feature.my.screens.title

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ilsangtech.ilsang.core.domain.TitleRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.feature.my.navigation.MyTitleRoute
import com.ilsangtech.ilsang.feature.my.screens.title.model.MyTitleScreenUiState
import com.ilsangtech.ilsang.feature.my.screens.title.model.MyTitleUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyTitleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository,
    private val titleRepository: TitleRepository
) : ViewModel() {
    val originTitleHistoryId = savedStateHandle.toRoute<MyTitleRoute>().titleHistoryId

    private val _selectedTitle = MutableStateFlow<MyTitleUiModel?>(null)
    val selectedTitle = _selectedTitle.asStateFlow()

    private val _isTitleUpdated = MutableStateFlow(false)
    val isTitleUpdated = _isTitleUpdated.asStateFlow()

    val myTitleUiState = combine(
        flow { emit(titleRepository.getTitleList()) },
        flow { emit(titleRepository.getUserTitleList()) }
    ) { titleList, userTitleList ->
        MyTitleScreenUiState.Success(
            titleList = titleList.map { title ->
                MyTitleUiModel(
                    titleHistoryId = userTitleList.find { it.title == title.title }?.titleHistoryId,
                    title = title.title,
                    condition = title.condition
                )
            }
        )
    }.onEach { state ->
        state.titleList.find { it.titleHistoryId == originTitleHistoryId }
            ?.let { title -> _selectedTitle.update { title } }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MyTitleScreenUiState.Loading
    )

    fun selectTitle(title: MyTitleUiModel) {
        _selectedTitle.update { if (it != title) title else null }
    }

    fun updateUserTitle() {
        viewModelScope.launch {
            try {
                selectedTitle.value?.titleHistoryId?.let {
                    userRepository.updateUserTitle(it)
                }
            } catch (_: Exception) {
            } finally {
                _isTitleUpdated.update { true }
            }
        }
    }
}