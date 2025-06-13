package com.ilsangtech.ilsang.feature.profile.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.core.util.XpLevelCalculator
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.primary500
import java.util.Locale

@Composable
internal fun UserInfoContent(
    modifier: Modifier = Modifier,
    nickname: String,
    xpPoint: Int
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(
                vertical = 18.dp,
                horizontal = 16.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(57.dp),
                tint = Color.Unspecified,
                painter = painterResource(R.drawable.default_user_profile),
                contentDescription = null
            )
            Spacer(Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = nickname,
                    style = TextStyle(
                        fontFamily = pretendardFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = gray500
                    )
                )
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "LV.${XpLevelCalculator.getCurrentLevel(xpPoint)}",
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontFamily = pretendardFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = primary500
                        )
                    )
                    Text(
                        text = "|",
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontFamily = pretendardFontFamily,
                            fontWeight = FontWeight.Normal,

                            color = gray100
                        )
                    )
                    Text(
                        text = String.format(
                            locale = Locale.KOREA,
                            format = "%,d",
                            xpPoint
                        ) + "XP",
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontFamily = pretendardFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = gray400
                        )
                    )
                }
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp),
                    progress = { XpLevelCalculator.getLevelProgress(xpPoint) },
                    color = primary,
                    trackColor = gray100,
                    gapSize = (-5).dp,
                    drawStopIndicator = {}
                )
            }
        }
    }
}

@Preview
@Composable
private fun UserInfoContentPreview() {
    UserInfoContent(
        nickname = "김일상123",
        xpPoint = 16300
    )
}

