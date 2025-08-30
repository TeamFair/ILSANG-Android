package com.ilsangtech.ilsang.feature.my.screens.setting.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.feature.my.component.DefaultSettingListItem
import com.ilsangtech.ilsang.feature.my.component.DefaultSettingListItemIcon
import com.ilsangtech.ilsang.feature.my.component.defaultSettingListItemTextStyle

@Composable
internal fun CustomerCenterItem(
    onCustomerCenterItemClick: () -> Unit
) {
    DefaultSettingListItem(
        title = {
            Text(
                text = "고객센터",
                style = defaultSettingListItemTextStyle
            )
        },
        endContent = { DefaultSettingListItemIcon() },
        onItemClick = onCustomerCenterItemClick
    )
}

@Composable
internal fun FaqItem(
    onFaqItemClick: () -> Unit
) {
    DefaultSettingListItem(
        title = {
            Text(
                text = "자주 물어보는 질문",
                style = defaultSettingListItemTextStyle
            )
        },
        endContent = { DefaultSettingListItemIcon() },
        onItemClick = onFaqItemClick
    )
}

@Composable
internal fun TermsItem(
    onTermsItemClick: () -> Unit
) {
    DefaultSettingListItem(
        title = {
            Text(
                text = "약관 및 정책",
                style = defaultSettingListItemTextStyle
            )
        },
        endContent = { DefaultSettingListItemIcon() },
        onItemClick = onTermsItemClick
    )
}

@Composable
internal fun VersionItem() {
    val packageManager = LocalContext.current.packageManager
    val versionName = packageManager.getPackageInfo(LocalContext.current.packageName, 0).versionName
    DefaultSettingListItem(
        title = {
            Text(
                text = "현재 버전",
                style = defaultSettingListItemTextStyle
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
    DefaultSettingListItem(
        title = {
            Text(
                text = "로그아웃",
                style = defaultSettingListItemTextStyle
            )
        },
        onItemClick = onLogoutItemClick
    )
}

@Composable
internal fun WithdrawalItem(
    onWithdrawalItemClick: () -> Unit
) {
    DefaultSettingListItem(
        title = {
            Text(
                text = "회원 탈퇴",
                style = defaultSettingListItemTextStyle.copy(color = Color(0xFFF87272))
            )
        },
        onItemClick = onWithdrawalItemClick
    )
}

@Composable
internal fun LicenseItem(
    onLicenseItemClick: () -> Unit
) {
    DefaultSettingListItem(
        title = {
            Text(
                text = "오픈소스 정보",
                style = defaultSettingListItemTextStyle
            )
        },
        endContent = { DefaultSettingListItemIcon() },
        onItemClick = onLicenseItemClick
    )
}
