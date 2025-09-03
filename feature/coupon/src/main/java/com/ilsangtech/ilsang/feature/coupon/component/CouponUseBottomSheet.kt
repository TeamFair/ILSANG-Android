package com.ilsangtech.ilsang.feature.coupon.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.component.IlsangBottomSheet
import com.ilsangtech.ilsang.designsystem.theme.bodyTextStyle
import com.ilsangtech.ilsang.designsystem.theme.buttonTextStyle
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.tapRegularTextStyle
import com.ilsangtech.ilsang.designsystem.theme.title02
import com.ilsangtech.ilsang.feature.coupon.model.CouponUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CouponUseBottomSheet(
    modifier: Modifier = Modifier,
    coupon: CouponUiModel,
    onButtonClick: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    IlsangBottomSheet(
        modifier = modifier.fillMaxWidth(),
        bottomSheetState = rememberModalBottomSheetState(),
        onDismissRequest = onDismissRequest
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp, bottom = 15.dp),
            text = "쿠폰 사용하기",
            style = title02,
            color = gray500,
            textAlign = TextAlign.Center
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(96.dp),
                painter = painterResource(R.drawable.icon_coupon),
                tint = Color.Unspecified,
                contentDescription = "쿠폰"
            )
            Column(
                modifier = Modifier.padding(top = 1.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = coupon.title,
                    style = title02
                )
                Text(
                    text = coupon.writerName,
                    style = bodyTextStyle,
                    color = gray500
                )
                Text(
                    text = coupon.expireDate + "까지",
                    style = tapRegularTextStyle,
                    color = gray400
                )
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 38.dp, bottom = 16.dp)
                    .padding(horizontal = 20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(16.dp),
                onClick = onButtonClick
            ) {
                Text(
                    text = "사장님께 보여주기",
                    style = buttonTextStyle
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun CouponUseBottomSheetPreview() {
    val coupon = CouponUiModel(
        id = 1,
        title = "아메리카노 1잔",
        writerName = "카페 사장님",
        imageId = null,
        expireDate = "2023.12.31 23:59",
        usedDate = null,
        isUsed = false,
        isExpired = false
    )
    CouponUseBottomSheet(
        coupon = coupon,
        onButtonClick = {},
        onDismissRequest = {}
    )
}
