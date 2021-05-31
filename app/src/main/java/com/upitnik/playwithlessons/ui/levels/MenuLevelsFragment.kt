package com.upitnik.playwithlessons.ui.levels

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.core.Result
import com.upitnik.playwithlessons.core.extensions.gone
import com.upitnik.playwithlessons.core.extensions.invisible
import com.upitnik.playwithlessons.core.extensions.visible
import com.upitnik.playwithlessons.data.model.subject.Subject
import com.upitnik.playwithlessons.data.remote.levels.LevelsDataSource
import com.upitnik.playwithlessons.databinding.FragmentMenuLevelsBinding
import com.upitnik.playwithlessons.domain.levels.LevelsRepoImpl
import com.upitnik.playwithlessons.presentation.levels.LevelsViewModel
import com.upitnik.playwithlessons.presentation.levels.LevelsViewModelFactory

class MenuLevelsFragment : Fragment(R.layout.fragment_menu_levels) {

    private lateinit var binding: FragmentMenuLevelsBinding
    private val levelsViewModel by viewModels<LevelsViewModel> {
        LevelsViewModelFactory(
            LevelsRepoImpl(
                LevelsDataSource()
            )
        )
    }
    private var subject: Subject? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMenuLevelsBinding.bind(view)
        getLevels()
    }

    fun getLevels() {
        levelsViewModel.getLevels(subject!!.id).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    binding.pbSelectLevels.visible()
                    binding.rvLevels.invisible()
                }
                is Result.Sucess -> {
                    binding.pbSelectLevels.gone()
                    binding.rvLevels.visible()
                    if (result.data.isNotEmpty()) {
                        binding.rvLevels.layoutManager =
                            GridLayoutManager(this@MenuLevelsFragment.context, 5)
                        binding.rvLevels.adapter =
                            LevelsAdapter(result.data, subject!!)
                    }
                }
                is Result.Failure -> {
                    binding.pbSelectLevels.visible()
                    binding.rvLevels.invisible()
                    Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        subject = arguments?.getSerializable("Subject") as Subject?
    }

}