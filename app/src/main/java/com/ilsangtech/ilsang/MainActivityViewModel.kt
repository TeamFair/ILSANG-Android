package com.ilsangtech.ilsang

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilsangtech.ilsang.core.domain.AuthRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val loginTrigger = MutableSharedFlow<Unit>(replay = 1)
    private val _shouldShowIsZoneDialog = MutableStateFlow(false)
    val shouldShowIsZoneDialog = _shouldShowIsZoneDialog.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val isLoggedIn = loginTrigger.flatMapLatest {
        userRepository.getMyInfo()
            .onEach { myInfo ->
                if (myInfo.isCommercialAreaCode == null && myInfo.showIsZoneDialogAgain) {
                    _shouldShowIsZoneDialog.update { true }
                }
            }
            .map { true }
            .catch { emit(false) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val shouldShowOnBoarding = userRepository.shouldShowOnBoarding.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    init {
        viewModelScope.launch {
            loginTrigger.emit(Unit)
        }
    }

    fun login(idToken: String) {
        viewModelScope.launch {
            authRepository.login(idToken).onSuccess {
                loginTrigger.emit(Unit)
            }
        }
    }

    fun completeOnBoarding() {
        viewModelScope.launch {
            userRepository.completeOnBoarding()
        }
    }

    fun shownIsZoneDialog(showAgain: Boolean) {
        viewModelScope.launch {
            if (!showAgain) {
                userRepository.updateShowIsZoneDialogAgain(false)
            }
            _shouldShowIsZoneDialog.update { false }
        }
    }
}