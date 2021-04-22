package com.upitnik.playwithlessons.ui.relate.adapters

import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.data.model.relate.RelateAnswers

class RelateViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val btnAnswer: Button = view.findViewById(R.id.btnAnswer)

    fun render(
        answerData: RelateAnswers,
        onAnswerSelected: (RelateAnswers, Int) -> Unit
    ){
        btnAnswer.text = answerData.answerLeft[absoluteAdapterPosition].title
        btnAnswer.setOnClickListener {
            onAnswerSelected(answerData, absoluteAdapterPosition)
        }

    }
}