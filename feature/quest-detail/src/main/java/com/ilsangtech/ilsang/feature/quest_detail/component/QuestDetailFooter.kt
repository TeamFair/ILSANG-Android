package com.ilsangtech.ilsang.feature.quest_detail.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.buttonTextStyle
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.primary300
import com.ilsangtech.ilsang.designsystem.theme.tapRegularTextStyle
import com.ilsangtech.ilsang.designsystem.theme.toSp

@Composable
internal fun QuestDetailFooter(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onApprovalClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(13.dp)
    ) {
        Text(
            text = "퀘스트를 수행하고\n인증 후, 포인트를 적립받으세요",
            style = TextStyle(
                fontSize = 14.dp.toSp(),
                lineHeight = 22.dp.toSp()
            ),
            color = gray500,
            textAlign = TextAlign.Center
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.clickable(
                    onClick = onFavoriteClick,
                    indication = null,
                    interactionSource = null
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_favorite),
                    tint = if (isFavorite) primary300 else gray100,
                    contentDescription = "즐겨찾기",
                )
                Text(
                    text = "즐겨찾기",
                    style = tapRegularTextStyle.copy(
                        fontSize = 14.dp.toSp(),
                        lineHeight = 24.dp.toSp()
                    ),
                    color = gray400
                )
            }
            Button(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary,
                    disabledContainerColor = gray300,
                    contentColor = Color.White,
                    disabledContentColor = Color.White
                ),
                contentPadding = PaddingValues(vertical = 16.dp),
                onClick = onApprovalClick
            ) {
                Text(
                    text = "퀘스트 인증하기",
                    style = buttonTextStyle.copy(
                        fontSize = 16.dp.toSp(),
                        lineHeight = 18.dp.toSp()
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun QuestDetailFooterPreview() {
    QuestDetailFooter(
        isFavorite = false,
        onFavoriteClick = {},
        onApprovalClick = {}
    )
}