package com.ilsangtech.ilsang.feature.profile.component

import android.graphics.PointF
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.core.graphics.plus
import androidx.core.graphics.times
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath
import com.ilsangtech.ilsang.core.model.UserXpStats
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.heading02
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin

@Composable
internal fun UserStatsContent(
    modifier: Modifier = Modifier,
    userXpStats: UserXpStats
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            modifier = Modifier.padding(
                start = 4.dp,
                top = 2.dp
            ),
            text = "능력별 포인트",
            style = heading02.copy(color = gray400)
        )
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp)
        ) {
            UserStatsChart(userXpStats)
        }
    }
}

@Composable
private fun UserStatsChart(
    userXpStats: UserXpStats
) {
    val stats = listOf(
        userXpStats.strengthStat,     // 체력
        userXpStats.intellectStat,   // 지능
        userXpStats.charmStat,        // 매력
        userXpStats.funStat,         // 재미
        userXpStats.sociabilityStat, // 사회성
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var selectedStat: String? by remember { mutableStateOf(null) }
        StatText(
            modifier = Modifier.padding(bottom = 1.dp),
            iconRes = R.drawable.strength,
            title = "체력",
            xpPoint = userXpStats.strengthStat,
            isSelected = selectedStat == "체력",
            onClick = { selectedStat = "체력" },
            onDismiss = { selectedStat = null }
        )
        Row {
            StatText(
                modifier = Modifier.offset(y = 54.dp),
                iconRes = R.drawable.social,
                title = "사회성",
                xpPoint = userXpStats.sociabilityStat,
                isSelected = selectedStat == "사회성",
                onClick = { selectedStat = "사회성" },
                onDismiss = { selectedStat = null }
            )
            Box(
                modifier = Modifier
                    .size(185.dp)
                    .drawWithCache {
                        val center = size.center
                        val radius = size.minDimension / 2f
                        val angleStep = 360f / 5

                        val normalizationBase = max(stats.max() + 10, 50)
                        val normalizedStats = stats.map {
                            (it + 5).coerceIn(5, normalizationBase).toFloat() / normalizationBase
                        }

                        val vertices = normalizedStats.mapIndexed { index, rawValue ->
                            val value = rawValue.coerceIn(0.15f, 1f)
                            listOf(
                                radialToCartesian(
                                    value * radius,
                                    (angleStep * index).toRadians(),
                                    PointF(center.x, center.y)
                                ).x,
                                radialToCartesian(
                                    value * radius,
                                    (angleStep * index).toRadians(),
                                    PointF(center.x, center.y)
                                ).y
                            )
                        }.flatten().toFloatArray()


                        val statsPolygon = if (vertices.size >= 6) {
                            RoundedPolygon(
                                vertices = vertices,
                                rounding = CornerRounding(radius = 5.dp.toPx()),
                                centerX = size.center.x,
                                centerY = size.center.y,
                            )
                        } else {
                            null
                        }

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
                            rotate(degrees = -90f) {
                                statsPolygon?.let {
                                    drawPath(
                                        path = statsPolygon.toPath().asComposePath(),
                                        color = primary.copy(alpha = 0.8f),
                                    )
                                }
                            }
                        }
                    }
            )
            StatText(
                modifier = Modifier.padding(top = 54.dp),
                iconRes = R.drawable.bulb,
                title = "지능",
                xpPoint = userXpStats.intellectStat,
                isSelected = selectedStat == "지능",
                onClick = { selectedStat = "지능" },
                onDismiss = { selectedStat = null }
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
                title = "재미",
                xpPoint = userXpStats.funStat,
                isSelected = selectedStat == "재미",
                onClick = { selectedStat = "재미" },
                onDismiss = { selectedStat = null }
            )
            StatText(
                iconRes = R.drawable.heart,
                title = "매력",
                xpPoint = userXpStats.charmStat,
                isSelected = selectedStat == "매력",
                onClick = { selectedStat = "매력" },
                onDismiss = { selectedStat = null }
            )
        }
    }
}

@Composable
private fun StatText(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    title: String,
    xpPoint: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    onDismiss: () -> Unit
) {
    val statTextStyle = remember {
        TextStyle(
            fontFamily = pretendardFontFamily,
            fontSize = 15.sp,
            lineHeight = 22.sp,
            color = gray500
        )
    }
    val statPopUpTextStyle = remember {
        TextStyle(
            fontFamily = pretendardFontFamily,
            fontSize = 13.sp,
            lineHeight = 20.sp,
            color = Color.White
        )
    }

    Box {
        if (isSelected) {
            Popup(
                alignment = Alignment.TopCenter,
                onDismissRequest = onDismiss,
                offset = IntOffset(
                    x = 0,
                    y = if (title == "사회성" || title == "지능") 46 else -112
                )
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(primary)
                            .padding(horizontal = 8.dp)
                            .height(35.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(text = title, style = statPopUpTextStyle)
                            Text(text = "$xpPoint", style = statPopUpTextStyle)
                            Text(text = "P", style = statPopUpTextStyle)
                        }
                    }
                    Box(
                        modifier = Modifier
                            .size(
                                width = 22.dp,
                                height = 28.dp
                            )
                            .offset(y = (-16).dp)
                            .drawWithCache {
                                val triangle = RoundedPolygon(
                                    numVertices = 3,
                                    radius = size.width / 2,
                                    centerX = size.center.x,
                                    centerY = size.center.y,
                                    rounding = CornerRounding(2.dp.toPx())
                                )
                                onDrawBehind {
                                    rotate(90f) {
                                        drawPath(
                                            path = triangle.toPath().asComposePath(),
                                            color = primary
                                        )
                                    }
                                }
                            }
                    )
                }
            }
        }
        Row(
            modifier = modifier
                .height(30.dp)
                .clickable(
                    onClick = onClick,
                    indication = null,
                    interactionSource = null
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = title,
                style = statTextStyle
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UserStatsContentPreview() {
    UserStatsContent(
        userXpStats = UserXpStats(
            charmStat = 0,
            funStat = 0,
            strengthStat = 100,
            sociabilityStat = 0,
            intellectStat = 0
        )
    )
}

private fun Float.toRadians() = this * PI.toFloat() / 180f

private val PointZero = PointF(0f, 0f)
private fun radialToCartesian(
    radius: Float,
    angleRadians: Float,
    center: PointF = PointZero
) = directionVectorPointF(angleRadians) * radius + center

private fun directionVectorPointF(angleRadians: Float) =
    PointF(cos(angleRadians), sin(angleRadians))

