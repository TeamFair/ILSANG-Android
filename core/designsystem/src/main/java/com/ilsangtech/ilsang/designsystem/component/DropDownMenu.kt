package com.ilsangtech.ilsang.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.toSp

@Composable
fun <T> DropDownMenu(
    modifier: Modifier = Modifier,
    list: List<T>,
    selectedItem: T,
    onItemSelected: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val unselectedList = list.filter { it != selectedItem }

    Column(modifier = modifier.width(150.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    shape = if (!expanded) {
                        RoundedCornerShape(8.dp)
                    } else {
                        RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                    }
                )
                .background(Color.White)
                .clickable(
                    onClick = { expanded = !expanded },
                    indication = null,
                    interactionSource = null
                )
                .padding(
                    vertical = 10.dp,
                    horizontal = 12.dp
                )
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = selectedItem.toString(),
                    style = dropDownMenuTextStyle()
                )
                Spacer(Modifier.width(10.dp))
                Icon(
                    painter = painterResource(
                        if (expanded) {
                            R.drawable.icon_under
                        } else {
                            R.drawable.icon_up
                        }
                    ),
                    tint = gray500,
                    contentDescription = null
                )
            }
        }
        AnimatedVisibility(visible = expanded) {
            Column {
                unselectedList.forEachIndexed { index, item ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                shape = if (index == unselectedList.lastIndex) {
                                    RoundedCornerShape(
                                        bottomStart = 8.dp,
                                        bottomEnd = 8.dp
                                    )
                                } else {
                                    RectangleShape
                                }
                            )
                            .background(Color.White)
                            .clickable(
                                onClick = {
                                    onItemSelected(item)
                                    expanded = !expanded
                                },
                                indication = null,
                                interactionSource = null
                            )
                            .padding(
                                vertical = 10.dp,
                                horizontal = 12.dp
                            )
                    ) {
                        Text(
                            text = item.toString(),
                            style = dropDownMenuTextStyle()
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun dropDownMenuTextStyle(): TextStyle {
    return TextStyle(
        fontFamily = pretendardFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 15.dp.toSp(),
        lineHeight = 1.3.em,
        color = gray500
    )
}

@Preview
@Composable
private fun DropDownMenuPreview() {
    val items = listOf("Item 1", "Item 2", "Item 3")
    var selectedItem by remember { mutableStateOf(items[0]) }

    DropDownMenu(
        list = items,
        selectedItem = selectedItem,
        onItemSelected = { selectedItem = it }
    )
}