package com.upitnik.playwithlessons.ui.levels

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.databinding.FragmentMenuLevelsBinding

class MenuLevelsFragment : Fragment(R.layout.fragment_menu_levels) {

    private lateinit var binding: FragmentMenuLevelsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMenuLevelsBinding.bind(view)
        val listLevel = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10","11","12","13","14","15")
        binding.rvLevels.layoutManager = GridLayoutManager(this@MenuLevelsFragment.context, 5)
        val adapter = LevelsAdapter(listLevel)
        binding.rvLevels.adapter = adapter
    }
}