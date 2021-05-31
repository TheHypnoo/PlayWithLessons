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
import com.google.firebase.auth.FirebaseAuth
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.application.AppConstants
import com.upitnik.playwithlessons.core.Result
import com.upitnik.playwithlessons.core.extensions.*
import com.upitnik.playwithlessons.data.model.authentication.User
import com.upitnik.playwithlessons.data.model.subject.Subject
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
    private var user: User? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainMenuBinding.bind(view)
        if (user != null) {
            binding.civUser.load(user!!.image!!)
            binding.tvUser.text = user!!.nickname
            binding.lpiProgress.progress = user!!.experience

            binding.tvLevelUser.text = checkExperience(user!!.experience)
        }
        CoroutineScope(Dispatchers.Main).launch {
            withContext(CoroutineScope(Dispatchers.Main).coroutineContext) {
                getUser()
                getSubjects()
            }
        }
        initClickListener()
    }

    private fun initClickListener() {
        binding.btnRanking.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenu_to_Ranking)
        }
        binding.mcvUser.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable(
                "User",
                User(
                    user!!.id,
                    user!!.uid,
                    user!!.email,
                    user!!.experience,
                    user!!.image,
                    user!!.nickname,
                    user!!.score
                )
            )
            findNavController().navigate(R.id.action_mainMenu_to_Profile, bundle)
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
                    Uri.parse(AppConstants.BASE_URL)
                )
            )
        }
    }

    private fun getUser() {
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
                    binding.civUser.load(user!!.image!!)
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

    private fun getSubjects() {
        mainMenuViewModel.getSubjects(FirebaseAuth.getInstance().currentUser!!.uid)
            .observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.pbSubjescts.visible()
                    }
                    is Result.Sucess -> {
                        binding.pbSubjescts.gone()
                        binding.rvSubjectMainMenu.visible()
                        if (result.data.isNotEmpty()) {
                            binding.rvSubjectMainMenu.layoutManager =
                                GridLayoutManager(this@MainMenu.context, 2)
                            binding.rvSubjectMainMenu.adapter =
                                SubjectAdapter(result.data, this@MainMenu)
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

    override fun onSubjectClicked(Subject: Subject) {
        val bundle = Bundle()
        bundle.putSerializable(
            "Subject",
            Subject(
                Subject.gamemode_id,
                Subject.id,
                Subject.image,
                Subject.name,
                Subject.number,
                Subject.pwluser_id,
                Subject.subject_id,
                Subject.difficult
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        user = arguments?.getSerializable("User") as User?
    }

}