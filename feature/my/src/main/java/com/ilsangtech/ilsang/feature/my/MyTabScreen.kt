package com.ilsangtech.ilsang.feature.my

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ilsangtech.ilsang.core.model.Challenge
import com.ilsangtech.ilsang.core.model.MyInfo
import com.ilsangtech.ilsang.core.model.UserXpStats
import com.ilsangtech.ilsang.feature.my.component.MyChallengeContent
import com.ilsangtech.ilsang.feature.my.component.MyInfoMenuContent
import com.ilsangtech.ilsang.feature.my.component.MyTabHeader
import com.ilsangtech.ilsang.feature.my.component.MyTabMenuContent
import com.ilsangtech.ilsang.feature.my.component.UserProfileContent

@Composable
fun MyTabScreen(
    myTabViewModel: MyTabViewModel = hiltViewModel(),
    navigateToNicknameEdit: () -> Unit,
    navigateToMyChallenge: (String, String?, String?, String, Int, Int) -> Unit,
    navigateToSetting: () -> Unit,
    navigateToMyTitle: (titleId: String?) -> Unit
) {
    val myInfo by myTabViewModel.myInfo.collectAsStateWithLifecycle()
    val userXpStats by myTabViewModel.userXpStats.collectAsStateWithLifecycle()
    val challengePager = myTabViewModel.challengePager.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        challengePager.refresh()
    }

    MyTabScreen(
        myInfo = myInfo,
        userXpStats = userXpStats,
        challengePager = challengePager,
        navigateToNicknameEdit = navigateToNicknameEdit,
        navigateToMyChallenge = {
            navigateToMyChallenge(
                it.challengeId,
                it.receiptImageId,
                it.questImage,
                it.missionTitle,
                it.viewCount,
                it.likeCnt
            )
        },
        navigateToSetting = navigateToSetting,
        navigateToMyTitle = {}
    )
}

@Composable
fun MyTabScreen(
    myInfo: MyInfo?,
    userXpStats: UserXpStats,
    challengePager: LazyPagingItems<Challenge>,
    navigateToNicknameEdit: () -> Unit,
    navigateToMyChallenge: (Challenge) -> Unit,
    navigateToSetting: () -> Unit,
    navigateToMyTitle: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        var selectedMenu by remember { mutableStateOf(MyTabMenu.CHALLENGE) }
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 20.dp)
        ) {
            MyTabHeader(onSettingIconClick = navigateToSetting)
            Spacer(Modifier.height(5.dp))
            UserProfileContent(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                myInfo = myInfo!!,
                navigateToNicknameEdit = navigateToNicknameEdit,
                onMyTitleClick = navigateToMyTitle
            )
            Spacer(Modifier.height(16.dp))
            MyTabMenuContent(selectedMenu = selectedMenu) {
                selectedMenu = it
            }
            when (selectedMenu) {
                MyTabMenu.CHALLENGE -> {
                    MyChallengeContent(
                        challengePager = challengePager,
                        onMyChallengeClick = navigateToMyChallenge
                    )
                }

                MyTabMenu.ACTIVITY -> {}
                MyTabMenu.INFO -> {
                    MyInfoMenuContent(
                        myInfo = myInfo,
                        userXpStats = userXpStats,
                        onMyTitleCardClick = navigateToMyTitle
                    )
                }
            }
        }
    }
}
