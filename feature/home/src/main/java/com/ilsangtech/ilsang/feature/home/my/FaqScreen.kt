package com.ilsangtech.ilsang.feature.home.my

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.toSp
import com.ilsangtech.ilsang.feature.home.R
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Composable
internal fun FaqScreen(
    onBackButtonClick: () -> Unit
) {
    val context = LocalContext.current
    val faqList = remember {
        context.resources.openRawResource(R.raw.setting_faq_list).use { inputStream ->
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            Json.decodeFromString<List<FaqModel>>(jsonString)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = background
    ) {
        Column {
            FaqScreenHeader(onBackButtonClick = onBackButtonClick)
            LazyColumn {
                items(
                    items = faqList,
                    key = { it.question }
                ) { faqItem ->
                    FaqItem(faqModel = faqItem)
                }
                item { Spacer(Modifier.navigationBarsPadding()) }
            }
        }
    }
}

@Composable
private fun FaqItem(faqModel: FaqModel) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    Column {
        Surface(
            onClick = { isExpanded = !isExpanded },
            color = Color.White
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
                    modifier = Modifier.align(Alignment.CenterStart),
                    text = faqModel.question,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontFamily = pretendardFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.dp.toSp(),
                        color = gray500
                    )
                )

                Icon(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    painter = painterResource(
                        id = if (isExpanded) {
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

        AnimatedVisibility(isExpanded) {
            Text(
                modifier = Modifier.padding(
                    vertical = 24.dp,
                    horizontal = 20.dp
                ),
                text = faqModel.answer,
                style = TextStyle(
                    fontFamily = pretendardFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.dp.toSp(),
                    lineHeight = 1.6.em,
                    color = gray500
                )
            )
        }
    }
}

@Composable
private fun FaqScreenHeader(
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
                    indication = null,
                    interactionSource = null
                ),
            painter = painterResource(id = R.drawable.back),
            contentDescription = null
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "자주 물어보는 질문",
            style = TextStyle(
                fontFamily = pretendardFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = gray500
            )
        )
    }
}

@Serializable
private data class FaqModel(
    val question: String,
    val answer: String
)