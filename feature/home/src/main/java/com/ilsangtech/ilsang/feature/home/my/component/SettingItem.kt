package com.ilsangtech.ilsang.feature.home.my.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.R

@Composable
internal fun SettingItem(
    title: @Composable () -> Unit,
    endContent: @Composable (() -> Unit)? = null,
    onItemClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        onClick = onItemClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 18.dp,
                    horizontal = 25.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            title.invoke()
            endContent?.invoke()
        }
    }
}

@Composable
internal fun CustomerCenterItem(
    onCustomerCenterItemClick: () -> Unit
) {
    SettingItem(
        title = {
            Text(
                text = "고객센터",
                style = settingItemTextStyle
            )
        },
        endContent = { SettingItemNextIcon() },
        onItemClick = onCustomerCenterItemClick
    )
}

@Composable
internal fun FaqItem(
    onFaqItemClick: () -> Unit
) {
    SettingItem(
        title = {
            Text(
                text = "자주 물어보는 질문",
                style = settingItemTextStyle
            )
        },
        endContent = { SettingItemNextIcon() },
        onItemClick = onFaqItemClick
    )
}

@Composable
internal fun TermsItem(
    onTermsItemClick: () -> Unit
) {
    SettingItem(
        title = {
            Text(
                text = "약관 및 정책",
                style = settingItemTextStyle
            )
        },
        endContent = { SettingItemNextIcon() },
        onItemClick = onTermsItemClick
    )
}

@Composable
internal fun VersionItem() {
    val packageManager = LocalContext.current.packageManager
    val versionName = packageManager.getPackageInfo(LocalContext.current.packageName, 0).versionName
    SettingItem(
        title = {
            Text(
                text = "현재 버전",
                style = settingItemTextStyle
            )
        },
        endContent = {
            Text(
                text = "v.$versionName",
                style = TextStyle(
                    fontFamily = pretendardFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 17.sp,
                    color = Color(0xFFA0A0A0)
                )
            )
        }
    )
}

@Composable
internal fun LogoutItem(
    onLogoutItemClick: () -> Unit
) {
    SettingItem(
        title = {
            Text(
                text = "로그아웃",
                style = settingItemTextStyle
            )
        },
        onItemClick = onLogoutItemClick
    )
}

@Composable
internal fun WithdrawalItem(
    onWithdrawalItemClick: () -> Unit
) {
    SettingItem(
        title = {
            Text(
                text = "회원 탈퇴",
                style = settingItemTextStyle.copy(color = Color(0xFFF87272))
            )
        },
        onItemClick = onWithdrawalItemClick
    )
}

@Composable
private fun SettingItemNextIcon() {
    Icon(
        painter = painterResource(R.drawable.icon_next),
        contentDescription = null,
        tint = Color.Unspecified
    )
}

private val settingItemTextStyle = TextStyle(
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 17.sp,
    color = gray500
)
