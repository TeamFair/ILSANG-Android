package com.ilsangtech.ilsang.feature.home.my.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_bold
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_regular
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.feature.home.R


@Composable
fun MyChallengeHeader(
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .navigationBarsPadding()
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
                painter = painterResource(R.drawable.back),
                contentDescription = null,
                tint = gray500
            )
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "챌린지 정보",
                style = myChallengeHeaderTextStyle,
            )

            Icon(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable(
                        onClick = { isExpanded = true },
                        interactionSource = null,
                        indication = null
                    ),
                painter = painterResource(R.drawable.more_vertical),
                contentDescription = null,
                tint = gray500
            )
        }
        ChallengeDropDownMenu(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            isExpanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        )
    }
}

@Composable
fun ChallengeDropDownMenu(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    onDismissRequest: () -> Unit
) {
    DropdownMenu(
        modifier = modifier.width(180.dp),
        expanded = isExpanded,
        offset = DpOffset(x = (-20).dp, y = 10.dp),
        shape = RoundedCornerShape(8.dp),
        onDismissRequest = onDismissRequest,
        containerColor = Color.White
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 10.dp,
                    horizontal = 12.dp
                )
                .clickable(
                    onClick = {},
                    interactionSource = null,
                    indication = null
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                style = myChallengeDropDownMenuTextStyle,
                text = "공유하기"
            )
            Icon(
                modifier = Modifier.align(Alignment.CenterEnd),
                painter = painterResource(R.drawable.share),
                contentDescription = null
            )
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = gray100
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 10.dp,
                    horizontal = 12.dp
                )
                .clickable(
                    onClick = {},
                    interactionSource = null,
                    indication = null
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                style = myChallengeDropDownMenuTextStyle,
                text = "삭제하기"
            )
            Icon(
                modifier = Modifier.align(Alignment.CenterEnd),
                painter = painterResource(R.drawable.delete),
                contentDescription = null
            )
        }
    }
}

private val myChallengeHeaderTextStyle = TextStyle(
    fontFamily = FontFamily(Font(pretendard_bold)),
    fontSize = 17.sp,
    lineHeight = 22.sp,
    color = gray500
)

private val myChallengeDropDownMenuTextStyle = TextStyle(
    fontFamily = FontFamily(Font(pretendard_regular)),
    fontSize = 15.sp,
    lineHeight = 1.3.em,
    color = gray500
)

@Preview(showBackground = true)
@Composable
fun MyChallengeHeaderPreview() {
    MyChallengeHeader {}
}

@Preview
@Composable
fun ChallengeDropDownMenuPreview() {
    ChallengeDropDownMenu(
        isExpanded = true,
        onDismissRequest = {}
    )
}