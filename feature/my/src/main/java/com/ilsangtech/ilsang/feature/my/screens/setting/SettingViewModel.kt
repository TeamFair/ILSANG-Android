package com.ilsangtech.ilsang.feature.my.screens.setting

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
class SettingViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _logoutState = MutableStateFlow<Boolean?>(false)
    val logoutState = _logoutState.asStateFlow()

    fun logout() {
        viewModelScope.launch {
            authRepository.logout().onSuccess {
                _logoutState.update { true }
            }.onFailure {
                _logoutState.update { false }
            }
        }
    }
}