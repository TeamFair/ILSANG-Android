package com.ilsangtech.ilsang.feature.coupon.model

import androidx.compose.foundation.text.input.TextFieldState

sealed interface CouponUseUiState {
    data object Initial : CouponUseUiState
    data class ShowCoupon(val coupon: CouponUiModel) : CouponUseUiState
    data class PasswordVerify(
        val coupon: CouponUiModel,
        val password: TextFieldState
    ) : CouponUseUiState

    data object UseSuccess : CouponUseUiState
    data class WrongPassword(val coupon: CouponUiModel) : CouponUseUiState
}