package com.upitnik.playwithlessons.ui.relate.adapters.concat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.upitnik.playwithlessons.core.BaseConcatHolder
import com.upitnik.playwithlessons.databinding.RelateLeftButtonsRowBinding
import com.upitnik.playwithlessons.ui.relate.adapters.RelateAdapter

class RelateLeftConcatAdapter(private val RelateAdapter: RelateAdapter) :
    RecyclerView.Adapter<BaseConcatHolder<*>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseConcatHolder<*> {
        val itemBinding =
            RelateLeftButtonsRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConcatViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseConcatHolder<*>, position: Int) {
        when (holder) {
            is ConcatViewHolder -> holder.bind(RelateAdapter)
        }
    }

    override fun getItemCount(): Int = 1

    private inner class ConcatViewHolder(val binding: RelateLeftButtonsRowBinding) :
        BaseConcatHolder<RelateAdapter>(binding.root) {
        override fun bind(adapter: RelateAdapter) {
            binding.rvUpcomingMovies.adapter = adapter
        }
    }

}