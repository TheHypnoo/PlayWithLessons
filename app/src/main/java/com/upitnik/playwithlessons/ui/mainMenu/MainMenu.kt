package com.upitnik.playwithlessons.ui.mainMenu

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.core.Result
import com.upitnik.playwithlessons.data.model.auth.UserItem
import com.upitnik.playwithlessons.data.model.subject.SubjectsItem
import com.upitnik.playwithlessons.data.remote.MainMenu.MainMenuDataSource
import com.upitnik.playwithlessons.databinding.FragmentMainMenuBinding
import com.upitnik.playwithlessons.domain.mainMenu.MainMenuRepoImpl
import com.upitnik.playwithlessons.presentation.mainMenu.MainMenuViewModel
import com.upitnik.playwithlessons.presentation.mainMenu.MainMenuViewModelFactory
import com.upitnik.playwithlessons.ui.Subjects.OnSubjectActionListener
import com.upitnik.playwithlessons.ui.Subjects.SubjectAdapter


class MainMenu : Fragment(R.layout.fragment_main_menu), OnSubjectActionListener {
    private lateinit var binding: FragmentMainMenuBinding
    private val viewModel by viewModels<MainMenuViewModel> {
        MainMenuViewModelFactory(
            MainMenuRepoImpl(
                MainMenuDataSource()
            )
        )
    }
    private var user: UserItem? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainMenuBinding.bind(view)
        if (user != null) {
            Glide.with(binding.root.context).load(user!!.image).into(binding.civUser)
            binding.tvUser.text = user!!.nickname
            binding.lpiProgress.progress = user!!.experience

            binding.tvLevelUser.text = checkExperience(user!!.experience)
            println("User encontrado")
        }
        getSubjects()
        getUser()
        binding.btnRanking.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenu_to_Ranking)
        }
        binding.mcvUser.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenu_to_Profile)
        }
        binding.civInstagram.setOnClickListener {
            val uri: Uri = Uri.parse("http://instagram.com/_u/upitnikofficial")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.instagram.android")

            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {

                //No encontró la aplicación, abre la versión web.
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://instagram.com/upitnikofficial")
                    )
                )
            }
        }
        binding.civTwitter.setOnClickListener {
            val uri: Uri = Uri.parse("http://twitter.com/UpitnikOfficial")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.twitter.android")

            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {

                //No encontró la aplicación, abre la versión web.
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://twitter.com/UpitnikOfficial")
                    )
                )
            }
        }
        binding.civWeb.setOnClickListener {
            Snackbar.make(binding.root, "Proximamente...", Snackbar.LENGTH_LONG)
                .setTextColor(Color.GREEN).show()
        }

    }

    private fun getUser() {
        viewModel.getUser().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    binding.pbProfile.visibility = View.VISIBLE
                    binding.mcvUser.visibility = View.GONE
                }
                is Result.Sucess -> {
                    binding.pbProfile.visibility = View.GONE
                    binding.mcvUser.visibility = View.VISIBLE
                    Toast.makeText(
                        requireContext(),
                        "Welcome ${result.data.nickname}",
                        Toast.LENGTH_SHORT
                    ).show()
                    user = result.data
                    Glide.with(binding.root.context).load(user!!.image).into(binding.civUser)
                    binding.tvUser.text = user!!.nickname
                    binding.lpiProgress.progress = user!!.experience

                    binding.tvLevelUser.text = checkExperience(user!!.experience)
                }
                is Result.Failure -> {
                    binding.pbProfile.visibility = View.VISIBLE
                    binding.mcvUser.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun getSubjects() {
        viewModel.getSubjects().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    binding.pbSubjescts.visibility = View.VISIBLE
                    binding.rvSubjectMainMenu.visibility = View.GONE
                }
                is Result.Sucess -> {
                    binding.pbSubjescts.visibility = View.GONE
                    binding.rvSubjectMainMenu.visibility = View.VISIBLE
                    /* Toast.makeText(
                         requireContext(),
                         "Welcome ${result.data}",
                         Toast.LENGTH_SHORT
                     ).show()*/
                    if (result.data.isNotEmpty()) {
                        binding.rvSubjectMainMenu.layoutManager =
                            GridLayoutManager(this@MainMenu.context, 2)
                        binding.rvSubjectMainMenu.adapter =
                            SubjectAdapter(result.data, this@MainMenu)
                    }
                }
                is Result.Failure -> {
                    binding.pbSubjescts.visibility = View.VISIBLE
                    binding.rvSubjectMainMenu.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    override fun onSubjectClicked(Subject: SubjectsItem) {
        binding.root.findNavController()
            .navigate(R.id.action_mainMenu_to_menulevels)
    }

    private fun checkExperience(experiencie: Int): String {
        if (experiencie == 0) {
            return "Nivel 0"
        }
        return "Nivel 0"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        user = arguments?.getSerializable("User") as UserItem?
    }

}