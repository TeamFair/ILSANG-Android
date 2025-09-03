package com.ilsangtech.ilsang.feature.coupon.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.component.ILSANGDialog
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily

@Composable
internal fun InvalidCouponPasswordDialog(
    onDismissRequest: () -> Unit
) {
    ILSANGDialog(
        buttonText = "확인",
        onDismissRequest = onDismissRequest
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                modifier = Modifier
                    .background(
                        color = Color(0xFFFFF1E5),
                        shape = CircleShape
                    )
                    .padding(5.5.dp)
                    .size(19.dp),
                painter = painterResource(R.drawable.icon_exclamation_mark),
                tint = Color.Unspecified,
                contentDescription = null
            )
            Text(
                text = "비밀번호가 틀렸습니다",
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

@Preview
@Composable
private fun InvalidCouponPasswordDialogPreview() {
    InvalidCouponPasswordDialog(
        onDismissRequest = {}
    )
}