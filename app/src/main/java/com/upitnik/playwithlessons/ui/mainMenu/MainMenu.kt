package com.upitnik.playwithlessons.ui.mainMenu

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
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
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.application.AppConstants
import com.upitnik.playwithlessons.core.Result
import com.upitnik.playwithlessons.core.extensions.*
import com.upitnik.playwithlessons.data.model.auth.UserItem
import com.upitnik.playwithlessons.data.model.difficulty.Difficulty
import com.upitnik.playwithlessons.data.model.levels.Levels
import com.upitnik.playwithlessons.data.model.progress.Progress
import com.upitnik.playwithlessons.data.model.subject.SubjectsItem
import com.upitnik.playwithlessons.data.remote.mainMenu.MainMenuDataSource
import com.upitnik.playwithlessons.databinding.FragmentMainMenuBinding
import com.upitnik.playwithlessons.domain.mainMenu.MainMenuRepoImpl
import com.upitnik.playwithlessons.presentation.mainMenu.MainMenuViewModel
import com.upitnik.playwithlessons.presentation.mainMenu.MainMenuViewModelFactory
import com.upitnik.playwithlessons.ui.subjects.OnSubjectActionListener
import com.upitnik.playwithlessons.ui.subjects.SubjectAdapter
import kotlinx.coroutines.*


class MainMenu : Fragment(R.layout.fragment_main_menu), OnSubjectActionListener {
    private lateinit var binding: FragmentMainMenuBinding
    private val mainMenuViewModel by viewModels<MainMenuViewModel> {
        MainMenuViewModelFactory(
            MainMenuRepoImpl(
                MainMenuDataSource()
            )
        )
    }
    private var user: UserItem? = null
    private var listSubjects: List<SubjectsItem> = listOf()
    private var listProgress: List<Progress> = listOf()
    private var listDifficulty: List<Difficulty> = listOf()
    private var listLevels: List<Levels> = listOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainMenuBinding.bind(view)
        if (user != null) {
            binding.civUser.load(user!!.image)
            binding.tvUser.text = user!!.nickname
            binding.lpiProgress.progress = user!!.experience

            binding.tvLevelUser.text = checkExperience(user!!.experience)
        }
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Main) {
                getUser()
                getSubjects()
                getProgress()
                getLevels()
            }
        }
        //getDifficulty()
        initClickListener()
        checkProgressInSubject()

    }

    private fun initClickListener() {
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
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("${AppConstants.BASE_URL}")
                )
            )
        }
    }

    private suspend fun getUser() {
        mainMenuViewModel.getUser().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    binding.pbProfile.visible()
                    binding.mcvUser.invisible()
                }
                is Result.Sucess -> {
                    binding.pbProfile.gone()
                    binding.mcvUser.visible()
                    user = result.data
                    binding.civUser.load(user!!.image)
                    binding.tvUser.text = user!!.nickname
                    binding.lpiProgress.progress = checkProgressExperience(user!!.experience)
                    binding.tvLevelUser.text = checkExperience(user!!.experience)
                }
                is Result.Failure -> {
                    binding.pbProfile.visible()
                    binding.mcvUser.invisible()
                    Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private suspend fun getSubjects() {
        mainMenuViewModel.getSubjects().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    binding.pbSubjescts.visible()
                    binding.rvSubjectMainMenu.invisible()
                }
                is Result.Sucess -> {
                    /*binding.pbSubjescts.gone()
                    binding.rvSubjectMainMenu.visible()*/
                    if (result.data.isNotEmpty()) {
                        listSubjects = result.data
                        /*
                        binding.rvSubjectMainMenu.layoutManager =
                            GridLayoutManager(this@MainMenu.context, 2)
                        binding.rvSubjectMainMenu.adapter =
                            SubjectAdapter(result.data, this@MainMenu)*/
                    }
                }
                is Result.Failure -> {
                    binding.pbSubjescts.visible()
                    binding.rvSubjectMainMenu.invisible()
                    Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun getLevels() {
        mainMenuViewModel.getLevels().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    /* binding.pbSubjescts.visibility = View.VISIBLE
                     binding.rvSubjectMainMenu.visibility = View.INVISIBLE*/
                }
                is Result.Sucess -> {
                    /*binding.pbSubjescts.visibility = View.GONE
                    binding.rvSubjectMainMenu.visibility = View.VISIBLE*/
                    /* Toast.makeText(
                         requireContext(),
                         "Welcome ${result.data}",
                         Toast.LENGTH_SHORT
                     ).show()*/
                    if (result.data.isNotEmpty()) {

                        /*binding.rvSubjectMainMenu.layoutManager =
                            GridLayoutManager(this@MainMenu.context, 2)
                        binding.rvSubjectMainMenu.adapter =
                            SubjectAdapter(result.data, this@MainMenu)*/
                    }
                }
                is Result.Failure -> {
                    /*binding.pbSubjescts.visibility = View.VISIBLE
                    binding.rvSubjectMainMenu.visibility = View.INVISIBLE*/
                    Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun getDifficulty() {
        mainMenuViewModel.getDifficulty().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    /* binding.pbSubjescts.visibility = View.VISIBLE
                     binding.rvSubjectMainMenu.visibility = View.INVISIBLE*/
                }
                is Result.Sucess -> {
                    /*binding.pbSubjescts.visibility = View.GONE
                    binding.rvSubjectMainMenu.visibility = View.VISIBLE*/
                    /* Toast.makeText(
                         requireContext(),
                         "Welcome ${result.data}",
                         Toast.LENGTH_SHORT
                     ).show()*/
                    if (result.data.isNotEmpty()) {
                        listDifficulty = result.data
                        /*binding.rvSubjectMainMenu.layoutManager =
                            GridLayoutManager(this@MainMenu.context, 2)
                        binding.rvSubjectMainMenu.adapter =
                            SubjectAdapter(result.data, this@MainMenu)*/
                    }
                }
                is Result.Failure -> {
                    /*binding.pbSubjescts.visibility = View.VISIBLE
                    binding.rvSubjectMainMenu.visibility = View.INVISIBLE*/
                    Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private suspend fun getProgress() {
        mainMenuViewModel.getProgress().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    binding.pbSubjescts.visible()
                    binding.rvSubjectMainMenu.invisible()
                }
                is Result.Sucess -> {
                    binding.pbSubjescts.gone()
                    binding.rvSubjectMainMenu.visible()
                    if (result.data.isNotEmpty()) {
                        listProgress = result.data
                    }
                }
                is Result.Failure -> {
                    binding.pbSubjescts.visible()
                    binding.rvSubjectMainMenu.invisible()
                    binding.root.context.toast("Error ${result.exception}")
                }
            }
        })
    }

    override fun onSubjectClicked(Subject: SubjectsItem) {
        val bundle = Bundle()
        bundle.putSerializable(
            "Subject",
            SubjectsItem(
                Subject.gamemode,
                Subject.id,
                Subject.image,
                Subject.name,
                Subject.progress,
                Subject.difficulty
            )
        )
        binding.root.findNavController()
            .navigate(R.id.action_mainMenu_to_menulevels, bundle)
    }

    private fun checkExperience(experiencie: Int): String {
        return when (experiencie.toString().length) {
            //5
            1 -> "Nivel 0"
            //25
            2 -> "Nivel 0"
            //100
            3 -> "Nivel ${experiencie.toString().substring(0, 1)}"
            //1001
            4 -> "Nivel ${experiencie.toString().substring(0, 2)}"
            //10001
            5 -> "Nivel ${experiencie.toString().substring(0, 3)}"
            else -> "Nivel 0"
        }
    }

    private fun checkProgressExperience(experiencie: Int): Int {
        return when (experiencie.toString().length) {
            //5
            1 -> experiencie
            //25
            2 -> experiencie
            //105
            3 -> experiencie.toString().substring(1).toInt()
            //1001
            4 -> experiencie.toString().substring(2, 4).toInt()
            //10001
            5 -> experiencie.toString().substring(3, 5).toInt()
            else -> 0
        }
    }

    private fun checkProgressInSubject() {
        if (!listSubjects.isNull() && !listProgress.isNull() && !user.isNull()) {
            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.IO) {
                    listProgress.forEach { progress ->
                        listSubjects.forEach { subjects ->
                            if (progress.subject == subjects.id) {
                                if (user!!.id == progress.user) {
                                    subjects.progress = progress.progress
                                    println("SUBJECTS: " + subjects.progress)
                                }
                            }

                        }
                    }
                }
            }
            binding.rvSubjectMainMenu.layoutManager =
                GridLayoutManager(this@MainMenu.context, 2)
            binding.rvSubjectMainMenu.adapter =
                SubjectAdapter(listSubjects, this@MainMenu)
        }
    }

    private fun checkDifficultyinSubject() {
        var baja = 0
        var media = 0
        var alta = 0
        listLevels.forEach { level ->
            listSubjects.forEach { subject ->
                if (level.difficulty == 0) {
                    baja++
                } else if (level.difficulty == 1) {
                    media++
                } else if (level.difficulty == 2) {
                    alta++
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        user = arguments?.getSerializable("User") as UserItem?
    }

}