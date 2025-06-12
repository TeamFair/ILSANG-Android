package com.ilsangtech.ilsang.feature.home.ranking

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ilsangtech.ilsang.core.model.RewardType
import com.ilsangtech.ilsang.core.model.UserXpTypeRank
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_bold
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.feature.home.HomeViewModel

@Composable
fun RankingScreen(
    homeViewModel: HomeViewModel,
    navigateToProfile: (String) -> Unit
) {
    val rankingUiState by homeViewModel.rankingUiState.collectAsStateWithLifecycle()
    RankingScreen(
        rankingUiState = rankingUiState,
        onRankingItemClick = navigateToProfile
    )
}

@Composable
fun RankingScreen(
    rankingUiState: Map<RewardType, List<UserXpTypeRank>>,
    onRankingItemClick: (String) -> Unit
) {
    var selectedRewardType by remember { mutableStateOf(RewardType.entries.first()) }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        color = background
    ) {
        Column {
            RankingScreenHeader(modifier = Modifier.statusBarsPadding())
            StatRankingTabRow(
                selectedRewardType = selectedRewardType,
                onRewardTypeSelect = { selectedRewardType = it }
            )
            rankingUiState[selectedRewardType]?.let { userXpTypeRanks ->
                StatRankingTabContent(
                    selectedRewardType = selectedRewardType,
                    userXpTypeRanks = userXpTypeRanks,
                    onItemClick = onRankingItemClick
                )
            }
        }
    }
}

@Composable
fun RankingScreenHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = 10.dp,
                horizontal = 20.dp
            )
    ) {
        Text(
            text = "랭킹",
            style = TextStyle(
                fontFamily = FontFamily(Font(pretendard_bold)),
                fontSize = 21.sp,
                lineHeight = 1.4.em,
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Center,
                    trim = LineHeightStyle.Trim.None
                ),
                color = gray500
            )
        )
    }
}

@Preview
@Composable
private fun RankingScreenPreview() {
    RankingScreen(
        rankingUiState = emptyMap(),
        onRankingItemClick = {}
    )
}