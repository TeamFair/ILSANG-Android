package com.ilsangtech.ilsang.feature.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
            .size(
                width = 180.dp,
                height = 240.dp
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardBackgroundColor,
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
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
fun NightQuestCard() {
    QuestCard(
        title = stringResource(R.string.night_quest_card_title),
        questLogo = {
            Icon(
                painter = painterResource(R.drawable.quest_card_night),
                tint = Color.Unspecified,
                contentDescription = null
            )
        },
        cardBackgroundColor = primary,
    )
}

@Preview
@Composable
fun LunchQuestCard() {
    QuestCard(
        title = stringResource(R.string.lunch_quest_card_title),
        questLogo = {
            Column {
                Icon(
                    painter = painterResource(R.drawable.quest_card_lunch),
                    tint = Color.Unspecified,
                    contentDescription = null
                )
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
            }
        },
        cardBackgroundColor = primary300,
    )
}

@Preview
@Composable
fun EmojiQuestCard() {
    QuestCard(
        title = stringResource(R.string.emoji_quest_card_title),
        questLogo = {
            Icon(
                modifier = Modifier.zIndex(1f),
                painter = painterResource(R.drawable.quest_card_emoji),
                tint = Color.Unspecified,
                contentDescription = null
            )
        },
        cardBackgroundColor = primary,
    )
}

@Preview
@Composable
fun TakeoutQuestCard() {
    QuestCard(
        title = stringResource(R.string.takeout_quest_card_title),
        questLogo = {
            Icon(
                painter = painterResource(R.drawable.quest_card_takeout),
                tint = Color.Unspecified,
                contentDescription = null
            )
        },
        cardBackgroundColor = primary500
    )
}