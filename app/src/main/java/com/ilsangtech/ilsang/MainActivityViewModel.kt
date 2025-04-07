package com.ilsangtech.ilsang

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _currentUserState = MutableStateFlow(Firebase.auth.currentUser)
    val currentUserState = _currentUserState.asStateFlow()

    init {
        if (currentUserState.value != null) {
            login(currentUserState.value)
        }
    }

    fun login(newUser: FirebaseUser?) {
        newUser?.getIdToken(false)?.addOnSuccessListener { result ->
            val idToken = result.token
            idToken?.let {
                viewModelScope.launch {
                    runCatching {
                        userRepository.login(
                            user = User(
                                accessToken = idToken,
                                email = newUser.email,
                            )
                        )
                    }.onSuccess {
                        _currentUserState.value = Firebase.auth.currentUser
                    }
                }
            }
        }

    }
}