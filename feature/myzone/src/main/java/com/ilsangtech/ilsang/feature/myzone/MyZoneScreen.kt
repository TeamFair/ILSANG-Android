package com.ilsangtech.ilsang.feature.myzone

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.ilsangtech.ilsang.feature.myzone.component.MyZoneHeader

@Composable
fun MyZoneScreen(onBackButtonClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column {
            MyZoneHeader(onBackButtonClick = onBackButtonClick)
        }
    }
}

@Preview
@Composable
private fun MyZoneScreenPreview() {
    MyZoneScreen {}
}