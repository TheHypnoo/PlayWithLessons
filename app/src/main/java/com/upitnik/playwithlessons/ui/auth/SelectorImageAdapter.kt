package com.upitnik.playwithlessons.ui.auth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.data.model.auth.imageUser
import com.upitnik.playwithlessons.databinding.ItemPhotoBinding


class SelectorImageAdapter(
    private val listLevels: List<imageUser>,
    private val OnImageClick: OnImageActionListener
) :
    RecyclerView.Adapter<SelectorImageAdapter.SelectorImageHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectorImageHolder {
        val viewInflater = LayoutInflater.from(parent.context)
        return SelectorImageHolder(viewInflater.inflate(R.layout.item_photo, parent, false))
    }

    override fun onBindViewHolder(holder: SelectorImageHolder, position: Int) {
        holder.render(listLevels[position], OnImageClick)
    }

    override fun getItemCount(): Int = listLevels.size

    class SelectorImageHolder(
        private val view: View
    ) : RecyclerView.ViewHolder(view) {
        val binding = ItemPhotoBinding.bind(view)

        fun render(imageUser: imageUser, OnImageClick: OnImageActionListener) {
            Glide.with(binding.root.context).load(imageUser.photo_url).into(binding.civImage)

            binding.civImage.setOnClickListener {
                OnImageClick.onImageClick(imageUser)
            }
        }
    }
}