package com.upitnik.playwithlessons.ui.Subjects

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.data.model.subject.SubjectsItem
import com.upitnik.playwithlessons.databinding.ItemSubjectBinding

class SubjectAdapter(
    private var subject: List<SubjectsItem>,
    private val OnSubjectClick: OnSubjectActionListener
) : RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SubjectViewHolder(layoutInflater.inflate(R.layout.item_subject, parent, false))
    }

    override fun getItemCount(): Int = subject.size

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val item = subject[position]
        holder.render(item, OnSubjectClick)
    }

    class SubjectViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemSubjectBinding.bind(view)

        fun render(subject: SubjectsItem, onSubjectClick: OnSubjectActionListener) {
            binding.tvNameSubject.text = subject.name
            Glide.with(binding.root.context).load(subject.image).into(binding.ivSubject)

            /*Te lleva al apartado de niveles enviandole la asignatura para que ya desde el
                Fragment Menu Levels carge los niveles
             */
            binding.root.setOnClickListener {
                onSubjectClick.onSubjectClicked(subject)
            }
        }
    }

}