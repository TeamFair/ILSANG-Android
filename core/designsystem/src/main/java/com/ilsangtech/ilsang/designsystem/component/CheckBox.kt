package com.ilsangtech.ilsang.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.gray200
import com.ilsangtech.ilsang.designsystem.theme.primary

@Composable
fun IlsangCheckBox(
    modifier: Modifier = Modifier,
    checked: Boolean,
    color: Color = primary,
    borderColor: Color = gray200,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(if (checked) color else Color.Transparent)
            .then(
                if (!checked) {
                    Modifier.border(
                        width = 1.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(4.dp)
                    )
                } else {
                    Modifier
                }
            )
            .then(
                if (enabled) {
                    Modifier.clickable(
                        onClick = onClick,
                        interactionSource = null,
                        indication = null
                    )
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Icon(
                painter = painterResource(id = R.drawable.icon_check),
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

@Composable
fun CircleShapeCheckBox(
    modifier: Modifier = Modifier,
    checked: Boolean,
    color: Color = primary,
    borderColor: Color = gray200,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .then(
                if (!checked) {
                    Modifier.border(
                        width = 1.dp,
                        color = borderColor,
                        shape = CircleShape
                    )
                } else {
                    Modifier
                }
            )
            .then(
                if (enabled) {
                    Modifier.clickable(
                        onClick = onClick,
                        interactionSource = null,
                        indication = null
                    )
                } else {
                    Modifier
                }
            )
    ) {
        if (checked) {
            Icon(
                painter = painterResource(id = R.drawable.icon_circular_check),
                contentDescription = null,
                tint = color
            )
        }
    }
}

@Composable
@Preview(
    showBackground = true,
    widthDp = 20,
    heightDp = 20
)
private fun IlsangCheckBoxPreview() {
    var checked by remember { mutableStateOf(false) }
    IlsangCheckBox(
        checked = checked,
        onClick = { checked = !checked }
    )
}

@Composable
@Preview(showBackground = true)
private fun CircleShapeCheckBoxPreview() {
    var checked by remember { mutableStateOf(false) }
    CircleShapeCheckBox(
        modifier = Modifier.size(20.dp),
        checked = checked,
        onClick = { checked = !checked }
    )
}