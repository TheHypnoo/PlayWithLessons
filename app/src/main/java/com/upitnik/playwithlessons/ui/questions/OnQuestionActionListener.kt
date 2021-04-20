package com.upitnik.playwithlessons.ui.questions

import com.upitnik.playwithlessons.data.model.questions.AnswerData
import com.upitnik.playwithlessons.data.model.relate.RelateAnswerData

interface OnQuestionActionListener {
    fun onAnswerClickedQuestions(answer: AnswerData)
    fun onAnswerClickedRelate(answer: RelateAnswerData)
}