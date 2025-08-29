package com.ilsangtech.ilsang.feature.submit.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.ilsangtech.ilsang.feature.submit.ImageSubmitScreen
import com.ilsangtech.ilsang.feature.submit.OxQuizSubmitScreen
import com.ilsangtech.ilsang.feature.submit.WordsQuizSubmitScreen
import kotlinx.serialization.Serializable

@Serializable
data object SubmitBaseRoute

@Serializable
data class ImageSubmitRoute(
    val questId: Int,
    val missionId: Int
)

@Serializable
data class OxQuizSubmitRoute(
    val questId: Int,
    val missionId: Int
)

@Serializable
data class WordsQuizSubmitRoute(
    val questId: Int,
    val missionId: Int
)

fun NavHostController.navigateToSubmit(
    questId: Int,
    missionId: Int,
    type: String
) {
    when (type) {
        "PHOTO" -> navigate(ImageSubmitRoute(questId, missionId))
        "OX" -> navigate(OxQuizSubmitRoute(questId, missionId))
        "WORDS" -> navigate(WordsQuizSubmitRoute(questId, missionId))
    }
}

fun NavGraphBuilder.submitNavigation(popBackStack: () -> Unit) {
    composable<ImageSubmitRoute> {
        ImageSubmitScreen(onDismiss = popBackStack)
    }
    composable<OxQuizSubmitRoute> {
        OxQuizSubmitScreen(onBackButtonClick = popBackStack)
    }
    composable<WordsQuizSubmitRoute> {
        WordsQuizSubmitScreen(onBackButtonClick = popBackStack)
    }
}