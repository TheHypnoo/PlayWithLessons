package com.upitnik.playwithlessons.ui.subjects

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.core.extensions.shake
import com.upitnik.playwithlessons.core.extensions.shakeRotate
import com.upitnik.playwithlessons.data.model.subject.Subject
import com.upitnik.playwithlessons.databinding.ItemSubjectBinding

class SubjectAdapter(
    private var subject: List<Subject>,
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

    class SubjectViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemSubjectBinding.bind(view)

        fun render(subject: Subject, onSubjectClick: OnSubjectActionListener) {
            /*binding.root.animation = AnimationUtils.loadAnimation(
                binding.root.context,
                R.anim.grid_anim
            )*/
            binding.root.shakeRotate()
            binding.tvNameSubject.text = subject.name
            binding.pbSubject.progress = subject.number
            binding.tvDifficult.text = subject.difficult
            when (subject.difficult) {
                FACIL -> {
                    binding.ivDifficult.setImageResource(R.drawable.ic_difficulty_low)
                }
                NORMAL -> {
                    binding.ivDifficult.setImageResource(R.drawable.ic_difficulty_medium)
                }
                else -> {
                    binding.ivDifficult.setImageResource(R.drawable.ic_difficulty_high)
                }
            }

            Glide.with(binding.root.context).load(subject.image).into(binding.ivSubject)
            binding.root.setOnClickListener {
                onSubjectClick.onSubjectClicked(subject)
            }
        }

        companion object {
            const val FACIL = "Fàcil"
            const val NORMAL = "Normal"
            const val DIFICIL = "Difícil"
        }
    }

}