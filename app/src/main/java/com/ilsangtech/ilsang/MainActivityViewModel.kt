package com.ilsangtech.ilsang

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
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
        Firebase.auth.addAuthStateListener { auth ->
            if (auth.currentUser != null) {
                login(auth.currentUser)
            } else {
                _isLoggedIn.update { false }
            }
        }
    }

    fun login(newUser: FirebaseUser?) {
        newUser?.getIdToken(false)?.addOnSuccessListener { result ->
            val idToken = result.token
            idToken?.let {
                viewModelScope.launch {
                    runCatching {
                        userRepository.login(
                            accessToken = idToken,
                            email = newUser.email!!,
                        )
                    }.onSuccess {
                        _isLoggedIn.value = true
                    }.onFailure {
                        _isLoggedIn.value = false
                    }
                }
            }
        }?.addOnFailureListener {
            _isLoggedIn.value = false
        }
    }

    fun completeOnBoarding() {
        viewModelScope.launch {
            userRepository.completeOnBoarding()
        }
    }
}