package com.ilsangtech.ilsang.feature.submit

import android.content.Context
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ilsangtech.ilsang.core.domain.ImageRepository
import com.ilsangtech.ilsang.core.domain.MissionRepository
import com.ilsangtech.ilsang.core.util.FileManager
import com.ilsangtech.ilsang.feature.submit.navigation.ImageSubmitRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageSubmitViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    @ApplicationContext private val context: Context,
    private val imageRepository: ImageRepository,
    private val missionRepository: MissionRepository,
) : ViewModel() {
    private val missionId = savedStateHandle.toRoute<ImageSubmitRoute>().missionId
    private val _capturedImageUri = MutableStateFlow<Uri?>(null)
    val capturedImageFile = MutableStateFlow(FileManager.createCacheFile(context)).asStateFlow()

    private val _submitUiState = MutableStateFlow<SubmitUiState>(SubmitUiState.NotSubmitted)
    val submitUiState = _submitUiState.asStateFlow()

    fun submitApproveImage() {
        viewModelScope.launch {
            _submitUiState.update { SubmitUiState.Loading }
            imageRepository.uploadMissionImage(
                imageBytes = FileManager.getBytesFromFile(context, capturedImageFile.value),
            ).onSuccess { imageId ->
                missionRepository.submitImageMission(
                    missionId = missionId,
                    imageId = imageId
                ).onSuccess {
                    _submitUiState.update { SubmitUiState.Success(emptyList()) }
                }.onFailure {
                    _submitUiState.update { SubmitUiState.Error }
                }
            }.onFailure {
                _submitUiState.update { SubmitUiState.Error }
            }
        }
    }

    fun completeSubmit() {
        _submitUiState.update { SubmitUiState.NotSubmitted }
        _capturedImageUri.update { null }
    }

    override fun onCleared() {
        capturedImageFile.value.delete()
        super.onCleared()
    }
}