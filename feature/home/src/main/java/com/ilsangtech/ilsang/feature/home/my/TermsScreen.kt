package com.ilsangtech.ilsang.feature.home.my

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.createBitmap
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.feature.home.R
import java.io.File
import java.io.FileOutputStream

@Composable
internal fun TermsScreen(
    onBackButtonClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = background
    ) {
        Column {
            TermsScreenHeader(onBackButtonClick = onBackButtonClick)
            LazyColumn {
                item {
                    TermsItem(
                        title = "개인정보 처리방침",
                        resId = R.raw.privacy_policy_2503
                    )
                }
                item {
                    TermsItem(
                        title = "서비스 이용약관",
                        resId = R.raw.terms_of_use_2406
                    )
                }
            }
        }
    }
}

@Composable
private fun TermsItem(
    title: String,
    resId: Int
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Column {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            onClick = { expanded = !expanded }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 18.dp,
                        horizontal = 25.dp
                    )
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontFamily = pretendardFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = gray500
                    )
                )
                Icon(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    painter = painterResource(
                        id = if (expanded) {
                            R.drawable.dropdown_menu_down
                        } else {
                            R.drawable.dropdown_menu_up
                        }
                    ),
                    tint = Color.Unspecified,
                    contentDescription = null
                )
            }
        }

        AnimatedVisibility(expanded) {
            PdfViewer(resId)
        }
    }
}

@Composable
private fun PdfViewer(resId: Int) {
    val context = LocalContext.current
    val pdfFile = remember { copyPdfFromRawToCache(context, resId, "sample_$resId.pdf") }

    val renderer by remember {
        mutableStateOf(
            PdfRenderer(ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY))
        )
    }

    var pageIndex by remember { mutableIntStateOf(0) }
    val pageCount = renderer.pageCount

    val bitmap by produceState<Bitmap?>(initialValue = null, pageIndex) {
        val page = renderer.openPage(pageIndex)
        val bmp = createBitmap(page.width, page.height)
        page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        page.close()
        value = bmp
    }

    val pagerState = rememberPagerState { pageCount }
    HorizontalPager(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White),
        state = pagerState
    ) { page ->
        pageIndex = page
        bitmap?.let {
            Image(
                modifier = Modifier.fillMaxWidth(),
                bitmap = it.asImageBitmap(),
                contentDescription = "PDF Page",
                contentScale = ContentScale.FillWidth
            )
        } ?: Text("Loading...", modifier = Modifier.fillMaxWidth())
    }

    DisposableEffect(Unit) {
        onDispose { pdfFile.delete() }
    }
}

private fun copyPdfFromRawToCache(context: Context, resId: Int, fileName: String): File {
    val file = File(context.cacheDir, fileName)
    if (!file.exists()) {
        context.resources.openRawResource(resId).use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        }
    }
    return file
}

@Composable
private fun TermsScreenHeader(
    onBackButtonClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .statusBarsPadding()
            .padding(vertical = 12.dp),
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp)
                .clickable(
                    onClick = onBackButtonClick,
                    interactionSource = null,
                    indication = null
                ),
            painter = painterResource(R.drawable.back),
            tint = Color.Unspecified,
            contentDescription = null
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "약관 및 정책",
            style = TextStyle(
                fontFamily = pretendardFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = gray500
            )
        )
    }
}