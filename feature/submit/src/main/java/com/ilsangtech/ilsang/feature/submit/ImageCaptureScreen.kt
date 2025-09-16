package com.ilsangtech.ilsang.feature.submit

import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.compose.CameraXViewfinder
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.asFlow
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.component.ILSANGDialog
import com.ilsangtech.ilsang.designsystem.theme.background

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun ImageCaptureScreen(
    viewModel: ImageCaptureViewModel = viewModel(),
    navigateToImageSubmit: (String, Int, Int) -> Unit,
    popBackStack: () -> Unit
) {
    val appContext = LocalContext.current.applicationContext
    val lifecycleOwner = LocalLifecycleOwner.current

    val mediaAccessPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        android.Manifest.permission.READ_MEDIA_IMAGES
    } else {
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    val mediaAccessPermissionState = rememberPermissionState(mediaAccessPermission)

    val surfaceRequest by viewModel.surfaceRequest.collectAsStateWithLifecycle()
    val latestImageUri by viewModel.latestImageUri.collectAsStateWithLifecycle()
    val selectedImageUri by viewModel.selectedImageUri.collectAsStateWithLifecycle()

    var showCameraPermissionDialog by remember { mutableStateOf(false) }
    var showMediaAccessPermissionDialog by remember { mutableStateOf(false) }

    val pickImage =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let { viewModel.selectGalleryImage(it) }
        }

    val cameraZoom by produceState(1f) {
        viewModel.camera.collect { camera ->
            camera?.cameraInfo?.zoomState?.asFlow()?.collect { zoomState ->
                value = zoomState.zoomRatio
            }
        }
    }

    val cameraState = rememberTransformableState { zoomChange, _, _ ->
        viewModel.updateCameraZoom(cameraZoom * zoomChange)
    }

    if (selectedImageUri != null) {
        navigateToImageSubmit(
            selectedImageUri.toString(),
            viewModel.questId,
            viewModel.missionId
        )
        viewModel.clearSelectedImage()
    }

    if (showCameraPermissionDialog) {
        ILSANGDialog(
            title = "카메라 권한",
            content = "일상은 퀘스트 인증 사진을 촬영하기 위해 카메라 접근 권한이 필요합니다.\n" +
                    "계속하려면 카메라 접근 권한을 허용해주세요.",
            positiveButtonText = "허용",
            negativeButtonText = "허용 안함",
            onClickPositiveButton = {
                cameraPermissionState.launchPermissionRequest()
                showCameraPermissionDialog = false
            },
            onClickNegativeButton = {
                showCameraPermissionDialog = false
                popBackStack()
            },
            onDismissRequest = { showCameraPermissionDialog = false }
        )
    }

    if (showMediaAccessPermissionDialog) {
        ILSANGDialog(
            title = "앨범 접근 권한",
            content = "일상은 퀘스트 인증에 필요한 사진을 불러오기 위해 앨범 접근 권한이 필요합니다.",
            positiveButtonText = "허용",
            negativeButtonText = "허용 안함",
            onClickPositiveButton = {
                mediaAccessPermissionState.launchPermissionRequest()
                showMediaAccessPermissionDialog = false
            },
            onClickNegativeButton = { showMediaAccessPermissionDialog = false },
            onDismissRequest = { showMediaAccessPermissionDialog = false }
        )
    }

    LaunchedEffect(cameraPermissionState.status) {
        when {
            cameraPermissionState.status.isGranted -> {
                viewModel.bindToCamera(appContext, lifecycleOwner)
            }

            cameraPermissionState.status.shouldShowRationale -> {
                showCameraPermissionDialog = true
            }

            else -> {
                cameraPermissionState.launchPermissionRequest()
            }
        }
    }

    LaunchedEffect(
        cameraPermissionState.status,
        mediaAccessPermissionState.status
    ) {
        if (cameraPermissionState.status.isGranted) {
            when {
                mediaAccessPermissionState.status.isGranted -> {
                    viewModel.updateLatestImageUri(appContext)
                }

                mediaAccessPermissionState.status.shouldShowRationale -> {
                    showMediaAccessPermissionDialog = true
                }

                else -> {
                    mediaAccessPermissionState.launchPermissionRequest()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        surfaceRequest?.let { request ->
            CameraXViewfinder(
                modifier = Modifier
                    .fillMaxSize()
                    .transformable(state = cameraState),
                surfaceRequest = request
            )
        }
        BackButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .statusBarsPadding()
                .padding(top = 12.dp, start = 15.dp),
            onClick = popBackStack
        )
        ImageCaptureButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
                .navigationBarsPadding(),
            onClick = { viewModel.captureImage(appContext) }
        )
        ImageThumbnailButton(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 30.dp, bottom = 54.dp)
                .navigationBarsPadding(),
            imageUri = latestImageUri,
            onClick = {
                pickImage.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }
        )
    }
}

@Composable
private fun ImageCaptureButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier,
        color = Color.Transparent,
        shape = CircleShape,
        onClick = onClick,
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .border(
                    width = 5.dp,
                    shape = CircleShape,
                    color = Color.White
                )
        )
    }
}

@Composable
private fun ImageThumbnailButton(
    modifier: Modifier = Modifier,
    imageUri: Uri?,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .size(50.dp)
            .clip(RoundedCornerShape(8.dp)),
        color = background,
        onClick = onClick
    ) {
        AsyncImage(
            model = imageUri,
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}

@Composable
private fun BackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Icon(
        modifier = modifier.clickable(
            onClick = onClick,
            indication = null,
            interactionSource = null
        ),
        painter = painterResource(R.drawable.icon_chevron_back),
        tint = Color.White,
        contentDescription = null
    )
}