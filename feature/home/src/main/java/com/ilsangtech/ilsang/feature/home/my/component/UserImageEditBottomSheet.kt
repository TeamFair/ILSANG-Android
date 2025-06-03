package com.ilsangtech.ilsang.feature.home.my.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_bold
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.subTitle02
import com.ilsangtech.ilsang.feature.home.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun UserImageEditBottomSheet(
    onDismissRequest: () -> Unit,
    onSelectImage: () -> Unit,
    onDeleteImage: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = bottomSheetState,
        dragHandle = null,
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 26.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "프로필 사진 수정",
                style = TextStyle(
                    fontFamily = FontFamily(Font(pretendard_bold)),
                    fontSize = 17.sp,
                )
            )
            Spacer(Modifier.height(15.dp))
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.Transparent,
                onClick = {
                    coroutineScope.launch {
                        bottomSheetState.hide()
                    }.invokeOnCompletion {
                        onSelectImage()
                        onDismissRequest()
                    }
                }
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        modifier = Modifier.padding(start = 18.dp),
                        painter = painterResource(id = R.drawable.image_select_icon),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    Text(
                        text = "갤러리에서 선택",
                        style = subTitle02.copy(color = gray500)
                    )
                }
            }
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.Transparent,
                onClick = {
                    coroutineScope.launch {
                        bottomSheetState.hide()
                    }.invokeOnCompletion {
                        onDeleteImage()
                        onDismissRequest()
                    }
                }
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        modifier = Modifier.padding(start = 18.dp),
                        painter = painterResource(id = R.drawable.image_delete_icon),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    Text(
                        text = "프로필 삭제",
                        style = subTitle02.copy(color = Color.Red)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun UserImageEditBottomSheetPreview() {
    UserImageEditBottomSheet(
        onDismissRequest = {},
        onSelectImage = {},
        onDeleteImage = {}
    )
}