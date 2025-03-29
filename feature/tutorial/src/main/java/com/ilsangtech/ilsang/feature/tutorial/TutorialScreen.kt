package com.ilsangtech.ilsang.feature.tutorial

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.core.ui.R.drawable.right_icon
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.tapBoldTextStyle

@Composable
fun TutorialScreen(navigateToHome: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            SkipButton(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(
                        top = 20.dp,
                        end = 20.dp
                    ),
                onClick = navigateToHome
            )
        }
    }
}

@Composable
private fun SkipButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier.clickable(
            onClick = onClick,
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "SKIP",
                style = tapBoldTextStyle,
                color = gray300
            )
            Icon(
                painter = painterResource(id = right_icon),
                contentDescription = null,
                tint = gray300
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TutorialScreenPreview() {
    TutorialScreen {}
}