package com.ilsangtech.ilsang.feature.home.my

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.feature.home.R
import com.ilsangtech.ilsang.feature.home.my.component.CustomerCenterItem
import com.ilsangtech.ilsang.feature.home.my.component.LogoutDialog
import com.ilsangtech.ilsang.feature.home.my.component.LogoutItem
import com.ilsangtech.ilsang.feature.home.my.component.TermsItem
import com.ilsangtech.ilsang.feature.home.my.component.VersionItem
import com.ilsangtech.ilsang.feature.home.my.component.WithdrawalItem

@Composable
fun SettingScreen(
    navigateToCustomerCenter: () -> Unit,
    navigateToWithdrawal: () -> Unit,
    popBackStack: () -> Unit
) {
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        LogoutDialog(
            onLogoutButtonClick = {
                showLogoutDialog = false
            },
            onDismissRequest = {
                showLogoutDialog = false
            }
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = background
    ) {
        Column {
            SettingScreenHeader(onBackButtonClick = popBackStack)
            CustomerCenterItem(onCustomerCenterItemClick = navigateToCustomerCenter)
            TermsItem {}
            VersionItem()
            LogoutItem { showLogoutDialog = true }
            WithdrawalItem(onWithdrawalItemClick = navigateToWithdrawal)
        }
    }
}

@Composable
private fun SettingScreenHeader(
    onBackButtonClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .statusBarsPadding()
            .padding(vertical = 12.dp),
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp)
                .clickable(
                    onClick = onBackButtonClick,
                    indication = null,
                    interactionSource = null
                ),
            painter = painterResource(id = R.drawable.back),
            contentDescription = null
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "설정",
            style = TextStyle(
                fontFamily = pretendardFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = gray500
            )
        )
    }
}


@Preview
@Composable
private fun SettingScreenPreview() {
    SettingScreen(
        navigateToCustomerCenter = {},
        navigateToWithdrawal = {},
        popBackStack = {}
    )
}