package com.ilsangtech.ilsang.feature.submit

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceRequest
import androidx.camera.core.takePicture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ilsangtech.ilsang.core.util.FileManager
import com.ilsangtech.ilsang.feature.submit.navigation.ImageCaptureRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageCaptureViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val questId = savedStateHandle.toRoute<ImageCaptureRoute>().questId
    val missionId = savedStateHandle.toRoute<ImageCaptureRoute>().missionId

    private val _surfaceRequest = MutableStateFlow<SurfaceRequest?>(null)
    val surfaceRequest = _surfaceRequest.asStateFlow()

    val camera = MutableStateFlow<Camera?>(null)
    private var processCameraProvider: ProcessCameraProvider? = null

    private val cameraPreviewUseCase = Preview.Builder().build().apply {
        setSurfaceProvider { request ->
            _surfaceRequest.update { request }
        }
    }
    private val imageCapture = ImageCapture.Builder().build()

    private val _latestImageUri = MutableStateFlow<Uri?>(null)
    val latestImageUri = _latestImageUri.asStateFlow()

    private val _selectedImageUri = MutableStateFlow<Uri?>(null)
    val selectedImageUri = _selectedImageUri.asStateFlow()

    suspend fun bindToCamera(appContext: Context, lifecycleOwner: LifecycleOwner) {
        processCameraProvider = ProcessCameraProvider.awaitInstance(appContext)

        camera.update {
            processCameraProvider?.bindToLifecycle(
                lifecycleOwner, DEFAULT_BACK_CAMERA, imageCapture, cameraPreviewUseCase
            )
        }
    }

    fun updateLatestImageUri(appContext: Context) {
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATE_ADDED
        )

        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        val cursor = appContext.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )

        cursor?.use {
            if (it.moveToFirst()) {
                val id = it.getLong(it.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                _latestImageUri.update {
                    ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id
                    )
                }
            }
        }
    }

    fun updateCameraZoom(zoomLevel: Float) {
        camera.value?.cameraControl?.setZoomRatio(zoomLevel)
    }

    fun captureImage(appContext: Context) {
        viewModelScope.launch {
            try {
                val cacheFile = FileManager.createCacheFile(appContext)
                val outputOptions = ImageCapture.OutputFileOptions.Builder(cacheFile).build()

                val result = imageCapture.takePicture(outputOptions)

                val uri = result.savedUri ?: Uri.fromFile(cacheFile)
                _selectedImageUri.update { uri }
            } catch (e: Exception) {
                Log.e("ImageCapture", "Capture failed", e)
            }
        }
    }

    fun selectGalleryImage(uri: Uri) {
        _selectedImageUri.update { uri }
    }

    fun clearSelectedImage() {
        _selectedImageUri.update { null }
    }

    override fun onCleared() {
        super.onCleared()
        processCameraProvider?.unbindAll()
    }
}