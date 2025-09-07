package com.ilsangtech.ilsang.feature.coupon.model

sealed interface CouponUseUiState {
    data object Initial : CouponUseUiState
    data class ShowCoupon(val coupon: CouponUiModel) : CouponUseUiState
    data class PasswordVerify(
        val coupon: CouponUiModel,
        val isWrongPassword: Boolean = false
    ) : CouponUseUiState

    data object UseSuccess : CouponUseUiState
}