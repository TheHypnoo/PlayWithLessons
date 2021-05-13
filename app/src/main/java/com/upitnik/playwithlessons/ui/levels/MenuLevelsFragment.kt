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
import com.upitnik.playwithlessons.data.model.levels.Levels
import com.upitnik.playwithlessons.data.model.subject.SubjectsItem
import com.upitnik.playwithlessons.data.remote.mainMenu.MainMenuDataSource
import com.upitnik.playwithlessons.databinding.FragmentMenuLevelsBinding
import com.upitnik.playwithlessons.domain.mainMenu.MainMenuRepoImpl
import com.upitnik.playwithlessons.presentation.mainMenu.MainMenuViewModel
import com.upitnik.playwithlessons.presentation.mainMenu.MainMenuViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MenuLevelsFragment : Fragment(R.layout.fragment_menu_levels) {

    private lateinit var binding: FragmentMenuLevelsBinding
    private val levelsViewModel by viewModels<MainMenuViewModel> {
        MainMenuViewModelFactory(
            MainMenuRepoImpl(
                MainMenuDataSource()
            )
        )
    }
    private var subject: SubjectsItem? = null
    private var listLevels: ArrayList<Levels> = arrayListOf()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMenuLevelsBinding.bind(view)
        getLevels()
    }

    fun getLevels() {
        levelsViewModel.getLevels().observe(viewLifecycleOwner, Observer { result ->
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
                            LevelsAdapter(checkSubjectInLevel(result.data))
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

    fun checkSubjectInLevel(levels: List<Levels>): ArrayList<Levels> {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                if (subject != null) {
                    levels.forEach { level ->
                        if (level.subject == subject!!.id) {
                            listLevels.add(level)
                        }

                    }
                }
            }
        }
        return listLevels
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        subject = arguments?.getSerializable("Subject") as SubjectsItem?
    }

}