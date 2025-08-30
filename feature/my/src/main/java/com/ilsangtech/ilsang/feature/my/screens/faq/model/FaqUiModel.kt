package com.ilsangtech.ilsang.feature.my.screens.faq.model

import kotlinx.serialization.Serializable

@Serializable
internal data class FaqUiModel(
    val question: String,
    val answer: String
)