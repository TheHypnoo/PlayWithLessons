package com.upitnik.playwithlessons.data.model.questions

import java.io.Serializable

data class Answer(
    val correct: Int,
    val id: Int,
    val name: String,
    val questions_id: Int
) : Serializable