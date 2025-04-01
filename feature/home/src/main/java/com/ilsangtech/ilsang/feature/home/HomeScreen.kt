package com.ilsangtech.ilsang.feature.home

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import com.ilsangtech.ilsang.designsystem.component.ILSANGNavigationBar
import com.ilsangtech.ilsang.designsystem.component.ILSANGNavigationBarItem
import com.ilsangtech.ilsang.feature.home.home.HomeTapScreen

@Composable
fun HomeScreen() {
    val homeTaps = HomeTap.entries
    var selectedTap by remember { mutableStateOf(homeTaps.first()) }

    Scaffold(
        bottomBar = {
            HomeBottomBar(
                selectedTap = selectedTap,
                onTabSelected = { selectedTap = it }
            )
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(
            start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
            end = paddingValues.calculateEndPadding(LayoutDirection.Ltr),
            bottom = paddingValues.calculateBottomPadding()
        )) {
            if (selectedTap == HomeTap.Home) {
                HomeTapScreen()
            } else {
                // 다른 탭에 대한 UI
            }
        }
    }
}

@Composable
fun HomeBottomBar(
    selectedTap: HomeTap,
    onTabSelected: (HomeTap) -> Unit
) {
    val homeTaps = HomeTap.entries
    ILSANGNavigationBar {
        homeTaps.forEach { tap ->
            ILSANGNavigationBarItem(
                selected = selectedTap == tap,
                icon = {
                    Icon(
                        painter = painterResource(id = tap.defaultIconRes),
                        tint = Color.Unspecified,
                        contentDescription = tap.title,
                    )
                },
                selectedIcon = {
                    Icon(
                        painter = painterResource(id = tap.selectedIconRes),
                        tint = Color.Unspecified,
                        contentDescription = tap.title,
                    )
                },
                label = tap.title,
                onClick = { onTabSelected(tap) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}