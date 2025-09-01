package com.ilsangtech.ilsang.feature.my.screens.challenge_detail

import android.content.Intent
import android.graphics.Bitmap
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.feature.my.BuildConfig
import com.ilsangtech.ilsang.feature.my.component.ChallengeDeleteDialog
import com.ilsangtech.ilsang.feature.my.screens.challenge_detail.component.MyChallengeDetailHeader
import com.ilsangtech.ilsang.feature.my.screens.challenge_detail.component.MyChallengeDetailInfoCard
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun MyChallengeDetailScreen(
    myChallengeViewModel: MyChallengeDetailViewModel = hiltViewModel(),
    navigateToMyTabMain: () -> Unit
) {
    val challengeUiState by myChallengeViewModel.challengeUiState.collectAsStateWithLifecycle()
    val isChallengeDeleteSuccess by myChallengeViewModel.isChallengeDeleteSuccess.collectAsStateWithLifecycle()

    LaunchedEffect(isChallengeDeleteSuccess) {
        if (isChallengeDeleteSuccess == true) {
            navigateToMyTabMain()
        }
    }

    MyChallengeDetailScreen(
        challenge = challengeUiState,
        onDeleteButtonClick = myChallengeViewModel::deleteChallenge,
        navigateToMyTabMain = navigateToMyTabMain
    )
}

@Composable
fun MyChallengeDetailScreen(
    challenge: MyChallengeDetailUiState?,
    onDeleteButtonClick: () -> Unit,
    navigateToMyTabMain: () -> Unit
) {
    val context = LocalContext.current
    val graphicsLayer = rememberGraphicsLayer()
    val coroutineScope = rememberCoroutineScope()

    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        ChallengeDeleteDialog(
            onDeleteButtonClick = {
                onDeleteButtonClick()
                showDeleteDialog = false
            },
            onDismissRequest = { showDeleteDialog = false }
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            MyChallengeDetailHeader(
                onBackButtonClick = navigateToMyTabMain,
                onShareButtonClick = {
                    coroutineScope.launch {
                        val imageBitmap = graphicsLayer.toImageBitmap()
                        val fileName = "user_profile_image.png"
                        val file = File(context.cacheDir, fileName).apply {
                            deleteOnExit()
                        }
                        val outputStream = file.outputStream()

                        imageBitmap.asAndroidBitmap()
                            .compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                        outputStream.close()

                        val uri = FileProvider.getUriForFile(
                            context,
                            "${context.packageName}.fileprovider",
                            file
                        )
                        val sendIntent = Intent(Intent.ACTION_SEND).apply {
                            setType("image/png")
                            putExtra(Intent.EXTRA_STREAM, uri)
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            setDataAndType(uri, "image/png")
                        }
                        context.startActivity(Intent.createChooser(sendIntent, "공유하기"))
                    }
                },
                onDeleteButtonClick = { showDeleteDialog = true }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .drawWithContent {
                        graphicsLayer.record {
                            this@drawWithContent.drawContent()
                        }
                        drawLayer(graphicsLayer = graphicsLayer)
                    }
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = BuildConfig.IMAGE_URL + challenge?.receiptImageId,
                    contentScale = ContentScale.FillWidth,
                    contentDescription = null
                )
                challenge?.let {
                    MyChallengeDetailInfoCard(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 16.dp)
                            .padding(horizontal = 20.dp),
                        challenge = challenge
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MyChallengeDetailScreenPreview() {
    MyChallengeDetailScreen(
        challenge = MyChallengeDetailUiState(
            challengeId = "",
            likeCount = 13,
            title = "겨울 간식 먹기",
            questImageId = "",
            receiptImageId = "",
            viewCount = 1302
        ),
        onDeleteButtonClick = {},
        navigateToMyTabMain = {}
    )
}