package com.ilsangtech.ilsang.feature.coupon

import androidx.lifecycle.ViewModel
import com.ilsangtech.ilsang.feature.coupon.model.CouponTab
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CouponViewModel @Inject constructor() : ViewModel() {
    private val _selectedCouponTab = MutableStateFlow(CouponTab.Available)
    val selectedCouponTab = _selectedCouponTab.asStateFlow()

    fun updateSelectedCouponTab(tab: CouponTab) {
        _selectedCouponTab.update { tab }
    }
}