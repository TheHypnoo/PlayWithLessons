package com.upitnik.playwithlessons.ui.relate.adapters.concat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.upitnik.playwithlessons.core.BaseConcatHolder
import com.upitnik.playwithlessons.databinding.RelateRightButtonsRowBinding
import com.upitnik.playwithlessons.ui.relate.adapters.RelateAdapter

class RelateRightConcatAdapter(private val RelateAdapter: RelateAdapter) :
    RecyclerView.Adapter<BaseConcatHolder<*>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseConcatHolder<*> {
        val itemBinding =
            RelateRightButtonsRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConcatViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseConcatHolder<*>, position: Int) {
        when (holder) {
            is ConcatViewHolder -> holder.bind(RelateAdapter)
        }
    }

    override fun getItemCount(): Int = 1

    private inner class ConcatViewHolder(val binding: RelateRightButtonsRowBinding) :
        BaseConcatHolder<RelateAdapter>(binding.root) {
        override fun bind(adapter: RelateAdapter) {
            binding.rvUpcomingMovies.adapter = adapter
        }
    }

}