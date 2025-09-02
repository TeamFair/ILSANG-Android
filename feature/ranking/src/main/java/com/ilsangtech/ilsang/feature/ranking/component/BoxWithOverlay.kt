package com.ilsangtech.ilsang.feature.ranking.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.SubcomposeLayout


@Composable
internal fun BoxWithOverlay(
    modifier: Modifier = Modifier,
    overlay: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    SubcomposeLayout(modifier) { constraints ->
        val overlayMeasurables = subcompose("overlay", overlay)
        val overlayPlaceables = overlayMeasurables.map {
            it.measure(constraints.copy(minWidth = 0, minHeight = 0))
        }
        val overlayHeight = overlayPlaceables.maxOfOrNull { it.height } ?: 0

        val contentMeasurables = subcompose("content") {
            content(PaddingValues(bottom = overlayHeight.toDp()))
        }
        val contentPlaceables = contentMeasurables.map { it.measure(constraints) }

        val width = constraints.maxWidth
        val height = constraints.maxHeight

        layout(width, height) {
            contentPlaceables.forEach { it.placeRelative(0, 0) }
            overlayPlaceables.forEach {
                it.placeRelative(
                    x = (width - it.width) / 2,
                    y = height - it.height
                )
            }
        }
    }
}