package com.ilsangtech.ilsang.feature.coupon.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.designsystem.component.ILSANGDialog
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily

@Composable
internal fun CouponUseSuccessDialog(
    onDismissRequest: () -> Unit
) {
    ILSANGDialog(
        buttonText = "확인",
        onDismissRequest = onDismissRequest,
    ) {
        Text(
            text = "쿠폰이 적용되었습니다",
            style = TextStyle(
                fontFamily = pretendardFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                lineHeight = 18.sp
            ),
        )
    }
}

@Preview
@Composable
private fun CouponUseSuccessDialogPreview() {
    CouponUseSuccessDialog(onDismissRequest = {})
}