package com.ilsangtech.ilsang.feature.submit

import android.net.Uri
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.buttonTextStyle
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.primary100
import com.ilsangtech.ilsang.designsystem.theme.title02
import com.ilsangtech.ilsang.feature.submit.component.SubmitErrorDialog
import com.ilsangtech.ilsang.feature.submit.component.SubmitLoadingDialog
import com.ilsangtech.ilsang.feature.submit.component.SubmitSuccessDialog
import com.ilsangtech.ilsang.feature.submit.model.SubmitResultUiState

@Composable
internal fun ImageSubmitScreen(
    imageSubmitViewModel: ImageSubmitViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    onSubmitSuccess: () -> Unit
) {
    val submitUiState by imageSubmitViewModel.submitUiState.collectAsStateWithLifecycle()

    when (submitUiState) {
        is SubmitResultUiState.Loading -> {
            SubmitLoadingDialog()
        }

        is SubmitResultUiState.Success -> {
            val rewardList = (submitUiState as SubmitResultUiState.Success).rewardPoints
            SubmitSuccessDialog(
                rewardPoints = rewardList,
                isIsZoneQuest = false,
                onDismissRequest = {
                    imageSubmitViewModel.completeSubmit()
                    onSubmitSuccess()
                }
            )
        }

        is SubmitResultUiState.Error -> {
            SubmitErrorDialog(
                onDismissRequest = {
                    imageSubmitViewModel.completeSubmit()
                }
            )
        }

        else -> {}
    }

    ImageSubmitScreen(
        imageUri = imageSubmitViewModel.imageUri,
        onBackButtonClick = popBackStack,
        onRetakeButtonClick = popBackStack,
        onSubmitButtonClick = imageSubmitViewModel::submitApproveImage
    )
}

@Composable
private fun ImageSubmitScreen(
    imageUri: Uri,
    onBackButtonClick: () -> Unit,
    onRetakeButtonClick: () -> Unit,
    onSubmitButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        ImageSubmitScreenHeader(onBackButtonClick)
        Box(modifier = Modifier.weight(1f)) {
            AsyncImage(
                model = imageUri,
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(1f)
                    .padding(bottom = 58.dp)
            )
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                ImageSubmitScreenFooter(
                    onRetakeButtonClick = onRetakeButtonClick,
                    onSubmitButtonClick = onSubmitButtonClick
                )
            }
        }
    }
}

@Composable
private fun ImageSubmitScreenHeader(
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
            painter = painterResource(R.drawable.icon_back),
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
private fun ImageSubmitScreenFooter(
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
private fun ImageSubmitScreenPreview() {
    ImageSubmitScreen(
        "".toUri(),
        {},
        {},
        {}
    )
}