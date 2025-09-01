package com.ilsangtech.ilsang.feature.my.screens.title.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.designsystem.theme.caption02
import com.ilsangtech.ilsang.designsystem.theme.gray200
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.title02
import com.ilsangtech.ilsang.designsystem.theme.toSp
import com.ilsangtech.ilsang.feature.my.R

@Composable
internal fun MyTitleCard(
    modifier: Modifier = Modifier,
    titleName: String?,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = onClick
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.padding(
                    top = 16.dp,
                    start = 20.dp,
                    end = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp)

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = "내 칭호",
                        style = caption02,
                        color = gray400
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.icon_chevron_next),
                        tint = gray400,
                        contentDescription = null
                    )
                }

                titleName?.let {
                    Text(
                        text = titleName,
                        style = title02.copy(lineBreak = LineBreak.Heading),
                        color = gray500,
                        fontSize = 17.dp.toSp(),
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            if (titleName != null) {
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(
                            bottom = 16.dp,
                            end = 16.dp
                        ),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_normal_title),
                        tint = Color.Unspecified,
                        contentDescription = null
                    )
                    Icon(
                        painter = painterResource(R.drawable.icon_rare_title),
                        tint = Color.Unspecified,
                        contentDescription = null
                    )
                    Icon(
                        painter = painterResource(R.drawable.icon_legend_title),
                        tint = Color.Unspecified,
                        contentDescription = null
                    )
                }
            } else {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "칭호가 없어요!",
                        style = title02,
                        color = gray300,
                        fontSize = 17.dp.toSp()
                    )
                    Text(
                        text = "퀘스트 수행으로 \n" +
                                "칭호를 획득해 보세요",
                        style = caption02,
                        color = gray200,
                        fontSize = 12.dp.toSp(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
@Preview(widthDp = 169)
private fun MyTitleCardPreview1() {
    MyTitleCard(titleName = null) { }
}

@Composable
@Preview(widthDp = 169)
private fun MyTitleCardPreview2() {
    MyTitleCard(titleName = "세상을 움직이는 자") { }
}