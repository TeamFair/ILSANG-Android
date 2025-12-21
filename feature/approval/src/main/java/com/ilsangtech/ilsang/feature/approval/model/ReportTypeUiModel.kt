package com.ilsangtech.ilsang.feature.approval.model

enum class ReportTypeUiModel(private val uiText: String) {
    ABUSE("욕설/비방"),
    ADVERTISEMENT("광고/홍보"),
    ILLEGAL("음란/불법"),
    SPAM("스팸"),
    ETC("기타");

    override fun toString(): String = uiText
}
