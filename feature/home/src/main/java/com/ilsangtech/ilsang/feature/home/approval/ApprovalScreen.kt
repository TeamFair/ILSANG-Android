package com.ilsangtech.ilsang.feature.home.approval

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.ilsangtech.ilsang.designsystem.theme.background

@Composable
fun ApprovalScreen(
    approvalViewModel: ApprovalViewModel = hiltViewModel(),
    navigateToProfile: (String) -> Unit
) {
    val randomChallenges = approvalViewModel.randomChallenges.collectAsLazyPagingItems()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        color = background
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 48.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(Modifier.statusBarsPadding()) }
            items(randomChallenges.itemCount) {
                randomChallenges[it]?.let { randomChallenge ->
                    ApprovalItem(
                        challenge = randomChallenge,
                        onProfileClick = { navigateToProfile(randomChallenge.customerId) },
                        onLikeButtonClick = {
                            approvalViewModel.likeChallenge(randomChallenge)
                        },
                        onHateButtonClick = {
                            approvalViewModel.hateChallenge(randomChallenge)
                        }
                    )
                }
            }
        }
    }
}
