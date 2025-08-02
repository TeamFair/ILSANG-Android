package com.ilsangtech.ilsang.core.model

data class Title(
    val id: String,
    val historyId: String? = null,
    val name: String,
    val type: String,
    val condition: String?,
    val createdAt: String
)
