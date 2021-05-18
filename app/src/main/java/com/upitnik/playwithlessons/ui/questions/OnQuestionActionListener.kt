package com.upitnik.playwithlessons.ui.questions

import com.upitnik.playwithlessons.data.model.questions.Answer

interface OnQuestionActionListener {
    fun onAnswerClickedQuestions(answer: Answer)
}