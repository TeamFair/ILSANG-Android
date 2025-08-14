package com.ilsangtech.ilsang.feature.banner.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ilsangtech.ilsang.feature.banner.BannerDetailScreen
import kotlinx.serialization.Serializable

@Serializable
data object BannerBaseRoute

@Serializable
data class BannerDetailRoute(
    val id: Int,
    val title: String,
    val description: String,
    val imageId: String
)

fun NavGraphBuilder.bannerNavigation(
    onBackButtonClick: () -> Unit
) {
    navigation<BannerBaseRoute>(startDestination = BannerDetailRoute::class) {
        composable<BannerDetailRoute> {
            BannerDetailScreen(onBackButtonClick = onBackButtonClick)
        }
    }
}