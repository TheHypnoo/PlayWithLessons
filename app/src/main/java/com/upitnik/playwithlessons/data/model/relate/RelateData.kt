package com.upitnik.playwithlessons.data.model.relate

import java.io.Serializable

data class RelateData(
    val answers: List<RelateAnswers>,
) : Serializable

data class RelateAnswers(
    val answerLeft: List<RelateAnswerLeft>,
    val answerRight: List<RelateAnswerRight>
)

data class RelateAnswerLeft(val title: String, val correct: String) : Serializable

data class RelateAnswerRight(val title: String, val correct: String) : Serializable
