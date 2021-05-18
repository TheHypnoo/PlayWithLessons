package com.upitnik.playwithlessons.ui.levels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.data.model.levels.Levels
import com.upitnik.playwithlessons.data.model.subject.Subject
import com.upitnik.playwithlessons.databinding.ItemLevelBinding

class LevelsAdapter(private val listLevels: List<Levels>, private val subject: Subject) :
    RecyclerView.Adapter<LevelsAdapter.LevelsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelsHolder {
        val viewInflater = LayoutInflater.from(parent.context)
        return LevelsHolder(viewInflater.inflate(R.layout.item_level, parent, false))
    }

    override fun onBindViewHolder(holder: LevelsHolder, position: Int) {
        holder.render(listLevels[position], subject)
    }

    override fun getItemCount(): Int = listLevels.size

    class LevelsHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemLevelBinding.bind(view)

        fun render(level: Levels, subject: Subject) {
            val bundle = Bundle()
            bundle.putSerializable(
                "Level",
                Levels(
                    level.difficulty,
                    level.id,
                    level.number,
                    level.subject
                )
            )
            bundle.putSerializable(
                "Subject",
                Subject(
                    subject.gamemode_id,
                    subject.id,
                    subject.image,
                    subject.name,
                    subject.number,
                    subject.pwluser_id,
                    subject.subject_id
                )
            )
            binding.btnLevel.text = level.number.toString()
            binding.btnLevel.setOnClickListener {
                binding.root.findNavController()
                    .navigate(R.id.action_menulevels_to_activityQuestions, bundle)
            }
        }
    }
}