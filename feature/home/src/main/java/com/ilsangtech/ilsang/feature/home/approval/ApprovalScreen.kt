package com.ilsangtech.ilsang.feature.home.approval

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
fun ApprovalScreen(approvalViewModel: ApprovalViewModel = hiltViewModel()) {
    val randomChallenges = approvalViewModel.randomChallenges.collectAsLazyPagingItems()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = background
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 48.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(Modifier.statusBarsPadding())
            }
            items(randomChallenges.itemCount) {
                ApprovalItem(challenge = randomChallenges[it]!!)
            }
        }
    }
}
