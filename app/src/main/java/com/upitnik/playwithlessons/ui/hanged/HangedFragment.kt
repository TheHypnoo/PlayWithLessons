package com.upitnik.playwithlessons.ui.hanged

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.databinding.FragmentHangedBinding
import com.upitnik.playwithlessons.databinding.FragmentLoginBinding

class HangedFragment : Fragment() {
    private var _binding: FragmentHangedBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       _binding = FragmentHangedBinding.inflate(inflater, container, false)
        return binding.root
    }
}