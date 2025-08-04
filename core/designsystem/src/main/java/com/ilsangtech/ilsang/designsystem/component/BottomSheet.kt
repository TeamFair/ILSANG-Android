package com.ilsangtech.ilsang.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.designsystem.theme.gray100

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IlsangBottomSheet(
    modifier: Modifier = Modifier,
    bottomSheetState: SheetState,
    onDismissRequest: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    ModalBottomSheet(
        modifier = modifier,
        sheetState = bottomSheetState,
        containerColor = Color.White,
        onDismissRequest = onDismissRequest,
        dragHandle = { IlsangBottomSheetDragHandle() },
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun IlsangBottomSheetDragHandle() {
    Box(
        modifier = Modifier
            .padding(top = 8.dp)
            .size(
                width = 30.dp,
                height = 4.dp
            )
            .clip(RoundedCornerShape(20.dp))
            .background(gray100)
    )
}