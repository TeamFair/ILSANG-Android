package com.ilsangtech.ilsang.feature.home.my

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ilsangtech.ilsang.core.model.Challenge
import com.ilsangtech.ilsang.core.model.UserInfo
import com.ilsangtech.ilsang.feature.home.HomeViewModel
import com.ilsangtech.ilsang.feature.home.my.component.MyChallengeContent
import com.ilsangtech.ilsang.feature.home.my.component.MyInfoMenuContent
import com.ilsangtech.ilsang.feature.home.my.component.MyTabHeader
import com.ilsangtech.ilsang.feature.home.my.component.MyTabMenuContent
import com.ilsangtech.ilsang.feature.home.my.component.UserProfileContent
import kotlinx.coroutines.flow.asFlow

@Composable
fun MyTabScreen(
    homeViewModel: HomeViewModel,
    navigateToNicknameEdit: () -> Unit
) {
    val userInfo by homeViewModel.userInfo.collectAsStateWithLifecycle()
    val challengePager = homeViewModel.challengePager.collectAsLazyPagingItems()

    MyTabScreen(
        userInfo = userInfo,
        challengePager = challengePager,
        navigateToNicknameEdit = navigateToNicknameEdit
    )
}

@Composable
fun MyTabScreen(
    userInfo: UserInfo?,
    challengePager: LazyPagingItems<Challenge>,
    navigateToNicknameEdit: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        var selectedMenu by remember { mutableStateOf(MyTabMenu.CHALLENGE) }
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 16.dp)
        ) {
            MyTabHeader()
            Spacer(Modifier.height(5.dp))
            UserProfileContent(
                userInfo = userInfo!!,
                navigateToNicknameEdit = navigateToNicknameEdit
            )
            Spacer(Modifier.height(16.dp))
            MyTabMenuContent(selectedMenu = selectedMenu) {
                selectedMenu = it
            }
            when (selectedMenu) {
                MyTabMenu.CHALLENGE -> {
                    MyChallengeContent(challengePager)
                }

                MyTabMenu.ACTIVITY -> {}
                MyTabMenu.INFO -> {
                    MyInfoMenuContent(userInfo)
                }
            }
        }
    }
}

@Preview
@Composable
fun MyTabScreenPreview() {
    MyTabScreen(
        userInfo = UserInfo(
            accessToken = "",
            authorization = "",
            nickname = "김일상1234",
            email = "",
            profileImage = null,
            completeChallengeCount = 0,
            couponCount = 0,
            xpPoint = 0,
            status = ""
        ),
        challengePager = emptyList<PagingData<Challenge>>().asFlow().collectAsLazyPagingItems()
    ) {}
}