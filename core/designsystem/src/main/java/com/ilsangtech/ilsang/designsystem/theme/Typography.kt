package com.ilsangtech.ilsang.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.designsystem.R

val pretendardFontFamily = FontFamily(
    Font(R.font.pretendard_bold, FontWeight.Bold),
    Font(R.font.pretendard_semibold, FontWeight.SemiBold),
    Font(R.font.pretendard_regular, FontWeight.Normal),
    Font(R.font.pretendard_medium, FontWeight.Medium)
)

val payboocFontFamily = FontFamily(
    Font(R.font.paybooc_extrabold, FontWeight.ExtraBold),
    Font(R.font.paybooc_bold, FontWeight.Bold),
    Font(R.font.paybooc_medium, FontWeight.Medium),
    Font(R.font.paybooc_light, FontWeight.Light),
)

val gmarketSansFontFamily = FontFamily(
    Font(R.font.gmarket_sans_bold, FontWeight.Bold),
    Font(R.font.gmarket_sans_medium, FontWeight.Medium),
    Font(R.font.gmarket_sans_light, FontWeight.Light)
)

val title01 = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard_bold)),
    fontSize = 23.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.sp
)

val title02 = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard_bold)),
    fontSize = 17.sp,
    lineHeight = 22.sp,
    letterSpacing = 0.sp
)

val heading01 = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard_bold)),
    fontSize = 19.sp,
    lineHeight = 23.sp,
    letterSpacing = (-0.4).sp
)

val heading02 = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard_bold)),
    fontSize = 17.sp,
    lineHeight = 20.sp,
    letterSpacing = (-0.4).sp
)

val heading03 = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard_bold)),
    fontSize = 15.sp,
    lineHeight = 18.sp,
    letterSpacing = (-0.4).sp
)

val subTitle01 = TextStyle(
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = (-0.4).sp
)

val subTitle02 = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = (-0.4).sp
)

val caption01 = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
    fontSize = 13.sp,
    lineHeight = 20.sp,
    letterSpacing = (-0.3).sp
)

val caption02 = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
    fontSize = 12.sp,
    lineHeight = 16.sp,
    letterSpacing = (-0.3).sp
)

val bodyTextStyle = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
    fontSize = 15.sp,
    lineHeight = 22.sp,
    letterSpacing = (-0.3).sp
)

val buttonTextStyle = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
    fontSize = 16.sp,
    lineHeight = 18.sp,
    letterSpacing = 0.sp
)

val tapBoldTextStyle = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
    fontSize = 14.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.sp
)

val tapRegularTextStyle = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
    fontSize = 14.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.sp
)

val badge01TextStyle = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
    fontSize = 11.sp,
    lineHeight = 12.sp,
    letterSpacing = 0.sp
)

val badge02TextStyle = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
    fontSize = 10.sp,
    lineHeight = 12.sp,
    letterSpacing = (-0.3).sp
)

val ilsangTypography = Typography(
    titleLarge = title01,
    titleMedium = title02,
    headlineLarge = heading01,
    headlineMedium = heading02,
    headlineSmall = heading03,
    bodyMedium = bodyTextStyle
)

@Composable
fun Dp.toSp() = with(LocalDensity.current) { toSp() }