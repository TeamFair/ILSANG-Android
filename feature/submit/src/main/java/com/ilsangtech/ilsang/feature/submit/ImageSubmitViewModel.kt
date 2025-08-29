package com.ilsangtech.ilsang.feature.submit

import android.content.Context
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ilsangtech.ilsang.core.domain.ImageRepository
import com.ilsangtech.ilsang.core.domain.MissionRepository
import com.ilsangtech.ilsang.core.domain.QuestRepository
import com.ilsangtech.ilsang.core.util.FileManager
import com.ilsangtech.ilsang.feature.submit.model.SubmitResultUiState
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
    private val questRepository: QuestRepository
) : ViewModel() {
    private val questId = savedStateHandle.toRoute<ImageSubmitRoute>().questId
    private val missionId = savedStateHandle.toRoute<ImageSubmitRoute>().missionId
    private val _capturedImageUri = MutableStateFlow<Uri?>(null)
    val capturedImageFile = MutableStateFlow(FileManager.createCacheFile(context)).asStateFlow()

    private val _submitUiState =
        MutableStateFlow<SubmitResultUiState>(SubmitResultUiState.NotSubmitted)
    val submitUiState = _submitUiState.asStateFlow()

    fun submitApproveImage() {
        viewModelScope.launch {
            _submitUiState.update { SubmitResultUiState.Loading }
            imageRepository.uploadMissionImage(
                imageBytes = FileManager.getBytesFromFile(context, capturedImageFile.value),
            ).onSuccess { imageId ->
                missionRepository.submitImageMission(
                    missionId = missionId,
                    imageId = imageId
                ).onSuccess {
                    questRepository.getQuestDetail(questId).collect { quest ->
                        _submitUiState.update { SubmitResultUiState.Success(quest.rewards) }
                    }
                }.onFailure {
                    _submitUiState.update { SubmitResultUiState.Error }
                }
            }.onFailure {
                _submitUiState.update { SubmitResultUiState.Error }
            }
        }
    }

    fun completeSubmit() {
        _submitUiState.update { SubmitResultUiState.NotSubmitted }
        _capturedImageUri.update { null }
    }

    override fun onCleared() {
        capturedImageFile.value.delete()
        super.onCleared()
    }
}