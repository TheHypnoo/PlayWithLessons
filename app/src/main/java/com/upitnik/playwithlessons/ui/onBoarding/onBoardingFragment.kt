package com.upitnik.playwithlessons.ui.onBoarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.databinding.FragmentOnBoardingBinding


class onBoardingFragment : Fragment(R.layout.fragment_on_boarding) {

    private lateinit var binding: FragmentOnBoardingBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOnBoardingBinding.bind(view)

        binding.btnNextfragment.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardingFragment_to_loginFragment)
        }
    }
}