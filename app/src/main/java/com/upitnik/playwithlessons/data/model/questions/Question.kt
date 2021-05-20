package com.upitnik.playwithlessons.data.model.questions

import java.io.Serializable

data class Question(
    val answer: List<Answer>,
    val id: Int,
    val image: String?,
    val level: Int,
    var stagecorrect: Int,
    val statement: String,
    val subject: Int
) : Serializable