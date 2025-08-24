package com.ilsangtech.ilsang.feature.quest.component

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
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_regular
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.toSp
import com.ilsangtech.ilsang.feature.quest.R
import com.ilsangtech.ilsang.feature.quest.model.QuestTabUiModel
import com.ilsangtech.ilsang.feature.quest.model.RepeatQuestTypeUiModel
import com.ilsangtech.ilsang.feature.quest.model.SortTypeUiModel

@Composable
internal fun SortTypeMenuContent(
    modifier: Modifier = Modifier,
    questTab: QuestTabUiModel,
    selectedRepeatType: RepeatQuestTypeUiModel?,
    selectedSortType: SortTypeUiModel,
    onSelectRepeatType: (RepeatQuestTypeUiModel) -> Unit,
    onSelectSortType: (SortTypeUiModel) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(end = 20.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.End
    ) {
        if (questTab == QuestTabUiModel.REPEAT && selectedRepeatType != null) {
            RepeatQuestSortTypeMenu(
                repeatType = selectedRepeatType,
                onSelectRepeatType = onSelectRepeatType
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
private fun RepeatQuestSortTypeMenu(
    repeatType: RepeatQuestTypeUiModel,
    onSelectRepeatType: (RepeatQuestTypeUiModel) -> Unit
) {
    QuestFilterDropDownMenu(
        width = 85.dp,
        typeList = RepeatQuestTypeUiModel.entries,
        selectedType = repeatType,
        onTypeSelected = onSelectRepeatType
    )
}

@Composable
private fun QuestSortTypeMenu(
    selectedSortType: SortTypeUiModel,
    onSelectSortType: (SortTypeUiModel) -> Unit
) {
    QuestFilterDropDownMenu(
        width = 150.dp,
        typeList = SortTypeUiModel.entries,
        selectedType = selectedSortType,
        onTypeSelected = onSelectSortType
    )
}

@Composable
private fun <T> QuestFilterDropDownMenu(
    modifier: Modifier = Modifier,
    width: Dp,
    typeList: List<T>,
    selectedType: T,
    onTypeSelected: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
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
                    text = selectedType.toString(),
                    style = dropdownMenuTitleStyle
                )
                Spacer(Modifier.weight(1f))
                Icon(
                    painter = painterResource(
                        if (!expanded) {
                            R.drawable.dropdown_menu_up
                        } else {
                            R.drawable.dropdown_menu_down
                        }
                    ),
                    contentDescription = null
                )
            }
        }
        if (expanded) {
            typeList.filter { type -> type != selectedType }
                .forEachIndexed { index, type ->
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
                                    onTypeSelected(type)
                                }
                            )
                            .background(
                                color = Color.White,
                                shape = if (index != typeList.size - 2) {
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
                            text = type.toString(),
                            style = dropdownMenuTitleStyle.copy(fontSize = 15.dp.toSp())
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
private fun SortTypeMenuContentPreview() {
    SortTypeMenuContent(
        modifier = Modifier,
        questTab = QuestTabUiModel.REPEAT,
        selectedRepeatType = RepeatQuestTypeUiModel.Daily,
        selectedSortType = SortTypeUiModel.PointDesc,
        onSelectRepeatType = {},
        onSelectSortType = {}
    )
}