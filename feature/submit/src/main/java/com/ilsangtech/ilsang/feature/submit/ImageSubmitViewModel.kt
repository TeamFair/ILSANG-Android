package com.ilsangtech.ilsang.feature.submit

import android.content.Context
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ilsangtech.ilsang.core.domain.ChallengeRepository
import com.ilsangtech.ilsang.core.domain.QuestRepository
import com.ilsangtech.ilsang.core.util.FileManager
import com.ilsangtech.ilsang.feature.submit.navigation.SubmitRoute
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
    private val questRepository: QuestRepository,
    private val challengeRepository: ChallengeRepository
) : ViewModel() {
    private val questId = savedStateHandle.toRoute<SubmitRoute>().questId
    private val _capturedImageUri = MutableStateFlow<Uri?>(null)
    val capturedImageFile = MutableStateFlow(FileManager.createCacheFile(context)).asStateFlow()

    private val _submitUiState = MutableStateFlow<SubmitUiState>(SubmitUiState.NotSubmitted)
    val submitUiState = _submitUiState.asStateFlow()

    fun submitApproveImage() {
        viewModelScope.launch {
            runCatching {
                _submitUiState.update { SubmitUiState.Loading }
                //TODO 퀘스트 보상 단일 조회 호출
            }.onSuccess {
                _submitUiState.update {
                    SubmitUiState.Success(
                        //TODO 실제 보상 리스트
                        emptyList()
                    )
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