package com.ilsangtech.ilsang.feature.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.primary300
import com.ilsangtech.ilsang.designsystem.theme.primary500
import com.ilsangtech.ilsang.designsystem.theme.title02

@Composable
fun QuestCard(
    modifier: Modifier = Modifier,
    title: String,
    questLogo: @Composable () -> Unit,
    cardBackgroundColor: Color,
) {
    Card(
        modifier = modifier
            .size(width = 180.dp, height = 240.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardBackgroundColor,
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 27.dp)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = title02,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(24.dp))
            questLogo()
        }
    }
}

@Preview
@Composable
fun WeatherQuestCard() {
    QuestCard(
        title = stringResource(R.string.weather_quest_card_title),
        questLogo = {
            Column {
                Icon(
                    painter = painterResource(R.drawable.quest_card_weather),
                    tint = Color.Unspecified,
                    contentDescription = null
                )
                Spacer(Modifier.height(27.dp))
            }
        },
        cardBackgroundColor = primary,
    )
}

@Preview
@Composable
fun MusicQuestCard() {
    QuestCard(
        title = stringResource(R.string.music_quest_card_title),
        questLogo = {
            Column {
                Icon(
                    painter = painterResource(R.drawable.quest_card_music),
                    tint = Color.Unspecified,
                    contentDescription = null
                )
                Spacer(Modifier.height(27.dp))
            }
        },
        cardBackgroundColor = primary,
    )
}

@Preview
@Composable
fun CoffeeQuestCard() {
    QuestCard(
        title = stringResource(R.string.coffee_quest_card_title),
        questLogo = {
            Column {
                Icon(
                    painter = painterResource(R.drawable.quest_card_coffee),
                    tint = Color.Unspecified,
                    contentDescription = null
                )
                Spacer(Modifier.height(27.dp))
            }
        },
        cardBackgroundColor = primary300,
    )
}

@Preview
@Composable
fun EggQuestCard() {
    QuestCard(
        title = stringResource(R.string.egg_quest_card_title),
        questLogo = {
            Column(
                modifier = Modifier.offset(y = 3.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(7.dp))
                Icon(
                    modifier = Modifier.zIndex(1f),
                    painter = painterResource(R.drawable.quest_card_egg),
                    tint = Color.Unspecified,
                    contentDescription = null
                )
                Box(
                    modifier = Modifier
                        .offset(y = (-2).dp)
                        .size(
                            width = 12.dp,
                            height = 14.dp
                        )
                        .background(
                            color = gray300
                        )
                )
                Box(
                    modifier = Modifier
                        .offset(y = (-3).dp)
                        .size(
                            width = 16.dp,
                            height = 15.dp
                        )
                        .background(
                            color = Color(0xFFBC6100),
                            shape = RoundedCornerShape(
                                topStart = 4.dp,
                                topEnd = 4.dp
                            )
                        )
                )
            }
        },
        cardBackgroundColor = primary,
    )
}

@Preview
@Composable
fun WaterQuestCard() {
    QuestCard(
        title = stringResource(R.string.water_quest_card_title),
        questLogo = {
            Icon(
                painter = painterResource(R.drawable.quest_card_water),
                tint = Color.Unspecified,
                contentDescription = null
            )
            Spacer(Modifier.height(27.dp))
        },
        cardBackgroundColor = primary500
    )
}