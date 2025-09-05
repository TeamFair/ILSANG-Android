package com.ilsangtech.ilsang.feature.coupon.component

import androidx.compose.foundation.layout.fillMaxWidth
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
import com.ilsangtech.ilsang.feature.coupon.model.CouponTab

@Composable
internal fun CouponTabRow(
    modifier: Modifier = Modifier,
    selectedTab: CouponTab,
    onTabSelected: (CouponTab) -> Unit
) {
    TabRow(
        modifier = modifier.fillMaxWidth(),
        selectedTabIndex = selectedTab.ordinal,
        containerColor = Color.Transparent,
        indicator = { tabPositions ->
            if (selectedTab.ordinal < tabPositions.size) {
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(
                        tabPositions[selectedTab.ordinal]
                    ),
                    width = 127.dp,
                    height = 3.dp,
                    color = primary
                )
            }

        },
        divider = { HorizontalDivider(color = gray100) }
    ) {
        CouponTab.entries.forEach { couponTab ->
            Tab(
                selected = selectedTab == couponTab,
                selectedContentColor = gray500,
                unselectedContentColor = gray300,
                onClick = { onTabSelected(couponTab) }
            ) {
                Text(
                    modifier = Modifier.padding(top = 11.dp, bottom = 9.dp),
                    text = "$couponTab",
                    style = tapBoldTextStyle
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CouponTabRowPreview() {
    var selectedTab by remember { mutableStateOf(CouponTab.Available) }
    CouponTabRow(
        selectedTab = selectedTab,
        onTabSelected = {
            selectedTab = it
        }
    )
}