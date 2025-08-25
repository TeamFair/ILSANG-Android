package com.ilsangtech.ilsang.feature.ranking.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import com.ilsangtech.ilsang.core.model.title.TitleGrade
import com.ilsangtech.ilsang.core.ui.title.TitleGradeIcon
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.badge01TextStyle
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.heading02
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.title01
import com.ilsangtech.ilsang.designsystem.theme.toSp
import com.ilsangtech.ilsang.feature.ranking.BuildConfig
import java.util.Locale

@Composable
internal fun UserRankItem(
    modifier: Modifier = Modifier,
    imageId: String?,
    nickname: String,
    titleName: String?,
    titleGrade: TitleGrade?,
    rank: Int?,
    point: Int
) {
    DefaultUserRankCard(modifier = modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier.size(30.dp),
                contentAlignment = Alignment.Center
            ) {
                rank?.let {
                    if (rank <= 3) {
                        Icon(
                            modifier = Modifier.padding(2.dp),
                            painter = painterResource(
                                when (rank) {
                                    1 -> R.drawable.rank_gold
                                    2 -> R.drawable.rank_silver
                                    else -> R.drawable.rank_bronze
                                }
                            ),
                            tint = Color.Unspecified,
                            contentDescription = null
                        )
                    } else {
                        Text(
                            text = rank.toString(),
                            style = heading02.copy(
                                fontSize = 15.dp.toSp(),
                                lineHeight = 18.dp.toSp()
                            )
                        )
                    }
                }
            }
            DefaultUserRankContent(
                imageId = imageId,
                nickname = nickname,
                titleName = titleName,
                titleGrade = titleGrade,
                point = point
            )
        }
    }
}

@Composable
internal fun MyRankCard(
    modifier: Modifier = Modifier,
    imageId: String?,
    nickname: String,
    titleName: String?,
    titleGrade: TitleGrade?,
    point: Int,
    rank: Int?,
    requiredPoint: Int?
) {
    DefaultUserRankCard(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(R.drawable.icon_rank_card_me),
                    tint = Color.Unspecified,
                    contentDescription = null
                )
                rank?.let {
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp),
                        text = rank.toString(),
                        style = heading02.copy(
                            fontSize = 15.dp.toSp(),
                            lineHeight = 18.dp.toSp()
                        )
                    )
                }
            }
            DefaultUserRankContent(
                imageId = imageId,
                nickname = nickname,
                titleName = titleName,
                titleGrade = titleGrade,
                point = point
            )
        }
        if (requiredPoint != null && requiredPoint > 0) {
            Box(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(300.dp))
                    .border(
                        shape = RoundedCornerShape(300.dp),
                        color = primary,
                        width = 1.dp
                    )
                    .padding(
                        vertical = 6.dp,
                        horizontal = 16.dp
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "앞으로 ${requiredPoint}P 획득시" +
                            " 다음  순위로 올라갈 수 있어요!",
                    style = badge01TextStyle,
                    color = primary
                )
            }
        }
    }
}

@Composable
private fun DefaultUserRankCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier.padding(36.dp),
            content = content
        )
    }
}

@Composable
private fun DefaultUserRankContent(
    modifier: Modifier = Modifier,
    imageId: String?,
    nickname: String,
    titleName: String?,
    titleGrade: TitleGrade?,
    point: Int
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color(0xFFF1F5FF)),
            model = BuildConfig.IMAGE_URL + imageId,
            contentDescription = null
        )
        Column {
            Text(
                text = nickname,
                style = TextStyle(
                    fontFamily = pretendardFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                ),
                color = gray500
            )
            Spacer(Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (titleGrade != null && titleName != null) {
                    TitleGradeIcon(
                        modifier = Modifier.size(12.dp),
                        titleGrade = titleGrade
                    )
                    Text(
                        text = titleName,
                        style = badge01TextStyle,
                        color = gray400
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            Text(
                text = String.format(
                    locale = Locale.getDefault(),
                    format = "%,d",
                    point
                ) + "p",
                style = title01
            )
        }
    }
}

@Preview
@Composable
private fun UserRankItemPreview() {
    val imageId = "sample_image_id"
    val nickname = "일상유저123닉네임 길어"
    val titleName = "모두의 시선을 받는 자"
    val titleGrade = TitleGrade.Standard
    val rank = 1
    val point = 81223840
    UserRankItem(
        imageId = imageId,
        nickname = nickname,
        titleName = titleName,
        titleGrade = titleGrade,
        rank = rank,
        point = point
    )
}

@Preview
@Composable
private fun MyRankCardPreview() {
    val imageId = "sample_image_id"
    val nickname = "일상유저123닉네임 길어"
    val titleName = "모두의 시선을 받는 자"
    val titleGrade = TitleGrade.Standard
    val point = 81223840
    val rank = 1000
    val requiredPoint = 1000
    MyRankCard(
        modifier = Modifier,
        imageId = imageId,
        nickname = nickname,
        titleName = titleName,
        titleGrade = titleGrade,
        point = point,
        rank = rank,
        requiredPoint = requiredPoint
    )
}

