package com.upitnik.playwithlessons.ui.questions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.data.model.questions.Answer

class AnswerAdapter(
    private val answer: List<Answer>,
    private val onAnswerSelected: (Answer, Int) -> Unit
) : RecyclerView.Adapter<AnswerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AnswerViewHolder(layoutInflater.inflate(R.layout.item_answer, parent, false))
    }

    override fun getItemCount(): Int = answer.size

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        val item = answer[position]
        holder.render(item, onAnswerSelected)
    }

}