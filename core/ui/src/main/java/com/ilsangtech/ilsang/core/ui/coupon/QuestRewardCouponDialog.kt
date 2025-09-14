package com.ilsangtech.ilsang.core.ui.coupon

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.window.Dialog
import com.ilsangtech.ilsang.core.model.coupon.QuestDetailCoupon
import com.ilsangtech.ilsang.core.util.DateConverter
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.heading02
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.subTitle01
import com.ilsangtech.ilsang.designsystem.theme.tapRegularTextStyle
import com.ilsangtech.ilsang.designsystem.theme.title02

@Composable
internal fun QuestRewardCouponDialog(
    coupon: QuestDetailCoupon,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "이번 주 특별 보상",
                        style = heading02
                    )
                    Icon(
                        modifier = Modifier.clickable(
                            onClick = onDismissRequest,
                            indication = null,
                            interactionSource = null
                        ),
                        painter = painterResource(R.drawable.icon_close),
                        tint = gray500,
                        contentDescription = null
                    )
                }
                Icon(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(132.dp),
                    painter = painterResource(R.drawable.icon_coupon_with_background),
                    tint = Color.Unspecified,
                    contentDescription = null
                )
                Text(
                    text = coupon.name,
                    style = title02
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = coupon.storeName.orEmpty(),
                    style = subTitle01,
                    color = gray500
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = DateConverter.formatDate(
                        input = coupon.validTo,
                        outputPattern = "yyyy.MM.dd HH:mm"
                    ) + "까지",
                    style = tapRegularTextStyle,
                    color = gray400
                )
                Button(
                    modifier = Modifier
                        .padding(top = 48.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = primary),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    onClick = onDismissRequest
                ) {
                    Text(
                        text = "확인",
                        style = TextStyle(
                            fontFamily = pretendardFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            lineHeight = 18.sp
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun QuestRewardCouponDialogPreview() {
    val coupon = QuestDetailCoupon(
        id = 1,
        name = "아메리카노 1잔 무료",
        imageId = null,
        validTo = "2025-12-31T23:59:59",
        storeName = "스타벅스",
        description = "맛있는 아메리카노"
    )
    QuestRewardCouponDialog(
        coupon = coupon,
        onDismissRequest = {}
    )
}

