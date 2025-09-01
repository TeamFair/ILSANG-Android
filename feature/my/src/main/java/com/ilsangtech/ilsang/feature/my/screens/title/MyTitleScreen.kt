package com.ilsangtech.ilsang.feature.my.screens.title

import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.LaunchedEffect
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
import com.ilsangtech.ilsang.core.model.title.Title
import com.ilsangtech.ilsang.core.model.title.TitleGrade
import com.ilsangtech.ilsang.core.model.title.TitleType
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.toSp
import com.ilsangtech.ilsang.feature.my.R
import com.ilsangtech.ilsang.feature.my.screens.title.component.MyTitleHeader
import com.ilsangtech.ilsang.feature.my.screens.title.component.MyTitleUpdateDialog
import com.ilsangtech.ilsang.feature.my.screens.title.component.TitleTypeChipRow
import com.ilsangtech.ilsang.feature.my.screens.title.component.TypeTitleListHeader
import com.ilsangtech.ilsang.feature.my.screens.title.component.typeTitleList
import com.ilsangtech.ilsang.feature.my.screens.title.model.MyTitleScreenUiState
import com.ilsangtech.ilsang.feature.my.screens.title.model.MyTitleUiModel

@Composable
internal fun MyTitleScreen(
    myTitleViewModel: MyTitleViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit
) {
    val uiState by myTitleViewModel.myTitleUiState.collectAsStateWithLifecycle()
    val selectedTitle by myTitleViewModel.selectedTitle.collectAsStateWithLifecycle()
    val isTitleUpdated by myTitleViewModel.isTitleUpdated.collectAsStateWithLifecycle()

    var selectedType by remember { mutableStateOf<TitleGrade>(TitleGrade.Standard) }
    var showUpdateDialog by remember { mutableStateOf(false) }

    LaunchedEffect(isTitleUpdated) {
        if (isTitleUpdated) {
            onBackButtonClick()
        }
    }

    BackHandler(
        enabled = myTitleViewModel.originTitleHistoryId != selectedTitle?.titleHistoryId,
        onBack = { showUpdateDialog = true }
    )

    if (showUpdateDialog) {
        MyTitleUpdateDialog(
            onUpdateButtonClick = myTitleViewModel::updateUserTitle,
            onDismissRequest = { showUpdateDialog = false }
        )
    }

    MyTitleScreen(
        uiState = uiState,
        selectedTitleGrade = selectedType,
        selectedTitle = selectedTitle,
        onTypeChipClick = { selectedType = it },
        onBackButtonClick = {
            if (myTitleViewModel.originTitleHistoryId == null) {
                onBackButtonClick()
            } else {
                showUpdateDialog = true
            }
        },
        onTitleSelect = myTitleViewModel::selectTitle
    )
}

@Composable
private fun MyTitleScreen(
    uiState: MyTitleScreenUiState,
    selectedTitleGrade: TitleGrade,
    selectedTitle: MyTitleUiModel?,
    onBackButtonClick: () -> Unit,
    onTypeChipClick: (TitleGrade) -> Unit,
    onTitleSelect: (MyTitleUiModel) -> Unit
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
                        selectedType = selectedTitleGrade,
                        onChipClick = onTypeChipClick
                    )
                }
                item {
                    TypeTitleListHeader(
                        modifier = Modifier.padding(vertical = 16.dp),
                        selectedGrade = selectedTitleGrade
                    )
                }
                if (uiState is MyTitleScreenUiState.Success) {
                    typeTitleList(
                        titleList = uiState.titleList.filter {
                            it.title.grade == selectedTitleGrade
                        },
                        selectedTitle = selectedTitle,
                        onTitleSelect = onTitleSelect
                    )
                }
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

@Preview
@Composable
private fun MyTitleScreenPreview() {
    val sampleTitles = listOf(
        MyTitleUiModel(
            titleHistoryId = 1,
            title = Title(name = "초보 탐험가", grade = TitleGrade.Standard, type = TitleType.Metro),
            condition = "지하철역 10회 방문"
        ),
        MyTitleUiModel(
            titleHistoryId = 2,
            title = Title(name = "상권 분석가", grade = TitleGrade.Rare, type = TitleType.Commercial),
            condition = "상권 5곳 방문"
        ),
        MyTitleUiModel(
            titleHistoryId = 3,
            title = Title(
                name = "도시 기여자",
                grade = TitleGrade.Legend,
                type = TitleType.Contribution
            ),
            condition = "장소 100곳 제보"
        )
    )
    MyTitleScreen(
        uiState = MyTitleScreenUiState.Success(titleList = sampleTitles),
        selectedTitleGrade = TitleGrade.Standard,
        selectedTitle = sampleTitles.first(),
        onBackButtonClick = {},
        onTypeChipClick = {},
        onTitleSelect = {}
    )
}