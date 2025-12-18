package com.ilsangtech.ilsang.feature.approval.model

enum class CommentAlertUiState(private val text: String) {
    TooLong("300자 이하로 작성해 주세요."),
    Empty("공백 제외 1자 이상 입력하세요."),
    Invalid("허용되지 않은 문자가 포함되어 있습니다."),
    SpamPrevention("스팸 방지를 위해 동일 게시물에는 1분 뒤에 다시 댓글을 작성할 수 있어요.");

    override fun toString(): String = text
}