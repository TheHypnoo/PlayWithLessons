package com.upitnik.playwithlessons.ui.questions

import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.data.model.questions.Answer
import com.upitnik.playwithlessons.data.model.questions.AnswerData

class AnswerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val btnAnswer: Button = view.findViewById(R.id.btnAnswer)

    fun render(
        answerData: Answer,
        onAnswerSelected: (Answer, Int) -> Unit
    ){
        btnAnswer.text = answerData.name
        btnAnswer.setOnClickListener {
            onAnswerSelected(answerData, bindingAdapterPosition)
        }

    }
}