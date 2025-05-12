package com.ilsangtech.ilsang.feature.home.my

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.model.Challenge
import com.ilsangtech.ilsang.feature.home.BuildConfig
import com.ilsangtech.ilsang.feature.home.HomeViewModel
import com.ilsangtech.ilsang.feature.home.my.component.MyChallengeHeader
import com.ilsangtech.ilsang.feature.home.my.component.MyChallengeInfoCard

@Composable
fun MyChallengeScreen(
    homeViewModel: HomeViewModel,
    navigateToMyTabMain: () -> Unit
) {
    val challenge by homeViewModel.selectedChallenge.collectAsStateWithLifecycle()
    MyChallengeScreen(
        challenge = challenge!!,
        navigateToMyTabMain = navigateToMyTabMain
    )
}

@Composable
fun MyChallengeScreen(
    challenge: Challenge,
    navigateToMyTabMain: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            MyChallengeHeader(onBackButtonClick = navigateToMyTabMain)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = BuildConfig.IMAGE_URL + challenge.receiptImageId,
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
                MyChallengeInfoCard(
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

@Preview
@Composable
fun MyChallengeScreenPreview() {
    MyChallengeScreen(
        Challenge(
            challengeId = "",
            createdAt = "",
            hateCnt = 12,
            likeCnt = 13,
            missionTitle = "겨울 간식 먹기",
            questImage = "",
            receiptImageId = "",
            status = "",
            userNickName = "",
            viewCount = 1302
        ), {}
    )
}