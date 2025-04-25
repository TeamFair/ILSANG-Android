package com.ilsangtech.ilsang.feature.home.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.model.Banner
import com.ilsangtech.ilsang.core.model.Quest
import com.ilsangtech.ilsang.feature.home.BuildConfig
import com.ilsangtech.ilsang.feature.home.R
import com.ilsangtech.ilsang.feature.home.quest.QuestBottomSheet

@Composable
fun HomeTapScreen(
    userNickname: String?,
    homeTapUiState: HomeTapUiState,
    navigateToQuestTab: () -> Unit,
    navigateToMyTab: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        var bottomSheetQuest by remember { mutableStateOf<Quest?>(null) }
        var showBottomSheet by remember { mutableStateOf(false) }

        if (showBottomSheet) {
            bottomSheetQuest?.let {
                QuestBottomSheet(
                    quest = it,
                    showBottomSheet = showBottomSheet,
                    onDismiss = {
                        showBottomSheet = false
                        bottomSheetQuest = null
                    },
                    onApproveButtonClick = {}
                )
            }
        }

        if (homeTapUiState is HomeTapUiState.Success) {
            LazyColumn {
                item {
                    HomeTapTopBar(
                        onClickProfile = navigateToMyTab
                    )
                }
                items(homeTapUiState.data.banners) { banner -> BannerView(banner) }
                item { Spacer(Modifier.height(36.dp)) }
                popularQuestsContent(
                    popularQuests = homeTapUiState.data.popularQuests,
                    onPopularQuestClick = {
                        bottomSheetQuest = it
                        showBottomSheet = true
                    }
                )
                item { Spacer(Modifier.height(36.dp)) }
                item {
                    RecommendedQuestsContent(
                        userNickname = userNickname,
                        recommendedQuests = homeTapUiState.data.recommendedQuests,
                        onRecommendedQuestClick = {
                            bottomSheetQuest = it
                            showBottomSheet = true
                        }
                    )
                }
                item { Spacer(Modifier.height(36.dp)) }
                item {
                    LargeRewardQuestsContent(
                        largeRewardQuests = homeTapUiState.data.largeRewardQuests,
                        navigateToQuestTab = navigateToQuestTab,
                        onApproveButtonClick = {}
                    )
                }
                item { Spacer(Modifier.height(36.dp)) }
                item { UserRankContent(homeTapUiState.data.topRankUsers) }
                item { Spacer(Modifier.height(84.dp)) }
            }
        }
    }
}

@Composable
fun HomeTapTopBar(
    modifier: Modifier = Modifier,
    onClickProfile: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .statusBarsPadding()
            .padding(
                top = 8.dp,
                bottom = 16.dp
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.home_ilsang_logo),
            tint = Color.Unspecified,
            contentDescription = null
        )
        Spacer(Modifier.weight(1f))
        Icon(
            modifier = Modifier
                .size(36.dp)
                .clickable(
                    onClick = onClickProfile,
                    indication = null,
                    interactionSource = null
                ),
            painter = painterResource(id = R.drawable.default_user_profile),
            tint = Color.Unspecified,
            contentDescription = null
        )
    }
}


@Composable
fun BannerView(banner: Banner) {
    AsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        model = BuildConfig.IMAGE_URL + banner.imageId,
        contentDescription = banner.description
    )
}

@Preview
@Composable
fun HomeTapScreenPreview() {
    HomeTapScreen(
        "누구누구",
        HomeTapUiState.Loading,
        {}, {}
    )
}