package com.ilsangtech.ilsang.feature.submit.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.ilsangtech.ilsang.core.model.mission.MissionType
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
    val missionId: Int,
    val isIsZoneQuest: Boolean
)

@Serializable
data class ImageSubmitRoute(
    val imageUri: String,
    val questId: Int,
    val missionId: Int,
    val isIsZoneQuest: Boolean
)

@Serializable
data class OxQuizSubmitRoute(
    val questId: Int,
    val missionId: Int,
    val isIsZoneQuest: Boolean
)

@Serializable
data class WordsQuizSubmitRoute(
    val questId: Int,
    val missionId: Int,
    val isIsZoneQuest: Boolean
)

fun NavHostController.navigateToSubmit(
    questId: Int,
    missionId: Int,
    type: MissionType,
    isIsZoneQuest: Boolean
) {
    when (type) {
        MissionType.Photo -> navigate(ImageCaptureRoute(questId, missionId, isIsZoneQuest))
        MissionType.Ox -> navigate(OxQuizSubmitRoute(questId, missionId, isIsZoneQuest))
        MissionType.Words -> navigate(WordsQuizSubmitRoute(questId, missionId, isIsZoneQuest))
    }
}

fun NavHostController.navigateToImageSubmit(
    imageUri: String,
    questId: Int,
    missionId: Int,
    isIsZoneQuest: Boolean
) = navigate(ImageSubmitRoute(imageUri, questId, missionId, isIsZoneQuest))

fun NavGraphBuilder.submitNavigation(
    navigateToImageSubmit: (String, Int, Int, Boolean) -> Unit,
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