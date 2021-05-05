package com.upitnik.playwithlessons.ui.levels

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.databinding.ItemLevelBinding

class LevelsAdapter(private val listLevels: List<String>) :
    RecyclerView.Adapter<LevelsAdapter.LevelsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelsHolder {
        val viewInflater = LayoutInflater.from(parent.context)
        return LevelsHolder(viewInflater.inflate(R.layout.item_level, parent, false))
    }

    override fun onBindViewHolder(holder: LevelsHolder, position: Int) {
        holder.render(listLevels[position])
    }

    override fun getItemCount(): Int = listLevels.size

    class LevelsHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemLevelBinding.bind(view)

        fun render(button: String) {
            binding.btnLevel.text = button
            binding.btnLevel.setOnClickListener {
                binding.root.findNavController()
                    .navigate(R.id.action_menulevels_to_activityQuestions)
            }
        }
    }
}