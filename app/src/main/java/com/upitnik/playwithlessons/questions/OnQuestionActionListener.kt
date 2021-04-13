package com.upitnik.playwithlessons.questions

import com.upitnik.playwithlessons.data.model.questions.AnswerData

interface OnQuestionActionListener {
    fun onAnswerClicked(answer: AnswerData)
}