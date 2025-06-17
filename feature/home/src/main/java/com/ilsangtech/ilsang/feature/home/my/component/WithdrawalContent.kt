package com.ilsangtech.ilsang.feature.home.my.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.toSp

@Composable
internal fun WithdrawalContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "\uD83D\uDEA8",
            style = TextStyle(
                fontFamily = pretendardFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 50.dp.toSp()
            )
        )
        Spacer(Modifier.height(18.dp))
        Text(
            text = "일상 탈퇴 전 확인하세요!",
            style = TextStyle(
                fontFamily = pretendardFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 23.dp.toSp(),
                color = gray500
            )
        )
        Spacer(Modifier.height(11.dp))
        Text(
            text = "탈퇴하시면 모든 데이터는 복구가 불가능합니다.",
            style = TextStyle(
                fontFamily = pretendardFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 15.dp.toSp(),
                color = gray500
            )
        )
        Spacer(Modifier.height(28.dp))
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = background
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 15.dp)
                    .padding(
                        start = 9.dp,
                        end = 24.dp
                    )
            ) {
                Text(
                    text = "· 진행 및 완료된 모든 퀘스트 내용이 삭제됩니다.\n" +
                            "· 경험치 및 레벨은 복구가 불가합니다.",
                    style = TextStyle(
                        fontFamily = pretendardFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.dp.toSp(),
                        color = gray500
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WithdrawalContentPreview() {
    WithdrawalContent()
}