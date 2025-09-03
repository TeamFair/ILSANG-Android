package com.ilsangtech.ilsang.feature.coupon.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ilsangtech.ilsang.feature.coupon.CouponScreen
import kotlinx.serialization.Serializable

@Serializable
data object CouponBaseRoute

@Serializable
data object CouponRoute

fun NavGraphBuilder.couponNavigation(
    navigateToHome: () -> Unit,
    popBackStack: () -> Unit
) {
    navigation<CouponBaseRoute>(startDestination = CouponRoute) {
        composable<CouponRoute> {
            CouponScreen(
                onQuestNavButtonClick = navigateToHome,
                onBackButtonClick = popBackStack
            )
        }
    }
}