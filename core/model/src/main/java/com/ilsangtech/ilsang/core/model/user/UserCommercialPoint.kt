package com.ilsangtech.ilsang.core.model.user

data class UserCommercialPoint(
    val topCommercialArea: TopCommercialArea,
    val totalOwnerContributions: List<TotalOwnerContribution>
)
