package com.upitnik.playwithlessons.ui.questions

import com.upitnik.playwithlessons.data.model.questions.AnswerData

interface OnQuestionActionListener {
    fun onAnswerClickedQuestions(answer: AnswerData)
}