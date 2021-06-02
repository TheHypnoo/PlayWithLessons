package com.upitnik.playwithlessons.ui.onBoarding.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.core.extensions.load
import com.upitnik.playwithlessons.databinding.FragmentFirstScreenBinding

class FirstScreen : Fragment(R.layout.fragment_first_screen) {
    private lateinit var binding: FragmentFirstScreenBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        binding = FragmentFirstScreenBinding.bind(view)
        //binding.ivMain.load("https://i.ytimg.com/vi/tQC5ZZbPWP4/maxresdefault.jpg")

        binding.tvNext.setOnClickListener {
            viewPager?.currentItem = 1
        }
    }
}