package com.ilsangtech.ilsang.feature.my.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_bold
import com.ilsangtech.ilsang.feature.my.R

@Composable
internal fun UserImageEditContent(
    model: Any?,
    onSelectImage: (Uri) -> Unit,
    onDeleteImage: () -> Unit
) {
    val imagePicker = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            onSelectImage(it)
        }
    }

    var showImageEditBottomSheet by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showImageEditBottomSheet) {
        UserImageEditBottomSheet(
            onDismissRequest = { showImageEditBottomSheet = false },
            onSelectImage = {
                imagePicker.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            },
            onDeleteImage = { showDeleteDialog = true }
        )
    }

    if (showDeleteDialog) {
        UserImageDeleteDialog(
            onDeleteButtonClick = {
                showDeleteDialog = false
                onDeleteImage()
            },
            onDismissRequest = { showDeleteDialog = false }
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            model = model,
            error = painterResource(R.drawable.default_user_profile),
            contentDescription = null
        )
        Spacer(Modifier.height(8.dp))
        Text(
            modifier = Modifier.clickable(
                indication = null,
                interactionSource = null,
                onClick = { showImageEditBottomSheet = true }
            ),
            text = "프로필 사진 수정",
            style = TextStyle(
                fontFamily = FontFamily(Font(pretendard_bold)),
                fontSize = 17.sp,
                color = Color(0xFF528BFF)
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UserImageEditContentPreview() {
    UserImageEditContent(
        model = null,
        onSelectImage = {},
        onDeleteImage = {}
    )
}



