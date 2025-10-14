package com.ilsangtech.ilsang.designsystem.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.tapBoldTextStyle
import com.ilsangtech.ilsang.designsystem.theme.tapRegularTextStyle

@Composable
fun <T> IlsangTabRow(
    modifier: Modifier = Modifier,
    containerColor: Color = Color.Transparent,
    tabList: List<T>,
    selectedTab: T,
    onTabSelected: (T) -> Unit
) {
    require(tabList.size in 2..5) {
        "TabRow must have between 2 and 5 items"
    }
    val selectedTabIndex = tabList.indexOf(selectedTab)
    val indicatorWidth = remember {
        when (tabList.size) {
            2 -> 128.dp
            3 -> 80.dp
            4 -> 60.dp
            else -> 40.dp
        }
    }

    TabRow(
        modifier = modifier,
        selectedTabIndex = selectedTabIndex,
        indicator = { tabPositions ->
            if (selectedTabIndex < tabPositions.size) {
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    width = indicatorWidth,
                    height = 3.dp,
                    color = primary
                )
            }
        },
        divider = { HorizontalDivider(color = gray100) },
        containerColor = containerColor
    ) {
        tabList.forEach { tab ->
            Tab(
                selected = selectedTab == tab,
                unselectedContentColor = gray300,
                selectedContentColor = gray500,
                onClick = { onTabSelected(tab) }
            ) {
                Text(
                    modifier = Modifier.padding(top = 11.dp, bottom = 9.dp),
                    text = tab.toString(),
                    style = if (selectedTab == tab) {
                        tapBoldTextStyle
                    } else {
                        tapRegularTextStyle
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun IlsangTabRowPreview() {
    val tabList = listOf("Tab 1", "Tab 2", "Tab 3")
    var selectedTab by remember { mutableStateOf(tabList.first()) }

    IlsangTabRow(
        tabList = tabList,
        selectedTab = selectedTab,
        onTabSelected = {
            selectedTab = it
        }
    )
}
