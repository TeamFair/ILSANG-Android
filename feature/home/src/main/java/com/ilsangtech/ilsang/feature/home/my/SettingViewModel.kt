package com.ilsangtech.ilsang.feature.home.my

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilsangtech.ilsang.core.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _logoutState = MutableStateFlow<Boolean?>(false)
    val logoutState = _logoutState.asStateFlow()

    fun logout() {
        viewModelScope.launch {
            userRepository.logout().onSuccess {
                _logoutState.update { true }
            }.onFailure {
                _logoutState.update { false }
            }
        }
    }
}