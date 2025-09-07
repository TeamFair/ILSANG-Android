package com.ilsangtech.ilsang.feature.coupon.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.component.IlsangBottomSheet
import com.ilsangtech.ilsang.designsystem.theme.badge01TextStyle
import com.ilsangtech.ilsang.designsystem.theme.badge02TextStyle
import com.ilsangtech.ilsang.designsystem.theme.buttonTextStyle
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.heading01
import com.ilsangtech.ilsang.designsystem.theme.heading02
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.tapRegularTextStyle
import com.ilsangtech.ilsang.designsystem.theme.toSp
import com.ilsangtech.ilsang.feature.coupon.BuildConfig
import com.ilsangtech.ilsang.feature.coupon.model.CouponUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CouponPasswordBottomSheet(
    modifier: Modifier = Modifier,
    coupon: CouponUiModel,
    password: TextFieldState,
    isWrongPassword: Boolean,
    onDismissRequest: () -> Unit,
    onButtonClick: () -> Unit
) {
    IlsangBottomSheet(
        modifier = modifier,
        bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        onDismissRequest = onDismissRequest
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp, bottom = 15.dp),
            text = "비밀번호 입력",
            style = heading01,
            color = gray500,
            textAlign = TextAlign.Center
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CouponInfoComponent(coupon = coupon)
            CouponPasswordTextField(
                modifier = Modifier.padding(top = 18.dp),
                password = password,
                isWrongPassword = isWrongPassword
            )
            if (isWrongPassword) {
                Text(
                    modifier = Modifier.padding(top = 3.dp, bottom = 12.dp),
                    text = "비밀번호 오류입니다.",
                    style = badge02TextStyle.copy(
                        fontSize = 10.dp.toSp(),
                        lineHeight = 12.dp.toSp()
                    ),
                    color = Color(0xFFF16969)
                )
            } else {
                Spacer(Modifier.height(24.dp))
            }
            Text(
                modifier = Modifier.padding(bottom = 12.dp),
                text = "쿠폰은 사용 후 되돌릴 수 없습니다\n" +
                        "쿠폰을 사용하시겠습니까?",
                textAlign = TextAlign.Center,
                style = tapRegularTextStyle,
                color = gray500
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary,
                    disabledContainerColor = gray300,
                    contentColor = Color.White,
                    disabledContentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                enabled = password.text.length == 4 && !isWrongPassword,
                onClick = onButtonClick,
            ) {
                Text(
                    text = "쿠폰 사용하기",
                    style = buttonTextStyle
                )
            }
        }
    }
}

@Composable
private fun CouponInfoComponent(coupon: CouponUiModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    color = gray100,
                    strokeWidth = 1.dp.toPx(),
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height)
                )
            }
            .padding(vertical = 20.dp, horizontal = 23.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(51.dp)
                .clip(CircleShape)
                .background(Color(0xFFF1F5FF))
                .padding(vertical = 5.dp, horizontal = 4.dp),
            model = BuildConfig.IMAGE_URL + coupon.imageId,
            placeholder = painterResource(R.drawable.icon_coupon),
            error = painterResource(R.drawable.icon_coupon),
            contentDescription = "쿠폰",
        )
        Column {
            Text(
                text = coupon.title,
                style = heading02
            )
            Text(
                text = coupon.writerName,
                style = badge01TextStyle,
                color = gray400
            )
            Text(
                text = coupon.expireDate + "까지",
                style = tapRegularTextStyle,
                color = gray400
            )
        }
    }
}

@Composable
private fun CouponPasswordTextField(
    modifier: Modifier = Modifier,
    password: TextFieldState,
    isWrongPassword: Boolean
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "비밀번호 4자리를 입력해 주세요",
            style = heading01
        )
        BasicTextField(
            modifier = Modifier,
            state = password,
            lineLimits = TextFieldLineLimits.SingleLine,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.NumberPassword,
                imeAction = ImeAction.Done
            ),
            inputTransformation = InputTransformation.maxLength(4),
            decorator = { innerTextField ->
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    repeat(4) { index ->
                        Box(
                            modifier = Modifier
                                .size(67.dp, 74.dp)
                                .background(
                                    color = gray100,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .then(
                                    if (isWrongPassword) {
                                        Modifier.border(
                                            width = 2.dp,
                                            color = Color(0xFFF16969),
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                    } else {
                                        Modifier
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            val text = password.text
                            if (index < text.length) {
                                Text(
                                    text = text[index].toString(),
                                    style = TextStyle(
                                        fontFamily = pretendardFontFamily,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 20.dp.toSp()
                                    ),
                                    color = if (isWrongPassword) Color(0xFFF16969) else Color.Black,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
                Box(modifier = Modifier.size(0.dp)) {
                    innerTextField()
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun CouponPasswordBottomSheetPreview() {
    val password = rememberTextFieldState()
    val coupon = CouponUiModel(
        id = 1,
        title = "커피 무료 쿠폰",
        imageId = "image_id",
        writerName = "스타벅스",
        expireDate = "2023.12.31",
        usedDate = null,
        isUsed = false,
        isExpired = false
    )
    CouponPasswordBottomSheet(
        coupon = coupon,
        password = password,
        isWrongPassword = false,
        onDismissRequest = {},
        onButtonClick = {}
    )
}

