package com.upitnik.playwithlessons.data.model.relate

import java.io.Serializable

data class RelateData(val title: String, val answers: List<RelateAnswerData>) : Serializable

data class RelateAnswerData(val title: String, val correct: String) : Serializable
