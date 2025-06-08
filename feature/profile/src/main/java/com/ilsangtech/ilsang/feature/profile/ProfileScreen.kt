package com.ilsangtech.ilsang.feature.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.title02

@Composable
private fun ProfileScreen(
    onBackButtonClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = background
    ) {
        Column(modifier = Modifier.statusBarsPadding()) {
            ProfileScreenHeader(
                onBackButtonClick = onBackButtonClick
            )
        }
    }
}

@Composable
private fun ProfileScreenHeader(
    onBackButtonClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 45.dp)
            .padding(
                vertical = 7.dp,
                horizontal = 12.dp
            )
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable(
                    onClick = onBackButtonClick,
                    interactionSource = null,
                    indication = null
                ),
            painter = painterResource(R.drawable.icon_back),
            tint = gray500,
            contentDescription = null
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "프로필 정보",
            style = title02.copy(color = gray500)
        )

    }
}

@Composable
@Preview
private fun ProfileScreenPreview() {
    ProfileScreen(
        onBackButtonClick = {}
    )
}