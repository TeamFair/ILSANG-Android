package com.ilsangtech.ilsang.feature.my.screens.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.feature.my.screens.setting.component.CustomerCenterItem
import com.ilsangtech.ilsang.feature.my.screens.setting.component.FaqItem
import com.ilsangtech.ilsang.feature.my.screens.setting.component.LicenseItem
import com.ilsangtech.ilsang.feature.my.screens.setting.component.LogoutDialog
import com.ilsangtech.ilsang.feature.my.screens.setting.component.LogoutItem
import com.ilsangtech.ilsang.feature.my.screens.setting.component.SettingScreenHeader
import com.ilsangtech.ilsang.feature.my.screens.setting.component.TermsItem
import com.ilsangtech.ilsang.feature.my.screens.setting.component.VersionItem
import com.ilsangtech.ilsang.feature.my.screens.setting.component.WithdrawalItem

@Composable
internal fun SettingScreen(
    settingViewModel: SettingViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit,
    navigateToCustomerCenter: () -> Unit,
    navigateToFaq: () -> Unit,
    navigateToLicense: () -> Unit,
    navigateToTerms: () -> Unit,
    navigateToWithdrawal: () -> Unit,
    popBackStack: () -> Unit
) {
    val logoutState by settingViewModel.logoutState.collectAsStateWithLifecycle()

    LaunchedEffect(logoutState) {
        if (logoutState == true) {
            navigateToLogin()
        }
    }

    SettingScreen(
        onLogoutButtonClick = settingViewModel::logout,
        navigateToCustomerCenter = navigateToCustomerCenter,
        navigateToFaq = navigateToFaq,
        navigateToLicense = navigateToLicense,
        navigateToTerms = navigateToTerms,
        navigateToWithdrawal = navigateToWithdrawal,
        popBackStack = popBackStack
    )
}

@Composable
private fun SettingScreen(
    onLogoutButtonClick: () -> Unit,
    navigateToCustomerCenter: () -> Unit,
    navigateToFaq: () -> Unit,
    navigateToLicense: () -> Unit,
    navigateToTerms: () -> Unit,
    navigateToWithdrawal: () -> Unit,
    popBackStack: () -> Unit
) {
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        LogoutDialog(
            onLogoutButtonClick = {
                onLogoutButtonClick()
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
            FaqItem(onFaqItemClick = navigateToFaq)
            TermsItem(onTermsItemClick = navigateToTerms)
            LicenseItem(onLicenseItemClick = navigateToLicense)
            VersionItem()
            LogoutItem { showLogoutDialog = true }
            WithdrawalItem(onWithdrawalItemClick = navigateToWithdrawal)
        }
    }
}

@Preview
@Composable
private fun SettingScreenPreview() {
    SettingScreen(
        onLogoutButtonClick = {},
        navigateToCustomerCenter = {},
        navigateToFaq = {},
        navigateToWithdrawal = {},
        navigateToTerms = {},
        navigateToLicense = {},
        popBackStack = {}
    )
}