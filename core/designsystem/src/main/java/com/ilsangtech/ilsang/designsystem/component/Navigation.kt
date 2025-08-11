package com.ilsangtech.ilsang.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.ILSANGTheme
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.primary300

@Composable
fun IlsangNavigationBarItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit,
    label: String,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = modifier
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = interactionSource
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.size(32.dp),
            contentAlignment = Alignment.Center
        ) {
            if (selected) {
                selectedIcon()
            } else {
                icon()
            }
        }
        Text(
            text = label,
            style = TextStyle(
                fontSize = 11.dp.toSp(),
                lineHeight = 12.dp.toSp(),
                fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                color = if (selected) primary300 else gray300
            ),
        )
    }
}

@Composable
fun ILSANGNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        modifier = modifier,
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 8.dp
                )
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceAround,
            content = content
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun IlsangNavigationBarPreview() {
    val labels = listOf(
        "홈", "퀘스트", "인증", "랭킹", "마이"
    )
    val icons = listOf(
        R.drawable.home,
        R.drawable.quest,
        R.drawable.approval,
        R.drawable.ranking,
        R.drawable.my
    )
    val selectedIcons = listOf(
        R.drawable.selected_home,
        R.drawable.selected_quest,
        R.drawable.selected_approval,
        R.drawable.selected_ranking,
        R.drawable.selected_my
    )
    var selectedIndex by remember { mutableIntStateOf(0) }

    ILSANGTheme {
        Scaffold(
            bottomBar = {
                ILSANGNavigationBar {
                    labels.forEachIndexed { index, label ->
                        IlsangNavigationBarItem(
                            selected = selectedIndex == index,
                            icon = {
                                Icon(
                                    painter = painterResource(id = icons[index]),
                                    tint = Color.Unspecified,
                                    contentDescription = label,
                                )
                            },
                            selectedIcon = {
                                Icon(
                                    painter = painterResource(id = selectedIcons[index]),
                                    tint = Color.Unspecified,
                                    contentDescription = label,
                                )
                            },
                            label = label,
                            onClick = { selectedIndex = index }
                        )
                    }
                }
            }
        ) {}
    }
}

@Composable
private fun Dp.toSp() = with(LocalDensity.current) { this@toSp.toSp() }