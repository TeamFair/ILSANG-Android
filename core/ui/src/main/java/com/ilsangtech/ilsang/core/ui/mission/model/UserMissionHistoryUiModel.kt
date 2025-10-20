package com.ilsangtech.ilsang.core.ui.mission.model

import android.os.Bundle
import androidx.navigation.NavType
import com.ilsangtech.ilsang.core.model.mission.MissionType
import com.ilsangtech.ilsang.core.model.mission.UserMissionHistory
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.core.util.DateConverter
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Serializable
data class UserMissionHistoryUiModel(
    val missionHistoryId: Int,
    val title: String,
    val questImageId: String?,
    val submitImageId: String,
    val viewCount: Int,
    val likeCount: Int,
    val createdAt: String,
    //TODO QuestType, MissionType을 도메인 모델에서 받을 수 있도록 적용 필요
    val questType: QuestType = QuestType.Repeat.Daily,
    val missionType: MissionType = MissionType.Photo
)

fun UserMissionHistory.toUiModel(): UserMissionHistoryUiModel {
    return UserMissionHistoryUiModel(
        missionHistoryId = missionHistoryId,
        title = title,
        questImageId = questImageId,
        submitImageId = submitImageId,
        viewCount = viewCount,
        likeCount = likeCount,
        createdAt = DateConverter.formatDate(input = createdAt)
    )
}

val userMissionHistoryNavType =
    object : NavType<UserMissionHistoryUiModel>(isNullableAllowed = false) {
        override fun get(
            bundle: Bundle,
            key: String
        ): UserMissionHistoryUiModel? {
            return bundle.getString(key)?.let { Json.decodeFromString(it) }
        }

        override fun parseValue(value: String): UserMissionHistoryUiModel {
            val decoded = URLDecoder.decode(value, StandardCharsets.UTF_8.toString())
            return Json.decodeFromString(decoded)
        }

        override fun put(
            bundle: Bundle,
            key: String,
            value: UserMissionHistoryUiModel
        ) {
            bundle.putString(
                key,
                Json.encodeToString(UserMissionHistoryUiModel.serializer(), value)
            )
        }

        override fun serializeAsValue(value: UserMissionHistoryUiModel): String {
            val json = Json.encodeToString(value)
            return URLEncoder.encode(json, StandardCharsets.UTF_8.toString())
        }
    }