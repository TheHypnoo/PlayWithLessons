package com.upitnik.playwithlessons.ui.relate.adapters

import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.data.model.relate.RelateAnswerData
import com.upitnik.playwithlessons.data.model.relate.RelateData

class RelateViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val btnAnswer: Button = view.findViewById(R.id.btnAnswer)

    fun render(
        answerData: RelateAnswerData,
        onAnswerSelected: (RelateAnswerData, Int) -> Unit
    ){
        btnAnswer.text = answerData.title
        btnAnswer.setOnClickListener {
            onAnswerSelected(answerData, adapterPosition)
        }

    }
}