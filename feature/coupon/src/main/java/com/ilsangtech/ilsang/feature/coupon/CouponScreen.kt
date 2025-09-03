package com.ilsangtech.ilsang.feature.coupon

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.feature.coupon.component.CouponScreenHeader
import com.ilsangtech.ilsang.feature.coupon.component.CouponTabRow
import com.ilsangtech.ilsang.feature.coupon.model.CouponTab

@Composable
internal fun CouponScreen(
    viewModel: CouponViewModel = hiltViewModel(),
    onQuestNavButtonClick: () -> Unit,
    onBackButtonClick: () -> Unit
) {
    val selectedCouponTab by viewModel.selectedCouponTab.collectAsStateWithLifecycle()

    CouponScreen(
        selectedCouponTab = selectedCouponTab,
        onCouponTabSelected = viewModel::updateSelectedCouponTab,
        onQuestNavButtonClick = onQuestNavButtonClick,
        onBackButtonClick = onBackButtonClick
    )
}

@Composable
private fun CouponScreen(
    selectedCouponTab: CouponTab,
    onCouponTabSelected: (CouponTab) -> Unit,
    onQuestNavButtonClick: () -> Unit,
    onBackButtonClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = background
    ) {
        Column {
            CouponScreenHeader(onBackButtonClick = onBackButtonClick)
            CouponTabRow(
                selectedTab = selectedCouponTab,
                onTabSelected = onCouponTabSelected
            )
        }
    }
}