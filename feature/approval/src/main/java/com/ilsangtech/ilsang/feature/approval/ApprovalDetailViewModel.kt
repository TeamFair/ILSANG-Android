package com.ilsangtech.ilsang.feature.approval

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.ilsangtech.ilsang.feature.approval.navigation.ApprovalDetailRoute
import com.ilsangtech.ilsang.feature.approval.navigation.missionHistoryUiModelTypeMap
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ApprovalDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val missionHistoryUiModel =
        savedStateHandle.toRoute<ApprovalDetailRoute>(missionHistoryUiModelTypeMap).missionHistory
}