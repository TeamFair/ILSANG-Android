package com.ilsangtech.ilsang.feature.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.model.title.Title
import com.ilsangtech.ilsang.core.model.title.TitleGrade
import com.ilsangtech.ilsang.core.model.title.TitleType
import com.ilsangtech.ilsang.core.ui.title.TitleGradeIcon
import com.ilsangtech.ilsang.core.util.XpLevelCalculator
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.badge01TextStyle
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.toSp
import com.ilsangtech.ilsang.feature.profile.BuildConfig

@Composable
internal fun UserProfileInfoCard(
    modifier: Modifier = Modifier,
    nickname: String,
    profileImageId: String?,
    title: Title?,
    point: Int
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 15.dp)
                .padding(start = 16.dp, end = 33.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.padding(bottom = 7.dp)) {
                AsyncImage(
                    modifier = Modifier
                        .size(57.dp)
                        .clip(CircleShape),
                    model = BuildConfig.IMAGE_URL + profileImageId,
                    placeholder = painterResource(R.drawable.default_user_profile),
                    error = painterResource(R.drawable.default_user_profile),
                    contentDescription = null
                )
                Box(
                    modifier = Modifier
                        .offset(y = 7.dp)
                        .align(Alignment.BottomCenter)
                        .width(57.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .border(
                            width = 1.dp,
                            color = primary,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .background(Color.White)
                        .padding(vertical = 3.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Lv. ${XpLevelCalculator.getCurrentLevel(point)}",
                        style = TextStyle(
                            fontFamily = pretendardFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.dp.toSp(),
                            lineHeight = 12.dp.toSp()
                        ),
                        color = primary
                    )
                }
            }
            Spacer(Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = nickname,
                    style = TextStyle(
                        fontFamily = pretendardFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        lineHeight = 18.sp,
                        color = gray500
                    )
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    title?.let {
                        TitleGradeIcon(
                            modifier = Modifier.size(20.dp),
                            titleGrade = title.grade
                        )
                        Text(
                            text = title.name,
                            style = badge01TextStyle.copy(
                                fontSize = 11.dp.toSp(),
                                lineHeight = 12.dp.toSp()
                            ),
                            color = gray500
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(8.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFFEDEBEB))
                    ) {
                        val pointProgress = XpLevelCalculator.getLevelProgress(point)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(pointProgress)
                                .height(8.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(primary)
                        )
                    }
                    Text(
                        modifier = Modifier.padding(start = 7.dp),
                        text = "${point}P",
                        style = TextStyle(
                            fontFamily = pretendardFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.dp.toSp(),
                            lineHeight = 12.dp.toSp(),
                            color = primary
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun UserProfileInfoCardPreview() {
    UserProfileInfoCard(
        nickname = "김일상123",
        point = 650,
        profileImageId = null,
        title = Title(
            name = "세상을 움직이는 자",
            grade = TitleGrade.Rare,
            type = TitleType.Metro
        )
    )
}

