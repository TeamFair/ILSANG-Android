package com.ilsangtech.ilsang.feature.my.screens.withdrawal

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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.feature.my.R
import com.ilsangtech.ilsang.feature.my.screens.withdrawal.component.WithdrawalButton
import com.ilsangtech.ilsang.feature.my.screens.withdrawal.component.WithdrawalContent
import com.ilsangtech.ilsang.feature.my.screens.withdrawal.component.WithdrawalDialog

@Composable
internal fun WithdrawalScreen(
    withdrawalViewModel: WithdrawalViewModel = hiltViewModel(),
    navigateToMyMain: () -> Unit,
    navigateToLogin: () -> Unit
) {
    val withdrawalState by withdrawalViewModel.withdrawalState.collectAsStateWithLifecycle()

    LaunchedEffect(withdrawalState) {
        if (withdrawalState == true) {
            navigateToLogin()
        }
    }

    WithdrawalScreen(
        onWithdrawalButtonClick = withdrawalViewModel::withdraw,
        onBackButtonClick = navigateToMyMain
    )
}

@Composable
private fun WithdrawalScreen(
    onWithdrawalButtonClick: () -> Unit,
    onBackButtonClick: () -> Unit
) {
    var showWithdrawalDialog by remember { mutableStateOf(false) }

    if (showWithdrawalDialog) {
        WithdrawalDialog(
            onDismissRequest = { showWithdrawalDialog = false },
            onWithdrawalButtonClick = {
                showWithdrawalDialog = false
                onWithdrawalButtonClick()
            }
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column {
            WithdrawalScreenHeader(onBackButtonClick = onBackButtonClick)
            WithdrawalContent(modifier = Modifier.weight(1f))
            WithdrawalButton(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 8.dp),
                onWithdrawalButtonClick = { showWithdrawalDialog = true }
            )
        }
    }
}

@Composable
private fun WithdrawalScreenHeader(
    onBackButtonClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(vertical = 12.dp)
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp)
                .clickable(
                    onClick = onBackButtonClick,
                    indication = null,
                    interactionSource = null
                ),
            painter = painterResource(id = R.drawable.back),
            tint = Color.Unspecified,
            contentDescription = null
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "회원 탈퇴",
            style = TextStyle(
                fontFamily = pretendardFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            ),
            color = gray500
        )
    }
}


@Preview
@Composable
private fun WithdrawalScreenPreview() {
    WithdrawalScreen(
        onWithdrawalButtonClick = {},
        onBackButtonClick = {}
    )
}
