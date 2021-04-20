package com.upitnik.playwithlessons.ui.relate.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.data.model.relate.RelateAnswerData
import com.upitnik.playwithlessons.data.model.relate.RelateData

class RelateAdapter(
    val answer: List<RelateAnswerData>,
    val onAnswerSelected: (RelateAnswerData, Int) -> Unit
) : RecyclerView.Adapter<RelateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelateViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RelateViewHolder(layoutInflater.inflate(R.layout.item_answer, parent, false))
    }

    override fun getItemCount(): Int = answer.size

    override fun onBindViewHolder(holder: RelateViewHolder, position: Int) {
        val item = answer[position]
        holder.render(item, onAnswerSelected)

    }

}