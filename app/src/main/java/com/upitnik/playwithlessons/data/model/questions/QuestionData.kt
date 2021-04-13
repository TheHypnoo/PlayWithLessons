package com.upitnik.playwithlessons.data.model.questions

import java.io.Serializable

data class QuestionData(val header:String, val title:String, val answers:List<AnswerData>):Serializable
data class AnswerData(val title:String, val isCorrect:Boolean):Serializable


