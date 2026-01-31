package com.ilsangtech.ilsang.core.ui.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.ui.BuildConfig

@Composable
fun IlsangImage(
    modifier: Modifier = Modifier,
    imageId: String?,
    placeHolder: Painter? = null,
    error: Painter? = null,
    contentScale: ContentScale = ContentScale.Crop,
    alpha: Float = 1f,
    contentDescription: String? = null
) {
    AsyncImage(
        modifier = modifier,
        model = BuildConfig.IMAGE_URL + imageId,
        placeholder = placeHolder,
        error = error,
        contentScale = contentScale,
        alpha = alpha,
        contentDescription = contentDescription
    )
}