package com.ilsangtech.ilsang

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilsangtech.ilsang.core.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _isLoggedIn = MutableStateFlow<Boolean?>(null)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    val shouldShowOnBoarding = userRepository.shouldShowOnBoarding.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    init {
        viewModelScope.launch {
            try {
                userRepository.updateMyInfo()
                _isLoggedIn.update { true }
            } catch (_: Exception) {
                _isLoggedIn.update { false }
            }
        }
    }

    fun login(idToken: String) {
        viewModelScope.launch {
            runCatching {
                userRepository.login(idToken)
            }.onSuccess {
                _isLoggedIn.update { true }
            }.onFailure {
                _isLoggedIn.update { false }
            }
        }
    }

    fun completeOnBoarding() {
        viewModelScope.launch {
            userRepository.completeOnBoarding()
        }
    }
}