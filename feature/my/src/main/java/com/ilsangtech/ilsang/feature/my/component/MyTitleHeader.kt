package com.ilsangtech.ilsang.feature.my.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ilsangtech.ilsang.designsystem.theme.caption01
import com.ilsangtech.ilsang.designsystem.theme.caption02
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.heading02
import com.ilsangtech.ilsang.designsystem.theme.heading03
import com.ilsangtech.ilsang.designsystem.theme.title01
import com.ilsangtech.ilsang.designsystem.theme.toSp
import com.ilsangtech.ilsang.feature.my.R

@Composable
internal fun MyTitleHeader(
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        MyTitleInfoDialog(onDismissRequest = { showDialog = false })
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "칭호",
                style = title01,
                color = Color.Black
            )
            Text(
                text = "퀘스트를 수행하면서 칭호를 얻을 수 있어요!",
                style = caption02,
                color = gray400
            )
        }
        Icon(
            modifier = Modifier
                .clickable(
                    onClick = { showDialog = true },
                    indication = null,
                    interactionSource = null
                )
                .padding(5.dp)
                .size(20.dp),
            painter = painterResource(id = R.drawable.icon_info),
            contentDescription = "칭호 정보"
        )
    }
}

@Composable
private fun MyTitleInfoDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .clickable(
                            onClick = onDismissRequest,
                            indication = null,
                            interactionSource = null
                        ),
                    painter = painterResource(id = R.drawable.icon_close),
                    contentDescription = "닫기",
                    tint = gray400
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter),
                    verticalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = "칭호",
                            style = heading03,
                            fontSize = 19.sp,
                            lineHeight = 23.sp,
                            color = Color.Black
                        )
                        Text(
                            text = "퀘스트를 수행하면 칭호가 쌓여요!",
                            style = caption02,
                            color = gray400
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                            .padding(bottom = 16.dp)
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TitleTypeInfoItem(
                            painter = painterResource(R.drawable.icon_normal_title),
                            type = "일반",
                            description = "가장 일반적인 칭호"
                        )
                        VerticalDivider(
                            modifier = Modifier.fillMaxHeight(),
                            color = gray100
                        )
                        TitleTypeInfoItem(
                            painter = painterResource(R.drawable.icon_rare_title),
                            type = "희귀",
                            description = "가장 희귀한 칭호"
                        )
                        VerticalDivider(
                            modifier = Modifier.fillMaxHeight(),
                            color = gray100
                        )
                        TitleTypeInfoItem(
                            painter = painterResource(R.drawable.icon_legend_title),
                            type = "전설",
                            description = "가장 전설적인 칭호"
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TitleTypeInfoItem(
    modifier: Modifier = Modifier,
    painter: Painter,
    type: String,
    description: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        Icon(
            painter = painter,
            contentDescription = type,
            tint = Color.Unspecified
        )
        Text(
            text = type,
            style = heading02,
            fontSize = 17.dp.toSp(),
            lineHeight = 20.dp.toSp(),
            color = Color.Black
        )
        Text(
            text = description,
            style = caption01,
            fontSize = 13.dp.toSp(),
            lineHeight = 20.dp.toSp(),
            color = gray400
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun MyTitleHeaderPreview() {
    MyTitleHeader()
}

@Composable
@Preview(showBackground = true)
private fun MyTitleInfoDialogPreview() {
    MyTitleInfoDialog { }
}