package com.ilsangtech.ilsang.feature.my.screens.profile_edit

import android.content.Context
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.util.FileManager
import com.ilsangtech.ilsang.feature.my.navigation.MyProfileEditRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    @ApplicationContext private val context: Context,
    private val userRepository: UserRepository
) : ViewModel() {
    val originNickname = savedStateHandle.toRoute<MyProfileEditRoute>().nickname
    val profileImageId = savedStateHandle.toRoute<MyProfileEditRoute>().profileImageId

    private val _editNickname = MutableStateFlow(originNickname)
    val editNickname = _editNickname.asStateFlow()

    private val _nicknameEditErrorMessage = MutableStateFlow<String?>(null)
    val nicknameEditErrorMessage = _nicknameEditErrorMessage.asStateFlow()

    private val _isUserProfileEditSuccess = MutableStateFlow<Boolean?>(null)
    val isUserProfileEditSuccess = _isUserProfileEditSuccess.asStateFlow()

    private fun isValidNickname(name: String): Boolean {
        val pattern = ".*[가-힣a-zA-Z0-9]+.*".toRegex()
        return pattern.matches(name) && name.length in 2..12
    }

    fun changeNickname(nickname: String) {
        _editNickname.value = nickname
        if (isValidNickname(nickname)) {
            _nicknameEditErrorMessage.value = null
        } else {
            _nicknameEditErrorMessage.value = "한글+영어+숫자 포함 2 ~ 12자 이하로 닉네임을 입력해주세요."
        }
    }

    fun updateUserProfile(uri: Uri?) {
        viewModelScope.launch {
            runCatching {
                if (uri != null) {
                    val fileData = FileManager.getBytesFromUri(context, uri)
                    userRepository.updateUserImage(fileData)
                }
                if (editNickname.value != originNickname) {
                    userRepository.updateUserNickname(editNickname.value)
                }
            }.onSuccess {
                userRepository.updateMyInfo()
                _isUserProfileEditSuccess.update { true }
            }.onFailure {
                _isUserProfileEditSuccess.update { false }
            }
        }
    }

    fun deleteUserProfileImage() {
        viewModelScope.launch {
            userRepository.deleteUserImage()
                .onSuccess {
                    userRepository.updateMyInfo()
                    _isUserProfileEditSuccess.update { true }
                }.onFailure {
                    _isUserProfileEditSuccess.update { false }
                }
        }
    }

    fun resetUserProfileEditSuccess() {
        _editNickname.update { originNickname }
        _nicknameEditErrorMessage.update { null }
        _isUserProfileEditSuccess.update { null }
    }

}