package com.ilsangtech.ilsang.feature.home.quest

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.core.model.QuestType
import com.ilsangtech.ilsang.core.model.RepeatQuestPeriod
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_regular
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.feature.home.R

@Composable
fun SortTypeMenuContent(
    modifier: Modifier,
    questType: QuestType,
    selectedRepeatPeriod: RepeatQuestPeriod,
    selectedSortType: String,
    onSelectRepeatPeriod: (RepeatQuestPeriod) -> Unit,
    onSelectSortType: (String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                end = 20.dp,
                top = 8.dp,
                bottom = 16.dp
            ),
        horizontalArrangement = Arrangement.End
    ) {
        if (questType == QuestType.REPEAT) {
            RepeatQuestSortTypeMenu(
                repeatQuestPeriod = selectedRepeatPeriod,
                onSelectRepeatPeriod = onSelectRepeatPeriod
            )
        }
        Spacer(Modifier.width(8.dp))
        QuestSortTypeMenu(
            selectedSortType = selectedSortType,
            onSelectSortType = onSelectSortType
        )
    }
}

@Composable
fun RepeatQuestSortTypeMenu(
    repeatQuestPeriod: RepeatQuestPeriod,
    onSelectRepeatPeriod: (RepeatQuestPeriod) -> Unit
) {
    val questTypeList = remember { listOf("일간", "주간", "월간") }
    val selectedTitle = when (repeatQuestPeriod) {
        RepeatQuestPeriod.DAILY -> "일간"
        RepeatQuestPeriod.WEEKLY -> "주간"
        RepeatQuestPeriod.MONTHLY -> "월간"
    }

    DropDownMenu(
        modifier = Modifier,
        width = 85.dp,
        titleList = questTypeList,
        selectedTitle = selectedTitle,
        onTitleSelected = { title ->
            when (title) {
                "일간" -> onSelectRepeatPeriod(RepeatQuestPeriod.DAILY)
                "주간" -> onSelectRepeatPeriod(RepeatQuestPeriod.WEEKLY)
                "월간" -> onSelectRepeatPeriod(RepeatQuestPeriod.MONTHLY)
            }
        }
    )
}

@Composable
fun QuestSortTypeMenu(
    selectedSortType: String,
    onSelectSortType: (String) -> Unit
) {
    val questTypeList = remember { listOf("포인트 높은 순", "포인트 낮은 순", "인기순") }
    DropDownMenu(
        modifier = Modifier,
        width = 150.dp,
        titleList = questTypeList,
        selectedTitle = selectedSortType,
        onTitleSelected = onSelectSortType
    )
}

@Composable
fun DropDownMenu(
    modifier: Modifier,
    width: Dp,
    titleList: List<String>,
    selectedTitle: String,
    onTitleSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .width(width)
                .clickable(
                    interactionSource = null,
                    indication = null,
                    onClick = { expanded = !expanded }
                )
                .background(
                    color = Color.White,
                    shape = if (!expanded) {
                        RoundedCornerShape(8.dp)
                    } else {
                        RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp
                        )
                    }
                )
                .padding(
                    horizontal = 12.dp,
                    vertical = 10.dp
                )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedTitle,
                    style = dropdownMenuTitleStyle
                )
                Spacer(Modifier.weight(1f))
                Icon(
                    painter = painterResource(
                        if (!expanded) {
                            R.drawable.dropdown_menu_down
                        } else {
                            R.drawable.dropdown_menu_up
                        }
                    ),
                    contentDescription = null
                )
            }
        }
        if (expanded) {
            titleList.filter { title -> title != selectedTitle }
                .forEachIndexed { index, title ->
                    HorizontalDivider(
                        modifier = Modifier.width(width),
                        color = gray100,
                        thickness = 0.4.dp
                    )
                    Box(
                        modifier = modifier
                            .width(width)
                            .clickable(
                                interactionSource = null,
                                indication = null,
                                onClick = {
                                    expanded = !expanded
                                    onTitleSelected(title)
                                }
                            )
                            .background(
                                color = Color.White,
                                shape = if (index != titleList.size - 2) {
                                    RectangleShape
                                } else {
                                    RoundedCornerShape(
                                        bottomStart = 8.dp,
                                        bottomEnd = 8.dp
                                    )
                                }
                            )
                            .padding(
                                horizontal = 12.dp,
                                vertical = 10.dp
                            )
                    ) {
                        Text(
                            text = title,
                            style = dropdownMenuTitleStyle
                        )
                    }
                }
        }
    }
}

private val dropdownMenuTitleStyle = TextStyle(
    fontFamily = FontFamily(Font(pretendard_regular)),
    color = gray500,
    fontSize = 15.sp,
    lineHeight = 1.3.em
)


@Preview(
    showBackground = true,
    backgroundColor = 0xF6F6F6,
    heightDp = 200
)
@Composable
fun SortTypeMenuContentPreview() {
    SortTypeMenuContent(
        modifier = Modifier,
        questType = QuestType.REPEAT,
        selectedRepeatPeriod = RepeatQuestPeriod.DAILY,
        selectedSortType = "최신순",
        onSelectRepeatPeriod = {},
        onSelectSortType = {}
    )
}