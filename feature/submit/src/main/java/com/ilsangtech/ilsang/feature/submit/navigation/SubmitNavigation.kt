package com.ilsangtech.ilsang.feature.submit.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.ilsangtech.ilsang.feature.submit.ImageCaptureScreen
import com.ilsangtech.ilsang.feature.submit.ImageSubmitScreen
import com.ilsangtech.ilsang.feature.submit.OxQuizSubmitScreen
import com.ilsangtech.ilsang.feature.submit.WordsQuizSubmitScreen
import kotlinx.serialization.Serializable

@Serializable
data object ImageSubmitBaseRoute

@Serializable
data class ImageCaptureRoute(
    val questId: Int,
    val missionId: Int
)

@Serializable
data class ImageSubmitRoute(
    val imageUri: String,
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
        "PHOTO" -> navigate(ImageCaptureRoute(questId, missionId))
        "OX" -> navigate(OxQuizSubmitRoute(questId, missionId))
        "WORDS" -> navigate(WordsQuizSubmitRoute(questId, missionId))
    }
}

fun NavHostController.navigateToImageSubmit(
    imageUri: String,
    questId: Int,
    missionId: Int
) = navigate(ImageSubmitRoute(imageUri, questId, missionId))

fun NavGraphBuilder.submitNavigation(
    navigateToImageSubmit: (String, Int, Int) -> Unit,
    navigateToHomeOrQuest: () -> Unit,
    popBackStack: () -> Unit
) {
    navigation<ImageSubmitBaseRoute>(startDestination = ImageCaptureRoute::class) {
        composable<ImageCaptureRoute> {
            ImageCaptureScreen(
                navigateToImageSubmit = navigateToImageSubmit,
                popBackStack = popBackStack
            )
        }
        composable<ImageSubmitRoute> {
            ImageSubmitScreen(
                onSubmitSuccess = navigateToHomeOrQuest,
                popBackStack = popBackStack
            )
        }
    }
    composable<OxQuizSubmitRoute> {
        OxQuizSubmitScreen(onBackButtonClick = popBackStack)
    }
    composable<WordsQuizSubmitRoute> {
        WordsQuizSubmitScreen(onBackButtonClick = popBackStack)
    }
}