package com.ilsangtech.ilsang.feature.home.submit

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.ilsangtech.ilsang.designsystem.theme.buttonTextStyle
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.primary100
import com.ilsangtech.ilsang.designsystem.theme.title02
import com.ilsangtech.ilsang.feature.home.HomeViewModel
import com.ilsangtech.ilsang.feature.home.R
import com.ilsangtech.ilsang.feature.home.util.FileManager

@Composable
fun SubmitScreen(
    homeViewModel: HomeViewModel,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val imageUri by homeViewModel.capturedImageUri.collectAsStateWithLifecycle()
    var retakeImageUri by remember { mutableStateOf<Uri?>(null) }
    val selectedQuest by homeViewModel.selectedQuest.collectAsStateWithLifecycle()
    val submitUiState by homeViewModel.submitUiState.collectAsStateWithLifecycle()

    val imageCaptureLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                homeViewModel.setCapturedImageUri(retakeImageUri!!)
            }
        }

    when (submitUiState) {
        is SubmitUiState.Loading -> SubmitLoadingDialog()
        is SubmitUiState.Success -> SubmitSuccessDialog(
            quest = selectedQuest!!,
            onDismiss = {
                homeViewModel.completeSubmit()
                onDismiss()
            }
        )

        is SubmitUiState.Error -> SubmitErrorDialog(
            onDismissRequest = {
                homeViewModel.completeSubmit()
                onDismiss()
            }
        )

        else -> {}
    }

    SubmitScreen(
        imageUri = imageUri,
        onRetakeButtonClick = {
            retakeImageUri = FileManager.createCacheFile(context)
            imageCaptureLauncher.launch(retakeImageUri!!)
        },
        onSubmitButtonClick = {
            homeViewModel.submitApproveImage(
                FileManager.getBytesFromUri(context, imageUri!!)
            )
        }

    )
}

@Composable
fun SubmitScreen(
    imageUri: Uri?,
    onRetakeButtonClick: () -> Unit,
    onSubmitButtonClick: () -> Unit
) {
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        SubmitScreenHeader { }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        Box(modifier = Modifier.weight(1f)) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(imageUri)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(1f)
                    .padding(bottom = 58.dp)
            )
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                SubmitScreenFooter(
                    onRetakeButtonClick = onRetakeButtonClick,
                    onSubmitButtonClick = onSubmitButtonClick
                )
            }
        }
    }
}

@Composable
fun SubmitScreenHeader(
    onBackButtonClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .statusBarsPadding()
            .padding(vertical = 12.dp)
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 15.dp)
                .clickable(
                    indication = null,
                    interactionSource = null,
                    onClick = onBackButtonClick
                ),
            painter = painterResource(R.drawable.back),
            contentDescription = "뒤로가기"
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "퀘스트 인증하기",
            style = title02,
            color = gray500
        )
    }
}

@Composable
fun SubmitScreenFooter(
    onRetakeButtonClick: () -> Unit,
    onSubmitButtonClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = 24.dp,
                    topEnd = 24.dp
                )
            )
            .background(Color.White)
            .padding(
                top = 18.dp,
                bottom = 8.dp
            )
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(13.dp)
    ) {
        Button(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primary100,
                contentColor = primary
            ),
            shape = RoundedCornerShape(12.dp),
            onClick = onRetakeButtonClick
        ) {
            Text(
                text = "다시 찍기",
                style = buttonTextStyle
            )
        }
        Button(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primary,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            onClick = onSubmitButtonClick
        ) {
            Text(
                text = "제출하기",
                style = buttonTextStyle
            )
        }
    }
}

@Preview
@Composable
fun SubmitScreenPreview() {
    SubmitScreen(null, {}, {})
}