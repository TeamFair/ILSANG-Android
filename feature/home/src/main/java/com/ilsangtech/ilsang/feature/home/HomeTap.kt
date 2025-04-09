package com.ilsangtech.ilsang.feature.home

import com.ilsangtech.ilsang.designsystem.R


enum class HomeTap(
    val title: String,
    val defaultIconRes: Int,
    val selectedIconRes: Int
) {
    Home(
        title = "홈",
        defaultIconRes = R.drawable.home,
        selectedIconRes = R.drawable.selected_home
    ),

    Quest(
        title = "퀘스트",
        defaultIconRes = R.drawable.quest,
        selectedIconRes = R.drawable.selected_quest
    ),

    Approval(
        title = "인증",
        defaultIconRes = R.drawable.approval,
        selectedIconRes = R.drawable.selected_approval
    ),

    Ranking(
        title = "랭킹",
        defaultIconRes = R.drawable.ranking,
        selectedIconRes = R.drawable.selected_ranking
    ),

    My(
        title = "마이",
        defaultIconRes = R.drawable.my,
        selectedIconRes = R.drawable.selected_my
    )
}