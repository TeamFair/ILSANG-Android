package com.ilsangtech.ilsang.feature.home.my.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.toSp
import com.ilsangtech.ilsang.feature.home.R

@Composable
internal fun WithdrawalButton(
    modifier: Modifier = Modifier,
    onWithdrawalButtonClick: () -> Unit
) {
    var isChecked by remember { mutableStateOf(false) }
    Column(modifier = modifier.navigationBarsPadding()) {
        Box(
            modifier = Modifier
                .clickable(
                    indication = null,
                    interactionSource = null,
                    onClick = { isChecked = !isChecked }
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clickable(
                            indication = null,
                            interactionSource = null,
                            onClick = { isChecked = !isChecked }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = if (isChecked) {
                            painterResource(id = R.drawable.icon_checked)
                        } else {
                            painterResource(
                                id = R.drawable.icon_unchecked
                            )
                        },
                        tint = Color.Unspecified,
                        contentDescription = null,
                    )
                }
                Spacer(Modifier.width(2.dp))
                Text(
                    text = "안내사항을 모두 확인하였으며, 이에 동의합니다.",
                    style = TextStyle(
                        fontFamily = pretendardFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.dp.toSp(),
                        color = gray500
                    )
                )
            }
        }
        Spacer(Modifier.height(12.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = isChecked,
            onClick = onWithdrawalButtonClick,
            contentPadding = PaddingValues(vertical = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primary,
                disabledContainerColor = gray300
            )
        ) {
            Text(
                text = "탈퇴하기",
                style = TextStyle(
                    fontFamily = pretendardFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.dp.toSp(),
                    color = Color.White
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WithdrawalButtonPreview() {
    WithdrawalButton {}
}