package com.ilsangtech.ilsang.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Sort(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)