package com.ilsangtech.ilsang.feature.my

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ilsangtech.ilsang.core.model.Title
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.toSp
import com.ilsangtech.ilsang.feature.my.component.MyTitleHeader
import com.ilsangtech.ilsang.feature.my.component.TitleTypeChipRow
import com.ilsangtech.ilsang.feature.my.component.TypeTitleListHeader
import com.ilsangtech.ilsang.feature.my.component.typeTitleList

@Composable
internal fun MyTitleScreen(
    myTitleViewModel: MyTitleViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit
) {
    val titleList by myTitleViewModel.myTitleUiState.collectAsStateWithLifecycle()
    var selectedType by remember { mutableStateOf("STANDARD") }

    MyTitleScreen(
        titleList = titleList,
        selectedType = selectedType,
        onTypeChipClick = { selectedType = it },
        onBackButtonClick = onBackButtonClick
    )
}

@Composable
private fun MyTitleScreen(
    titleList: List<Title>,
    selectedType: String,
    onBackButtonClick: () -> Unit,
    onTypeChipClick: (String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = background
    ) {
        Column {
            MyTitleTopBar(onBackButtonClick = onBackButtonClick)
            LazyColumn(
                modifier = Modifier.padding(horizontal = 20.dp),
                contentPadding = PaddingValues(bottom = 72.dp)
            ) {
                item { MyTitleHeader(modifier = Modifier.padding(top = 24.dp)) }
                item { Spacer(Modifier.height(36.dp)) }
                item {
                    TitleTypeChipRow(
                        selectedType = selectedType,
                        onChipClick = onTypeChipClick
                    )
                }
                item {
                    TypeTitleListHeader(
                        modifier = Modifier.padding(vertical = 16.dp),
                        selectedType = selectedType
                    )
                }
                typeTitleList(
                    titleList = titleList.filter { it.type == selectedType }
                )
            }
        }
    }
}

@Composable
private fun MyTitleTopBar(
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(45.dp)
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .fillMaxHeight()
                .clickable(
                    onClick = onBackButtonClick,
                    indication = null,
                    interactionSource = null
                )
                .padding(horizontal = 15.dp),
            painter = painterResource(id = R.drawable.back),
            contentDescription = "뒤로 가기",
            tint = gray500
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "내 칭호",
            style = TextStyle(
                fontFamily = pretendardFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 17.dp.toSp(),
                lineHeight = 22.dp.toSp(),
                color = gray500
            )
        )
    }
}

@Composable
@Preview
private fun MyTitleScreenPreview() {
    MyTitleScreen(
        titleList = emptyList(),
        selectedType = "STANDARD",
        onBackButtonClick = {},
        onTypeChipClick = {}
    )
}