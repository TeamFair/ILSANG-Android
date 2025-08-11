package com.ilsangtech.ilsang.navigation

import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.feature.approval.navigation.ApprovalRoute
import com.ilsangtech.ilsang.feature.home.navigation.HomeRoute
import com.ilsangtech.ilsang.feature.my.navigation.MyRoute
import com.ilsangtech.ilsang.feature.quest.navigation.QuestRoute
import com.ilsangtech.ilsang.feature.ranking.navigation.RankingRoute
import kotlin.reflect.KClass

fun NavHostController.navigateToTopLevelDestination(bottomTab: BottomTab) {
    val navOptions = navOptions {
        popUpTo(HomeRoute) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
    when (bottomTab) {
        BottomTab.Home -> navigate(HomeRoute, navOptions)
        BottomTab.Quest -> navigate(QuestRoute, navOptions)
        BottomTab.Approval -> navigate(ApprovalRoute, navOptions)
        BottomTab.Ranking -> navigate(RankingRoute, navOptions)
        BottomTab.My -> navigate(MyRoute, navOptions)
    }
}

enum class BottomTab(
    val title: String,
    val defaultIconRes: Int,
    val selectedIconRes: Int,
    val route: KClass<*>
) {
    Home(
        title = "홈",
        defaultIconRes = R.drawable.home,
        selectedIconRes = R.drawable.selected_home,
        route = HomeRoute::class
    ),

    Quest(
        title = "퀘스트",
        defaultIconRes = R.drawable.quest,
        selectedIconRes = R.drawable.selected_quest,
        route = QuestRoute::class
    ),

    Approval(
        title = "인증",
        defaultIconRes = R.drawable.approval,
        selectedIconRes = R.drawable.selected_approval,
        route = ApprovalRoute::class
    ),

    Ranking(
        title = "랭킹",
        defaultIconRes = R.drawable.ranking,
        selectedIconRes = R.drawable.selected_ranking,
        route = RankingRoute::class
    ),

    My(
        title = "마이",
        defaultIconRes = R.drawable.my,
        selectedIconRes = R.drawable.selected_my,
        route = MyRoute::class
    )
}