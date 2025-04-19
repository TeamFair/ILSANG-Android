package com.ilsangtech.ilsang.feature.home.my.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath
import com.ilsangtech.ilsang.core.model.UserInfo
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_bold
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_regular
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_semibold
import com.ilsangtech.ilsang.designsystem.theme.caption02
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray200
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.title01
import com.ilsangtech.ilsang.feature.home.R
import com.ilsangtech.ilsang.feature.home.my.util.XpLevelCalculator
import java.util.Locale

@Composable
fun MyInfoMenuContent(userInfo: UserInfo) {
    LazyColumn(
        modifier = Modifier.padding(top = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            MyInfoTotalXpPointContent(userInfo = userInfo)
        }
        item {
            MyInfoStatsXpPointContent(userInfo = userInfo)
        }
    }
}

@Composable
fun MyInfoTotalXpPointContent(userInfo: UserInfo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "총 포인트",
                style = caption02,
                color = gray400
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = String.format(
                    locale = Locale.KOREA,
                    format = "%,d",
                    userInfo.xpPoint
                ) + "XP",
                style = title01,
                color = gray500
            )
            Spacer(Modifier.height(14.dp))
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                progress = { XpLevelCalculator.getLevelProgress(userInfo.xpPoint) },
                color = primary,
                trackColor = Color(0xFFEDEBEB),
                gapSize = (-5).dp,
                drawStopIndicator = {}
            )
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "LV.${XpLevelCalculator.getCurrentLevel(userInfo.xpPoint)}",
                    fontFamily = FontFamily(Font(pretendard_bold)),
                    fontSize = 13.sp,
                    lineHeight = 12.sp,
                    color = gray200
                )
                Text(
                    text = "다음 레벨까지 ${XpLevelCalculator.getRequiredXpPointForNextLevel(userInfo.xpPoint)}XP 남았어요!",
                    fontFamily = FontFamily(Font(pretendard_regular)),
                    fontSize = 13.sp,
                    lineHeight = 12.sp,
                    color = gray400
                )
                Text(
                    text = "LV.${XpLevelCalculator.getCurrentLevel(userInfo.xpPoint) + 1}",
                    fontFamily = FontFamily(Font(pretendard_bold)),
                    fontSize = 13.sp,
                    lineHeight = 12.sp,
                    color = primary
                )
            }
        }
    }
}

@Composable
fun MyInfoStatsXpPointContent(userInfo: UserInfo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = "능력별 포인트",
                style = caption02,
                color = gray400
            )

            UserStatsGraph(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                userInfo = userInfo
            )
            Spacer(Modifier.height(9.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = primary),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                onClick = {}
            ) {
                Text(
                    text = "공유하기",
                    style = shareButtonTextStyle,
                )
            }
        }
    }
}

@Composable
fun UserStatsGraph(
    modifier: Modifier = Modifier,
    userInfo: UserInfo
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StatText(
            modifier = Modifier
                .padding(bottom = 1.dp),
            iconRes = R.drawable.strength,
            text = "체력"
        )
        Row {
            StatText(
                modifier = Modifier.offset(y = 54.dp),
                iconRes = R.drawable.social,
                text = "사회성"
            )
            Box(
                modifier = Modifier
                    .size(185.dp)
                    .drawWithCache {
                        val firstRoundedPolygon = RoundedPolygon(
                            numVertices = 5,
                            radius = size.width / 2,
                            centerX = size.center.x,
                            centerY = size.center.y,
                            rounding = CornerRounding(radius = 20.dp.toPx()),
                        )
                        val secondRoundedPolygon = RoundedPolygon(
                            numVertices = 5,
                            radius = size.width / 2 * 0.9f,
                            centerX = size.center.x,
                            centerY = size.center.y,
                            rounding = CornerRounding(radius = 20.dp.toPx()),
                        )
                        val thirdRoundedPolygon = RoundedPolygon(
                            numVertices = 5,
                            radius = size.width / 2 * 0.5f,
                            centerX = size.center.x,
                            centerY = size.center.y,
                            rounding = CornerRounding(radius = 20.dp.toPx()),
                        )

                        onDrawBehind {
                            rotate(degrees = 54f) {
                                drawPath(
                                    path = firstRoundedPolygon.toPath().asComposePath(),
                                    style = Stroke(width = 1.dp.toPx()),
                                    color = gray300
                                )
                                drawPath(
                                    path = secondRoundedPolygon.toPath().asComposePath(),
                                    style = Stroke(width = 1.dp.toPx()),
                                    color = gray100
                                )
                                drawPath(
                                    path = thirdRoundedPolygon.toPath().asComposePath(),
                                    style = Stroke(width = 1.dp.toPx()),
                                    color = gray100
                                )
                            }
                        }
                    }
            )
            StatText(
                modifier = Modifier.offset(y = 54.dp),
                iconRes = R.drawable.bulb,
                text = "지능"
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-18).dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            StatText(
                iconRes = R.drawable.`fun`,
                text = "재미"
            )
            StatText(
                iconRes = R.drawable.heart,
                text = "매력"
            )
        }
    }
}

@Composable
private fun StatText(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    text: String
) {
    Row(
        modifier = modifier.height(30.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = text,
            style = statTextStyle
        )
    }
}

private val shareButtonTextStyle = TextStyle(
    fontFamily = FontFamily(Font(pretendard_semibold)),
    fontSize = 16.sp,
    lineHeight = 18.sp,
)

private val statTextStyle = TextStyle(
    fontFamily = FontFamily(Font(pretendard_semibold)),
    fontSize = 15.sp,
    lineHeight = 22.sp,
    color = gray500
)

@Preview
@Composable
fun MyInfoTotalXpPointContentPreview() {
    MyInfoTotalXpPointContent(
        UserInfo(
            accessToken = "",
            authorization = "",
            nickname = "김일상1234",
            email = "",
            profileImage = null,
            completeChallengeCount = 0,
            couponCount = 0,
            xpPoint = 16300,
            status = ""
        )
    )
}

@Preview
@Composable
fun MyInfoStatsXpPointContentPreview() {
    MyInfoStatsXpPointContent(
        UserInfo(
            accessToken = "",
            authorization = "",
            nickname = "김일상1234",
            email = "",
            profileImage = null,
            completeChallengeCount = 0,
            couponCount = 0,
            xpPoint = 15300,
            status = ""
        )
    )
}