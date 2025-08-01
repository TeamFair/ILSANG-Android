package com.ilsangtech.ilsang.feature.my

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilsangtech.ilsang.core.domain.TitleRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.model.Title
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
    private val userRepository: UserRepository,
    private val titleRepository: TitleRepository
) : ViewModel() {
    val myTitleUiState = flow {
        emit(titleRepository.getTitleList())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _selectedTitle = MutableStateFlow<Title?>(null)
    val selectedTitle = _selectedTitle.asStateFlow()

    fun selectTitle(title: Title) {
        _selectedTitle.update {
            if (it != title) title else null
        }
    }

    fun updateUserTitle() {
        viewModelScope.launch {
            selectedTitle.value.let { title ->
                title?.historyId?.let { titleHistoryId ->
                    userRepository.updateUserTitle(titleHistoryId)
                }
            }
        }
    }
}