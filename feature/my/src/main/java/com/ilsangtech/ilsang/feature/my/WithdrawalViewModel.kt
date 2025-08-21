package com.ilsangtech.ilsang.feature.my

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilsangtech.ilsang.core.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WithdrawalViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _withdrawalState = MutableStateFlow<Boolean?>(null)
    val withdrawalState = _withdrawalState.asStateFlow()

    fun withdraw() {
        viewModelScope.launch {
            authRepository.withdraw().onSuccess {
                _withdrawalState.update { true }
            }.onFailure {
                _withdrawalState.update { false }
            }
        }
    }
}