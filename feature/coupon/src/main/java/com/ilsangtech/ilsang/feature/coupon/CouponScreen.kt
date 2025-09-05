package com.ilsangtech.ilsang.feature.coupon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.feature.coupon.component.AvailableCouponCard
import com.ilsangtech.ilsang.feature.coupon.component.CouponPasswordBottomSheet
import com.ilsangtech.ilsang.feature.coupon.component.CouponScreenHeader
import com.ilsangtech.ilsang.feature.coupon.component.CouponTabRow
import com.ilsangtech.ilsang.feature.coupon.component.CouponUseBottomSheet
import com.ilsangtech.ilsang.feature.coupon.component.CouponUseSuccessDialog
import com.ilsangtech.ilsang.feature.coupon.component.EmptyCouponContent
import com.ilsangtech.ilsang.feature.coupon.component.InvalidCouponPasswordDialog
import com.ilsangtech.ilsang.feature.coupon.component.UsedOrExpiredCouponCard
import com.ilsangtech.ilsang.feature.coupon.model.CouponTab
import com.ilsangtech.ilsang.feature.coupon.model.CouponUiModel
import com.ilsangtech.ilsang.feature.coupon.model.CouponUseUiState
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun CouponScreen(
    viewModel: CouponViewModel = hiltViewModel(),
    onQuestNavButtonClick: () -> Unit,
    onBackButtonClick: () -> Unit
) {
    val selectedCouponTab by viewModel.selectedCouponTab.collectAsStateWithLifecycle()
    val availableCouponItems = viewModel.availableCouponList.collectAsLazyPagingItems()
    val usedOrExpiredCouponItems = viewModel.usedOrExpiredCouponList.collectAsLazyPagingItems()
    val couponUseUiState by viewModel.couponUseUiState.collectAsStateWithLifecycle()

    if (couponUseUiState is CouponUseUiState.UseSuccess) {
        availableCouponItems.refresh()
    }

    CouponScreen(
        selectedCouponTab = selectedCouponTab,
        availableCouponItems = availableCouponItems,
        usedOrExpiredCouponItems = usedOrExpiredCouponItems,
        couponUseUiState = couponUseUiState,
        onCouponTabSelected = viewModel::updateSelectedCouponTab,
        onCouponClick = { coupon ->
            viewModel.updateCouponUseUiState(
                CouponUseUiState.ShowCoupon(coupon)
            )
        },
        onCouponUseButtonClick = { coupon ->
            viewModel.updateCouponUseUiState(
                CouponUseUiState.PasswordVerify(
                    coupon = coupon,
                    password = viewModel.couponPassword
                )
            )
        },
        onVerifyButtonClick = viewModel::verifyCouponPassword,
        onDismissRequest = {
            viewModel.updateCouponUseUiState(
                CouponUseUiState.Initial
            )
        },
        onQuestNavButtonClick = onQuestNavButtonClick,
        onBackButtonClick = onBackButtonClick
    )
}

@Composable
private fun CouponScreen(
    selectedCouponTab: CouponTab,
    availableCouponItems: LazyPagingItems<CouponUiModel>,
    usedOrExpiredCouponItems: LazyPagingItems<CouponUiModel>,
    couponUseUiState: CouponUseUiState,
    onCouponTabSelected: (CouponTab) -> Unit,
    onCouponClick: (CouponUiModel) -> Unit,
    onCouponUseButtonClick: (CouponUiModel) -> Unit,
    onVerifyButtonClick: (CouponUiModel) -> Unit,
    onDismissRequest: () -> Unit,
    onQuestNavButtonClick: () -> Unit,
    onBackButtonClick: () -> Unit
) {
    when (couponUseUiState) {
        is CouponUseUiState.ShowCoupon -> {
            CouponUseBottomSheet(
                coupon = couponUseUiState.coupon,
                onButtonClick = { onCouponUseButtonClick(couponUseUiState.coupon) },
                onDismissRequest = onDismissRequest
            )
        }

        is CouponUseUiState.PasswordVerify -> {
            CouponPasswordBottomSheet(
                coupon = couponUseUiState.coupon,
                password = couponUseUiState.password,
                onButtonClick = { onVerifyButtonClick(couponUseUiState.coupon) },
                onDismissRequest = onDismissRequest
            )
        }

        is CouponUseUiState.WrongPassword -> {
            InvalidCouponPasswordDialog(onDismissRequest = {
                onCouponUseButtonClick(couponUseUiState.coupon)
            })
        }

        is CouponUseUiState.UseSuccess -> {
            CouponUseSuccessDialog(onDismissRequest = onDismissRequest)
        }

        else -> {}
    }

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

            val (itemCount, loadState) =
                if (selectedCouponTab == CouponTab.Available) {
                    availableCouponItems.itemCount to
                            availableCouponItems.loadState.refresh
                } else {
                    usedOrExpiredCouponItems.itemCount to
                            usedOrExpiredCouponItems.loadState.refresh
                }


            if (loadState is LoadState.NotLoading && itemCount == 0) {
                EmptyCouponContent(
                    modifier = Modifier.weight(1f),
                    selectedCouponTab = selectedCouponTab,
                    onQuestNavButtonClick = onQuestNavButtonClick
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(
                    top = 24.dp, bottom = 72.dp,
                    start = 20.dp, end = 20.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (selectedCouponTab == CouponTab.Available) {
                    items(availableCouponItems.itemCount) {
                        val item = availableCouponItems[it]
                        item?.let {
                            AvailableCouponCard(
                                coupon = item,
                                onClick = { onCouponClick(item) }
                            )
                        }
                    }
                } else {
                    items(usedOrExpiredCouponItems.itemCount) {
                        val item = usedOrExpiredCouponItems[it]
                        item?.let {
                            UsedOrExpiredCouponCard(coupon = item)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CouponScreenPreview() {
    var selectedCouponTab by remember { mutableStateOf(CouponTab.Available) }

    val availableCouponItems = flowOf(
        PagingData.from(
            listOf(
                CouponUiModel(
                    1,
                    "Available Coupon 1",
                    "Store A",
                    null,
                    "2024.12.31 23:59",
                    null,
                    false,
                    false
                ),
                CouponUiModel(
                    2,
                    "Available Coupon 2",
                    "Store B",
                    null,
                    "2024.12.31 23:59",
                    null,
                    false,
                    false
                )
            )
        )
    ).collectAsLazyPagingItems()

    val usedOrExpiredCouponItems = flowOf(
        PagingData.from(
            listOf(
                CouponUiModel(
                    3,
                    "Used Coupon",
                    "Store C",
                    null,
                    "2023.12.31 23:59",
                    "2023.12.01",
                    true,
                    false
                ),
                CouponUiModel(
                    4,
                    "Expired Coupon",
                    "Store D",
                    null,
                    "2023.01.01 23:59",
                    null,
                    false,
                    true
                )
            )
        )
    ).collectAsLazyPagingItems()

    CouponScreen(
        selectedCouponTab = selectedCouponTab,
        availableCouponItems = availableCouponItems,
        usedOrExpiredCouponItems = usedOrExpiredCouponItems,
        couponUseUiState = CouponUseUiState.Initial,
        onCouponTabSelected = { selectedCouponTab = it },
        onCouponClick = {},
        onCouponUseButtonClick = {},
        onVerifyButtonClick = {},
        onDismissRequest = {},
        onQuestNavButtonClick = {},
        onBackButtonClick = {}
    )
}