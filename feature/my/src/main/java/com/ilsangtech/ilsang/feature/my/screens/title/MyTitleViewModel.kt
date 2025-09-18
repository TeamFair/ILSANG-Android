package com.ilsangtech.ilsang.feature.my.screens.title

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ilsangtech.ilsang.core.domain.TitleRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.model.title.TitleGrade
import com.ilsangtech.ilsang.core.model.title.UserTitle
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

    private val _currentTitle = MutableStateFlow<MyTitleUiModel?>(null)
    val currentTitle = _currentTitle.asStateFlow()
    private val _selectedTitleGrade = MutableStateFlow<TitleGrade>(TitleGrade.Standard)
    val selectedTitleGrade = _selectedTitleGrade.asStateFlow()

    private val _selectedTitle = MutableStateFlow<MyTitleUiModel?>(null)
    val selectedTitle = _selectedTitle.asStateFlow()

    private val _isTitleUpdated = MutableStateFlow(false)
    val isTitleUpdated = _isTitleUpdated.asStateFlow()

    private val _unreadTitleList = MutableStateFlow<List<UserTitle>>(emptyList())
    val unreadTitleList = _unreadTitleList.asStateFlow()

    val showUpdateButton = combine(
        selectedTitle,
        currentTitle
    ) { selectedTitle, currentTitle ->
        selectedTitle != currentTitle
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    val myTitleUiState = combine(
        flow { emit(titleRepository.getTitleList()) },
        flow { emit(titleRepository.getUserTitleList()) }
    ) { titleList, userTitleList ->
        MyTitleScreenUiState.Success(
            titleList = titleList.map { title ->
                MyTitleUiModel(
                    titleId = title.id,
                    titleHistoryId = userTitleList.find { it.title == title.title }?.titleHistoryId,
                    title = title.title,
                    condition = title.condition
                )
            }
        )
    }.onEach { state ->
        originTitleHistoryId?.let {
            state.titleList.find { it.titleHistoryId == originTitleHistoryId }
                ?.let { title ->
                    _selectedTitle.update { title }
                    _currentTitle.update { title }
                }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MyTitleScreenUiState.Loading
    )

    init {
        viewModelScope.launch {
            _unreadTitleList.update { titleRepository.getUnreadTitleList() }
        }
    }

    fun updateTitleGrade(titleGrade: TitleGrade) {
        _selectedTitleGrade.update { titleGrade }
    }

    fun selectTitle(title: MyTitleUiModel) {
        _selectedTitle.update { if (it != title) title else null }
    }

    fun updateUserTitle() {
        viewModelScope.launch {
            try {
                userRepository.updateUserTitle(
                    titleHistoryId = selectedTitle.value?.titleHistoryId
                )
            } catch (_: Exception) {
            } finally {
                _isTitleUpdated.update { true }
                _currentTitle.update { selectedTitle.value }
            }
        }
    }

    fun readTitle(titleHistoryId: Int) {
        viewModelScope.launch {
            titleRepository.readTitle(titleHistoryId)
            _unreadTitleList.update {
                it.filter { userTitle ->
                    userTitle.titleHistoryId != titleHistoryId
                }
            }
        }
    }

    fun clearTitleUpdateStatus() {
        _isTitleUpdated.update { false }
    }
}