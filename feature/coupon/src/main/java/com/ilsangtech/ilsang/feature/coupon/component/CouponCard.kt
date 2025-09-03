package com.ilsangtech.ilsang.feature.coupon.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.badge01TextStyle
import com.ilsangtech.ilsang.designsystem.theme.bodyTextStyle
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.heading01
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.tapRegularTextStyle
import com.ilsangtech.ilsang.feature.coupon.model.CouponUiModel

@Composable
internal fun AvailableCouponCard(
    modifier: Modifier = Modifier,
    coupon: CouponUiModel,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = coupon.title,
                    style = heading01,
                    color = primary
                )
                Text(
                    text = coupon.writerName,
                    style = bodyTextStyle,
                    color = gray500
                )
                Text(
                    text = coupon.expireDate + " 까지",
                    style = tapRegularTextStyle,
                    color = gray400
                )
            }
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color(0xFFF4F4F4))
                    .padding(5.dp),
                painter = painterResource(R.drawable.icon_card_arrow_right),
                contentDescription = "쿠폰 사용하기",
                tint = gray500
            )
        }
    }
}

@Composable
internal fun UsedOrExpiredCouponCard(
    modifier: Modifier = Modifier,
    coupon: CouponUiModel
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(
            modifier = Modifier.padding(vertical = 20.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = coupon.title,
                style = heading01,
                color = gray400
            )
            Text(
                text = coupon.writerName,
                style = bodyTextStyle,
                color = gray300
            )
            Text(
                text = coupon.expireDate + " 까지",
                style = tapRegularTextStyle,
                color = gray300
            )
            Text(
                text = if (coupon.isUsed) {
                    "사용완료(" + coupon.usedDate + " 사용)"
                } else {
                    "기간만료"
                },
                style = badge01TextStyle,
                color = gray400
            )
        }
    }
}

@Preview
@Composable
private fun AvailableCouponCardPreview() {
    val coupon = CouponUiModel(
        id = 1,
        title = "10% 할인 쿠폰",
        writerName = "일상카페",
        imageId = null,
        expireDate = "2025.08.24 23:59",
        usedDate = null,
        isUsed = false,
        isExpired = false
    )
    AvailableCouponCard(
        coupon = coupon,
        onClick = {}
    )
}

@Preview
@Composable
private fun UsedOrExpiredCouponCardPreview() {
    val coupon = CouponUiModel(
        id = 1,
        title = "10% 할인 쿠폰",
        writerName = "일상카페",
        imageId = null,
        expireDate = "2025.08.24 23:59",
        usedDate = "2024.01.01",
        isUsed = true,
        isExpired = false
    )
    UsedOrExpiredCouponCard(coupon = coupon)
}


