package com.ilsangtech.ilsang.feature.profile.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.ilsangtech.ilsang.core.ui.mission.UserMissionHistoryItem
import com.ilsangtech.ilsang.core.ui.mission.model.MissionTypes
import com.ilsangtech.ilsang.core.ui.mission.model.UserMissionHistoryUiModel
import com.ilsangtech.ilsang.designsystem.component.IlsangTabRow
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.heading01
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily

fun LazyListScope.userMissionHistoryContent(
    nickname: String,
    missionType: MissionTypes,
    userMissionHistoryItems: LazyPagingItems<UserMissionHistoryUiModel>,
    onMissionTypeSelected: (MissionTypes) -> Unit,
    onItemClick: (UserMissionHistoryUiModel) -> Unit
) {
    item {
        Text(
            modifier = Modifier.padding(bottom = 20.dp),
            text = "$nickname 님이 수행한 퀘스트",
            style = heading01
        )
    }
    item {
        IlsangTabRow(
            modifier = Modifier.padding(bottom = 20.dp),
            tabList = MissionTypes.entries,
            selectedTab = missionType,
            onTabSelected = onMissionTypeSelected
        )
    }
    if (userMissionHistoryItems.loadState.refresh is LoadState.NotLoading
        && userMissionHistoryItems.itemCount == 0
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "완료된 퀘스트가 없어요",
                    style = TextStyle(
                        fontFamily = pretendardFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 21.sp,
                        lineHeight = 1.5.em
                    ),
                    color = gray500
                )
            }
        }
    }

    items(userMissionHistoryItems.itemCount) { index ->
        val item = userMissionHistoryItems[index]
        item?.let {
            Column {
                UserMissionHistoryItem(
                    userMissionHistory = item,
                    onClick = { onItemClick(item) }
                )
                if (index < userMissionHistoryItems.itemCount - 1) {
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}