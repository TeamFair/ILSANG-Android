package com.ilsangtech.ilsang.feature.coupon.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.feature.coupon.model.CouponTab

@Composable
internal fun CouponListEmptyContent(
    modifier: Modifier = Modifier,
    selectedCouponTab: CouponTab,
    questNavButtonClick: () -> Unit
) {
    val (headingText, subText) = if (selectedCouponTab == CouponTab.Available) {
        "보유 중인 쿠폰이 없어요" to "일상존 퀘스트로 쿠폰 확률을 높여보세요!"
    } else {
        "만료/완료된 쿠폰이 없어요" to "일상존 퀘스트로 쿠폰 확률을 높여보세요!"
    }

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = headingText,
                    style = TextStyle(
                        fontFamily = pretendardFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 21.sp,
                        lineHeight = 1.5.em
                    ),
                    color = gray500,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = subText,
                    style = TextStyle(
                        fontFamily = pretendardFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 17.sp,
                        lineHeight = 1.5.em
                    ),
                    color = gray400,
                    textAlign = TextAlign.Center
                )
            }
            if (selectedCouponTab == CouponTab.Available) {
                Button(
                    modifier = Modifier.padding(top = 40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = primary),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(vertical = 16.dp, horizontal = 24.dp),
                    onClick = questNavButtonClick
                ) {
                    Text(
                        text = "퀘스트 바로가기",
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

@Preview(showBackground = true)
@Composable
private fun CouponListEmptyContentAvailablePreview() {
    CouponListEmptyContent(
        modifier = Modifier.fillMaxSize(),
        selectedCouponTab = CouponTab.Available,
        questNavButtonClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun CouponListEmptyContentUsedOrExpiredPreview() {
    CouponListEmptyContent(
        modifier = Modifier.fillMaxSize(),
        selectedCouponTab = CouponTab.UsedOrExpired,
        questNavButtonClick = {})
}
