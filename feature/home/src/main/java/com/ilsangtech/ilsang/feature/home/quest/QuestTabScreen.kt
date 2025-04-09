package com.ilsangtech.ilsang.feature.home.quest

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun QuestTabScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Column {
            QuestTapHeader()
            Box(modifier = Modifier.fillMaxWidth()) {
                SortTypeMenuContent(Modifier.zIndex(1f))
                LazyColumn(
                    modifier = Modifier.offset(y = 64.dp)
                ) {}
            }
        }
    }
}

@Preview(backgroundColor = 0xFFF6F6F6)
@Composable
fun QuestTabScreenPreview() {
    QuestTabScreen()
}
