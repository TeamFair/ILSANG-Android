package com.ilsangtech.ilsang.feature.banner

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.cachedIn
import androidx.paging.filter
import com.ilsangtech.ilsang.core.domain.QuestRepository
import com.ilsangtech.ilsang.feature.banner.navigation.BannerDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class BannerDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val questRepository: QuestRepository
) : ViewModel() {
    val bannerDetailInfo = savedStateHandle.toRoute<BannerDetailRoute>()

    private val _selectedQuestType = MutableStateFlow(BannerDetailQuestType.OnGoing)
    val selectedQuestType = _selectedQuestType.asStateFlow()

    private val _selectedSortType = MutableStateFlow(BannerDetailSortType.ExpiredDate)
    val selectedSortType = _selectedSortType.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val onGoingQuests = selectedSortType.flatMapLatest { sortType ->
        val orderExpiredDesc = if (sortType == BannerDetailSortType.ExpiredDate) true else null
        val orderRewardDesc = when (sortType) {
            BannerDetailSortType.PointDesc -> true
            BannerDetailSortType.PointAsc -> false
            else -> null
        }
        questRepository.getBannerQuests(
            bannerId = bannerDetailInfo.id,
            orderExpiredDesc = orderExpiredDesc,
            orderRewardDesc = orderRewardDesc
        ).map {
            it.filter { quest ->
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                inputFormat.timeZone = TimeZone.getTimeZone("UTC")
                val date = try {
                    inputFormat.parse(quest.expireDate)
                } catch (_: Exception) {
                    throw IllegalArgumentException("Invalid date format")
                }
                date.before(Date())
            }
        }
    }.cachedIn(viewModelScope)

    @OptIn(ExperimentalCoroutinesApi::class)
    val completedQuests = selectedSortType.flatMapLatest { sortType ->
        val orderExpiredDesc = if (sortType == BannerDetailSortType.ExpiredDate) true else null
        val orderRewardDesc = when (sortType) {
            BannerDetailSortType.PointDesc -> true
            BannerDetailSortType.PointAsc -> false
            else -> null
        }
        questRepository.getBannerQuests(
            bannerId = bannerDetailInfo.id,
            orderExpiredDesc = orderExpiredDesc,
            orderRewardDesc = orderRewardDesc
        ).map {
            it.filter { quest ->
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                inputFormat.timeZone = TimeZone.getTimeZone("UTC")
                val date = try {
                    inputFormat.parse(quest.expireDate)
                } catch (_: Exception) {
                    throw IllegalArgumentException("Invalid date format")
                }
                date.after(Date())
            }
        }
    }.cachedIn(viewModelScope)

    fun onQuestTypeChanged(questType: BannerDetailQuestType) {
        _selectedQuestType.update { questType }
    }

    fun onSortTypeChanged(sortType: BannerDetailSortType) {
        _selectedSortType.update { sortType }
    }
}