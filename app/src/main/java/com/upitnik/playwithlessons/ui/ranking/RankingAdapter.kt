package com.upitnik.playwithlessons.ui.ranking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.data.model.auth.UserItem
import com.upitnik.playwithlessons.databinding.ItemRankingBinding

class RankingAdapter(
    private val listUsers: List<UserItem>,
    private val OnUserClick: RankingFragment
) :
    RecyclerView.Adapter<RankingAdapter.RankingHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingHolder {
        val viewInflater = LayoutInflater.from(parent.context)
        return RankingHolder(viewInflater.inflate(R.layout.item_ranking, parent, false))
    }

    override fun onBindViewHolder(holder: RankingHolder, position: Int) {
        holder.render(listUsers[position], OnUserClick)
    }

    override fun getItemCount(): Int = listUsers.size

    class RankingHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemRankingBinding.bind(view)

        fun render(user: UserItem, OnUserClick: OnUserActionListener) {
            binding.tvUsername.text = user.nickname
            binding.tvScoreUser.text = user.score.toString()
            Glide.with(binding.root.context).load(user.image).into(binding.civImageUser)
            binding.root.setOnClickListener {
                OnUserClick.onUserClick(user)
            }

        }
    }
}