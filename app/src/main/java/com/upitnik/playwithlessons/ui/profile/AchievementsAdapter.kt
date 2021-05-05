package com.upitnik.playwithlessons.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.data.model.auth.Achievement
import com.upitnik.playwithlessons.databinding.ItemAchievementsBinding

class AchievementsAdapter(private val listAchievements: List<Achievement>) :
    RecyclerView.Adapter<AchievementsAdapter.AchievementHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementHolder {
        val viewInflater = LayoutInflater.from(parent.context)
        return AchievementHolder(viewInflater.inflate(R.layout.item_achievements, parent, false))
    }

    override fun onBindViewHolder(holder: AchievementHolder, position: Int) {
        holder.render(listAchievements[position])
    }

    override fun getItemCount(): Int = listAchievements.size

    class AchievementHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemAchievementsBinding.bind(view)

        fun render(achievement: Achievement) {
            println(achievement.image)
            Glide.with(binding.root.context).load(achievement.image)
                .into(binding.civImageAchievement)
            binding.tvNameAchievement.text = achievement.name

        }
    }
}