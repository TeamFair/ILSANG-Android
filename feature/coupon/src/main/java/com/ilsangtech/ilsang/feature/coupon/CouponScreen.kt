package com.ilsangtech.ilsang.feature.coupon

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun CouponScreen(
    viewModel: CouponViewModel = hiltViewModel(),
    onQuestNavButtonClick: () -> Unit,
    onBackButtonClick: () -> Unit
) {
    CouponScreen(
        onQuestNavButtonClick = onQuestNavButtonClick,
        onBackButtonClick = onBackButtonClick
    )
}

@Composable
private fun CouponScreen(
    onQuestNavButtonClick: () -> Unit,
    onBackButtonClick: () -> Unit
) {
}