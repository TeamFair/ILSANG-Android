package com.ilsangtech.ilsang.feature.coupon

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.ilsangtech.ilsang.core.domain.CouponRepository
import com.ilsangtech.ilsang.core.model.coupon.Coupon
import com.ilsangtech.ilsang.feature.coupon.model.CouponTab
import com.ilsangtech.ilsang.feature.coupon.model.CouponUiModel
import com.ilsangtech.ilsang.feature.coupon.model.CouponUseUiState
import com.ilsangtech.ilsang.feature.coupon.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CouponViewModel @Inject constructor(
    private val couponRepository: CouponRepository
) : ViewModel() {
    private val _selectedCouponTab = MutableStateFlow(CouponTab.Available)
    val selectedCouponTab = _selectedCouponTab.asStateFlow()

    private val couponList = couponRepository.getCouponList()
        .cachedIn(viewModelScope)
    val availableCouponList = couponList.map { pagingData ->
        pagingData.map(Coupon::toUiModel).filter { it.isAvailable }
    }
    val usedOrExpiredCouponList = couponList.map { pagingData ->
        pagingData.map(Coupon::toUiModel).filter { !it.isAvailable }
    }

    private val _couponUseUiState = MutableStateFlow<CouponUseUiState>(CouponUseUiState.Initial)
    val couponUseUiState = _couponUseUiState.asStateFlow()

    val couponPassword = TextFieldState()
    fun updateSelectedCouponTab(tab: CouponTab) {
        _selectedCouponTab.update { tab }
    }

    fun updateCouponUseUiState(state: CouponUseUiState) {
        couponPassword.clearText()
        _couponUseUiState.update { state }
    }

    fun verifyCouponPassword(coupon: CouponUiModel) {
        viewModelScope.launch {
            val id = coupon.id
            couponRepository.verifyCouponPassword(
                couponId = id,
                password = couponPassword.text.toString()
            ).onSuccess {
                couponRepository.useCoupon(id)
                    .onSuccess {
                        _couponUseUiState.update { CouponUseUiState.UseSuccess }
                    }.onFailure {
                        _couponUseUiState.update { CouponUseUiState.WrongPassword(coupon) }
                    }
            }.onFailure {
                _couponUseUiState.update { CouponUseUiState.WrongPassword(coupon) }
            }
        }
    }
}