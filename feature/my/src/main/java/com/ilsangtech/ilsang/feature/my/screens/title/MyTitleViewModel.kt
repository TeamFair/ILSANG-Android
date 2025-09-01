package com.ilsangtech.ilsang.feature.my.screens.title

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ilsangtech.ilsang.core.domain.TitleRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.model.title.Title
import com.ilsangtech.ilsang.feature.my.navigation.MyTitleRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
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
    val previousTitleId = savedStateHandle.toRoute<MyTitleRoute>().titleId
    val myTitleUiState = flow {
        emit(titleRepository.getTitleList())
    }
//        .onEach { titles ->
//            previousTitleId?.let {
//                val previousTitle = titles.find { it.id == previousTitleId }
//                previousTitle?.let { selectTitle(it) }
//            }
//        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _selectedTitle = MutableStateFlow<Title?>(null)
    val selectedTitle = _selectedTitle.asStateFlow()

    private val _isTitleUpdated = MutableStateFlow(false)
    val isTitleUpdated = _isTitleUpdated.asStateFlow()

    fun selectTitle(title: Title) {
        _selectedTitle.update { if (it != title) title else null }
    }

    fun updateUserTitle() {
        viewModelScope.launch {
//            try {
//                userRepository.updateUserTitle(selectedTitle.value?.historyId.orEmpty())
//                    .onSuccess { userRepository.updateMyInfo() }
//            } catch (_: Exception) {
//            } finally {
//                _isTitleUpdated.update { true }
//            }
        }
    }
}