package com.ilsangtech.ilsang.core.ui.quest.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.caption01
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.heading03

@Composable
internal fun MissionDescriptionCard(
    modifier: Modifier = Modifier,
    missionTitle: String
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = background),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                modifier = Modifier.padding(vertical = 3.dp),
                text = "퀘스트 요약",
                style = heading03,
                color = gray500
            )
            Text(
                text = missionTitle,
                style = caption01,
                color = gray500
            )
        }
    }
}

@Preview
@Composable
private fun MissionDescriptionCardPreview() {
    MissionDescriptionCard(
        missionTitle = "서현역 내부 또는 연결구간에서 특이한 패널·격자·기하 구조가 드러난 지점 촬영"
    )
}